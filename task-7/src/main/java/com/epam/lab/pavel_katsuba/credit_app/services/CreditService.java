package com.epam.lab.pavel_katsuba.credit_app.services;

import com.epam.lab.pavel_katsuba.credit_app.JsonUtils.converters.DepartmentsDBManager;
import com.epam.lab.pavel_katsuba.credit_app.beans.*;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.CreditStatus;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.Currency;
import com.epam.lab.pavel_katsuba.credit_app.beans.enums.SortType;
import com.epam.lab.pavel_katsuba.credit_app.dao_impl.TransactionDAOImpl;
import com.epam.lab.pavel_katsuba.credit_app.exceptions.DBManagerException;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.BankDAO;
import com.epam.lab.pavel_katsuba.credit_app.interfaces.DBManager;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreditService {
    public static final Logger LOGGER = Logger.getLogger(CreditService.class.getSimpleName());
    private BankDAO<User> userDAO;
    private BankDAO<Credit> creditDao;
    private BankDAO<Discount> discountDAO;
    private BankDAO<Event> eventDAO;
    private BankDAO<Transaction> transactionDAO;
    private static final String FILE_NAME_HEAD = "db_";
    private static final String FILE_NAME_TAIL = ".json";
    private static final String DEPARTMENT_PATTERN = FILE_NAME_HEAD + "[a-z]+([A-Z]*[0-9]*)?" + FILE_NAME_TAIL;
    private static final String NOT_FILE_EXCEPTION = "there is not file: " + FILE_NAME_HEAD;


    public CreditService() {
    }

    public CreditService(BankDAO<User> userDAO, BankDAO<Credit> creditDao, BankDAO<Discount> discountDAO, BankDAO<Event> eventDAO, BankDAO<Transaction> transactionDAO) {
        this.userDAO = userDAO;
        this.creditDao = creditDao;
        this.discountDAO = discountDAO;
        this.eventDAO = eventDAO;
        this.transactionDAO = transactionDAO;
    }

    public void prepareDB(Settings settings, String srcDirectory) {
        List<String> useDepartments = settings.getUseDepartments();
        DBManager<Data> converter;
        final File directory = new File(srcDirectory);
        if (useDepartments.size() == 0) {
            File[] dbFiles = directory.listFiles((dir, name) -> name.matches(DEPARTMENT_PATTERN));
            if (dbFiles != null) {
                for (File file : dbFiles) {
                    converter = new DepartmentsDBManager(file);
                    transportTransactions(converter);
                }
            }
        } else {
            final String filePathHead = directory.getPath() + "/" + FILE_NAME_HEAD;
            for (String dep : useDepartments) {
                try {
                    converter = new DepartmentsDBManager(new File(filePathHead + dep + FILE_NAME_TAIL));
                    transportTransactions(converter);
                } catch (DBManagerException e) {
                    LOGGER.log(Level.INFO, NOT_FILE_EXCEPTION + dep + FILE_NAME_TAIL, e);
                }
            }
        }
    }

    private void transportTransactions(DBManager<Data> converter) {
        TransactionDAOImpl transactionDAODepartments = new TransactionDAOImpl(converter);
        while (transactionDAODepartments.readAll().size() != 0) {
            Transaction transaction = transactionDAODepartments.readAll().get(0);
            int id = transaction.getId();
            transactionDAO.create(transaction);
            transactionDAODepartments.delete(id);
        }
    }

    private BigDecimal sumTransactionInBr(Transaction transaction, Settings settings) {
        List<Event> allEvents = eventDAO.readAll();
        Event currentEvent = getLastEventForTransaction(transaction, allEvents);
        if (currentEvent == null) {
            switch (transaction.getCurrency()) {
                case EUR:
                    return BigDecimal.valueOf(settings.getStartCostEUR()).multiply(transaction.getMoney());
                case USD:
                    return BigDecimal.valueOf(settings.getStartCostUSD()).multiply(transaction.getMoney());
                default:
                    return transaction.getMoney();
            }
        }
        return currentEvent.getCost().multiply(transaction.getMoney());
    }

    private Event getLastEventForTransaction(Transaction transaction, List<Event> allEvents) {
        Event currentEvent = null;
        for (Event event : allEvents) {
            if (event.getDate().isBefore(transaction.getDate()) && event.getCurrency() == transaction.getCurrency()) {
                if (currentEvent == null || currentEvent.getDate().isBefore(event.getDate())) {
                    currentEvent = event;
                }
            }
        }
        return currentEvent;
    }

    private BigDecimal sumDebtForTimeTransaction(BigDecimal sumDebt, LocalDate lastTrans, LocalDate transTime, Credit credit) {
        LocalDate creditDate = credit.getDate();
        long countPeriodsBeforeNow;
        long countPeriodsBeforeLastTrans;
        long countRateTimes;
        LocalDate dateLastAddedPercents;
        switch (credit.getPeriod()) {
            case DAY:
                countPeriodsBeforeNow = ChronoUnit.DAYS.between(creditDate, transTime);
                countPeriodsBeforeLastTrans = ChronoUnit.DAYS.between(creditDate, lastTrans);
                countRateTimes = countPeriodsBeforeNow - countPeriodsBeforeLastTrans;
                dateLastAddedPercents = creditDate.plusDays(countPeriodsBeforeLastTrans);
                for (long i = 0; i < countRateTimes; i++) {
                    sumDebt = addPercents(sumDebt, dateLastAddedPercents.plusDays(1), credit);
                }
                break;
            case WEEK:
                countPeriodsBeforeNow = ChronoUnit.WEEKS.between(creditDate, transTime);
                countPeriodsBeforeLastTrans = ChronoUnit.WEEKS.between(creditDate, lastTrans);
                countRateTimes = countPeriodsBeforeNow - countPeriodsBeforeLastTrans;
                dateLastAddedPercents = creditDate.plusWeeks(countPeriodsBeforeLastTrans);
                for (long i = 0; i < countRateTimes; i++) {
                    sumDebt = addPercents(sumDebt, dateLastAddedPercents.plusWeeks(1), credit);
                }
                break;
            case MONTH:
                countPeriodsBeforeNow = ChronoUnit.MONTHS.between(creditDate, transTime);
                countPeriodsBeforeLastTrans = ChronoUnit.MONTHS.between(creditDate, lastTrans);
                countRateTimes = countPeriodsBeforeNow - countPeriodsBeforeLastTrans;
                dateLastAddedPercents = creditDate.plusMonths(countPeriodsBeforeLastTrans);
                for (long i = 0; i < countRateTimes; i++) {
                    sumDebt = addPercents(sumDebt, dateLastAddedPercents.plusMonths(1), credit);
                }
                break;
            case YEAR:
                countPeriodsBeforeNow = ChronoUnit.YEARS.between(creditDate, transTime);
                countPeriodsBeforeLastTrans = ChronoUnit.YEARS.between(creditDate, lastTrans);
                countRateTimes = countPeriodsBeforeNow - countPeriodsBeforeLastTrans;
                dateLastAddedPercents = creditDate.plusYears(countPeriodsBeforeLastTrans);
                for (long i = 0; i < countRateTimes; i++) {
                    sumDebt = addPercents(sumDebt, dateLastAddedPercents.plusYears(1), credit);
                }
                break;
            default:
                throw new RuntimeException("wrong period = " + credit.getPeriod());
        }
        return sumDebt;
    }

    private BigDecimal addPercents(BigDecimal sumDebt, LocalDate dateAddPercents, Credit credit) {
        double rate = credit.getRate();
        for (Discount discount : discountDAO.readAll()) {
            if (discount.getDiscountPeriod().isHit(dateAddPercents)) {
                rate = rate - discount.getDiscount();
            }
        }
        rate = rate < 0 ? 0 : rate;
        sumDebt = sumDebt.add(sumDebt.divide(BigDecimal.valueOf(100))
                .multiply((BigDecimal.valueOf(rate))));
        return sumDebt;
    }

    public List<CreditInfo> getCreditInfo(Settings settings) {
        ShowFor showFor = settings.getShowFor();
        return userDAO.readAll().stream()
                .filter(user -> showFor == null ? user != null
                        : showFor.getShowForMatcher().isMatch(user))
                .flatMap(user -> creditDao.readAll().stream()
                        .filter(credit -> settings.getDateFrom() == null
                                ? credit != null
                                : credit.getUserId() == user.getId())
                        .collect(Collectors.toList()).stream())
                .flatMap(credit -> {
                    CreditInfo creditInfo = new CreditInfo();
                    if (credit.getDate().isAfter(settings.getDateFrom().minusDays(1))) {
                        creditInfo.setCredit(credit);
                        creditInfo.setStatus(CreditStatus.IN_PROGRESS);
                        List<Transaction> transactionsForCredit = transactionDAO.readAll().stream()
                                .filter(transaction -> settings.getDateTo() == null
                                        ? transaction.getCreditId() == credit.getId()
                                        : transaction.getCreditId() == credit.getId()
                                        && transaction.getDate().isBefore(settings.getDateTo().plusDays(1)))
                                .sorted(Comparator.comparing(Transaction::getDate))
                                .collect(Collectors.toList());
                        LocalDate lastTransaction = credit.getDate();
                        BigDecimal sumDebt = credit.getMoney();
                        BigDecimal sumTransactionInBr;
                        for (Transaction transaction : transactionsForCredit) {
                            sumDebt = sumDebtForTimeTransaction(sumDebt, lastTransaction, transaction.getDate(), credit);
                            sumTransactionInBr = sumTransactionInBr(transaction, settings);
                            sumDebt = sumDebt.subtract(sumTransactionInBr);
                            lastTransaction = transaction.getDate();
                            if (sumDebt.compareTo(BigDecimal.ZERO) <= 0) {
                                creditInfo.setStatus(CreditStatus.DONE);
                                creditInfo.setDoneDate(lastTransaction);
                            }
                        }
                        if (settings.getDateTo() == null) {
                            if (transactionsForCredit.size() == 0) {
                                sumDebt = sumDebtForTimeTransaction(sumDebt, lastTransaction, LocalDate.now(), credit);
                            }
                        } else
                            sumDebt = sumDebtForTimeTransaction(sumDebt, lastTransaction, settings.getDateTo(), credit);
                        creditInfo.setDebt(sumDebt.setScale(2, RoundingMode.DOWN));
                        creditInfo.setTransactionCount(transactionsForCredit.size());
                    }
                    creditInfo.setUser(userDAO.read(credit.getUserId()));
                    return Stream.of(creditInfo);
                })
                .sorted(settings.getSortBy() == SortType.DEBT
                        ? Comparator.comparing(CreditInfo::getDebt)
                        : settings.getSortBy() == SortType.NAME
                        ? (o1, o2) -> o1.getUser().getName().compareTo(o2.getUser().getName())
                        : Comparator.comparingLong(o -> ChronoUnit.DAYS.between(o.getUser().getBirthday(), LocalDate.now())))
                .collect(Collectors.toList());
    }
}
