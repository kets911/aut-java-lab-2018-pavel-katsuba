package com.epam.lab.pavel_katsuba.library.validators;

import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.db_utils.ReaderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@PropertySource("classpath:application.properties")
public class ReaderValidator implements Validator {
    private static final String USERNAME = "username";
    private static final String READER_CAN_BE_CREATED = "Reader with name %s can be created";
    private static Logger log = LogManager.getLogger(ReaderValidator.class.getSimpleName());
    @Value("${Duplicate.authorizationForm.username}")
    private String userNameException;
    @Value("${username.empty}")
    private String userNameEmptyException;
    @Value("${password.empty}")
    private String passwordEmptyException;

    @Autowired
    private ReaderServiceImpl readerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Reader.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, USERNAME, userNameEmptyException);
        ValidationUtils.rejectIfEmpty(errors, "password", passwordEmptyException);
        Reader formReader = (Reader) o;
        try {
            readerService.getReaderByName(formReader.getUsername());
            errors.rejectValue(USERNAME, userNameException);
        } catch (DataAccessException e) {
            log.info(String.format(READER_CAN_BE_CREATED, formReader.getUsername()));
        }
    }
}
