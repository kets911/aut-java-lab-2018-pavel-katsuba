package com.epam.lab.pavel_katsuba.library.db_utils;

import com.epam.lab.pavel_katsuba.library.Beans.Book;
import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.Beans.Role;
import com.epam.lab.pavel_katsuba.library.interfaces.CrudDao;
import com.epam.lab.pavel_katsuba.library.interfaces.ReaderService;
import com.epam.lab.pavel_katsuba.library.interfaces.RelatesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class ReaderServiceImpl implements UserDetailsService, ReaderService {
    private static final String WRONG_BOOK_EXCEPTION = "Reader can have only one copy of book in time. This reader already has had book: ";
    private CrudDao<Book> bookDao;
    private CrudDao<Reader> readerDao;
    private RelatesDao<Reader, Book> bookRelatesDao;
    private RelatesDao<Reader, Role> roleRelatesDao;

    @Autowired
    public ReaderServiceImpl(CrudDao<Book> bookDao, CrudDao<Reader> readerImpl, RelatesDao<Reader, Book> bookRelatesDao, RelatesDao<Reader, Role> roleRelatesDao) {
        this.bookDao = bookDao;
        this.readerDao = readerImpl;
        this.bookRelatesDao = bookRelatesDao;
        this.roleRelatesDao = roleRelatesDao;
    }

    @Transactional
    public void createReader(Reader reader) {
        readerDao.addEntity(reader);
        Reader dbReader = readerDao.getEntity(reader.getUsername());
        roleRelatesDao.addBeans(reader.getAuthorities(), dbReader.getId());
    }

    @Transactional
    public Reader getReaderById(int id) {
        Reader reader = readerDao.getEntity(id);
        reader.setBooks(bookRelatesDao.getBeans(id));
        reader.setAuthorities(roleRelatesDao.getBeans(reader.getId()));
        return reader;
    }

    @Transactional
    public Reader getReaderByName(String name) {
        Reader reader = readerDao.getEntity(name);
        reader.setBooks(bookRelatesDao.getBeans(reader.getId()));
        reader.setAuthorities(roleRelatesDao.getBeans(reader.getId()));
        return reader;
    }

    public List<Reader> getAllReaders() {
        return readerDao.getAllEntities();
    }

    public void changeReader(int oldId, Reader reader) {
        readerDao.putEntity(reader, oldId);
    }

    @Transactional
    public void deleteReader(int id) {
        bookRelatesDao.deleteBeanRelates(id);
        roleRelatesDao.deleteBeanRelates(id);
        readerDao.deleteEntity(id);
    }

    @Transactional
    public void takeBooks(int readerId, List<Integer> bookIds) {
        List<Book> books = new ArrayList<>();
        for (Integer id : bookIds) {
            Book book = bookDao.getEntity(id);
            if (bookRelatesDao.relateIsExist(readerId, id)) {
                throw new IllegalArgumentException(WRONG_BOOK_EXCEPTION + book.getNameBook());
            }
            int count = book.getCount();
            book.setCount(count - 1);
            if (count - 1 <= 0) {
                book.setTaken(true);
            }
            bookDao.putEntity(book, id);
            books.add(book);
        }
        bookRelatesDao.addBeans(books, readerId);
    }

    @Transactional
    public void returnBook(int readerId, int[] bookIds) {
        for (Integer id : bookIds) {
            boolean isExist = bookRelatesDao.relateIsExist(readerId, id);
            if (isExist) {
                Book book = bookDao.getEntity(id);
                book.setCount(book.getCount() + 1);
                book.setTaken(false);
                bookDao.putEntity(book, id);
                bookRelatesDao.delete(readerId, id);
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Reader reader = readerDao.getEntity(login);
        if (reader == null) {
            throw new UsernameNotFoundException("reader " + login + " was not found!");
        }
        List<Role> roles = roleRelatesDao.getBeans(reader.getId());
        reader.setAuthorities(roles);
        return reader;
    }

    @PostConstruct
    public void adminInit() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        Reader reader = Reader.builder()
                .username("admin")
                .password("password")
                .authorities(roles)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .enabled(true)
                .build();
        readerDao.addEntity(reader);
        Reader dbReader = readerDao.getEntity(reader.getUsername());
        roleRelatesDao.addBeans(roles, dbReader.getId());
    }
}
