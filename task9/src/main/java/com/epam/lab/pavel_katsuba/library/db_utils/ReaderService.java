package com.epam.lab.pavel_katsuba.library.db_utils;

import com.epam.lab.pavel_katsuba.library.Beans.Book;
import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.Beans.Role;
import com.epam.lab.pavel_katsuba.library.interfaces.CrudDao;
import com.epam.lab.pavel_katsuba.library.interfaces.RelatesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class ReaderService implements UserDetailsService {
    private CrudDao<Book> bookDao;
    private CrudDao<Reader> readerDao;
    private RelatesDao<Reader, Book> relatesDao;

    @Autowired
    public ReaderService(CrudDao<Book> bookDao, CrudDao<Reader> readerImpl, RelatesDao<Reader, Book> relatesDao) {
        this.bookDao = bookDao;
        this.readerDao = readerImpl;
        this.relatesDao = relatesDao;
    }

    public void createReader(Reader reader) {
        readerDao.addEntity(reader);
    }

    public Reader getReaderById(int id) {
        Reader reader = readerDao.getEntity(id);
        reader.setBooks(relatesDao.getBeans(id));
        return reader;
    }

    public Reader getReaderByName(String name) {
        Reader reader = readerDao.getEntity(name);
        reader.setBooks(relatesDao.getBeans(reader.getId()));
        return reader;
    }

    public List<Reader> getAllReaders() {
        return readerDao.getAllEntities();
    }

    @Transactional
    public void takeBooks(int readerId, List<Integer> bookIds) {
        List<Book> books = new ArrayList<>();
        for (Integer id : bookIds) {
            Book book = bookDao.getEntity(id);
            book.setTaken(true);
            bookDao.putEntity(book, id);
            books.add(book);
        }
        relatesDao.addBeans(books, readerId);
    }

    @Transactional
    public void returnBook(int readerId, int[] bookIds) {
        for (Integer id : bookIds) {
            Book book = bookDao.getEntity(id);
            book.setTaken(false);
            bookDao.putEntity(book, id);
            relatesDao.delete(readerId, id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Reader reader = readerDao.getEntity(login);
        if (reader == null) {
            throw new UsernameNotFoundException("reader " + login + " was not found!");
        }
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        reader.setAuthorities(roles);
        return reader;
    }
}
