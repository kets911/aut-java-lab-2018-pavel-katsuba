package com.epam.lab.pavel_katsuba.library.db_utils;

import com.epam.lab.pavel_katsuba.library.Beans.Author;
import com.epam.lab.pavel_katsuba.library.Beans.Book;
import com.epam.lab.pavel_katsuba.library.Beans.Genre;
import com.epam.lab.pavel_katsuba.library.interfaces.BookService;
import com.epam.lab.pavel_katsuba.library.interfaces.CrudDao;
import com.epam.lab.pavel_katsuba.library.interfaces.RelatesDao;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
@Data
public class BookServiceImpl implements BookService {
    private final CrudDao<Book> bookDAO;
    private final CrudDao<Author> authorDAO;
    private final CrudDao<Genre> genreDAO;
    private final RelatesDao<Book, Author> authorRelatesDAO;
    private final RelatesDao<Book, Genre> genreRelatesDAO;

    public List<Book> getBooks() {
        List<Book> books = bookDAO.getAllEntities();
        for (Book book : books) {
            int id = book.getId();
            List<Author> authors = authorRelatesDAO.getBeans(id);
            List<Genre> genres = genreRelatesDAO.getBeans(id);
            book.setAuthors(authors);
            book.setGenres(genres);
        }
        return books;
    }

    public Book getBook(int bookId) {
        Book book = bookDAO.getEntity(bookId);
        List<Author> authors = authorRelatesDAO.getBeans(bookId);
        List<Genre> genres = genreRelatesDAO.getBeans(bookId);
        book.setAuthors(authors);
        book.setGenres(genres);
        return book;
    }

    public Book getBook(String bookName) {
        Book book = bookDAO.getEntity(bookName);
        List<Author> authors = authorRelatesDAO.getBeans(book.getId());
        List<Genre> genres = genreRelatesDAO.getBeans(book.getId());
        book.setAuthors(authors);
        book.setGenres(genres);
        return book;
    }

    @Transactional
    public void addBook(Book book) {
        String nameBook = book.getNameBook();
        boolean isExist = bookDAO.isExist(nameBook);
        if (isExist) {
            Book dbBook = bookDAO.getEntity(nameBook);
            dbBook.setCount(dbBook.getCount() + book.getCount());
            bookDAO.putEntity(dbBook, dbBook.getId());
        } else {
            bookDAO.addEntity(book);
            List<Author> authors = new ArrayList<>();
            List<Genre> genres = new ArrayList<>();
            for (Author author : book.getAuthors()) {
                authorDAO.addEntity(author);
                authors.add(authorDAO.getEntity(author.getAuthorName()));
            }
            for (Genre genre : book.getGenres()) {
                genreDAO.addEntity(genre);
                genres.add(genreDAO.getEntity(genre.getGenreName()));
            }
            int bookId = bookDAO.getEntity(nameBook).getId();
            authorRelatesDAO.addBeans(authors, bookId);
            genreRelatesDAO.addBeans(genres, bookId);
        }
    }

    @Transactional
    public void changeAuthor(int bookId, int oldAuthorId, Author newAuthor) {
        authorDAO.addEntity(newAuthor);
        int authorId = authorDAO.getEntity(newAuthor.getAuthorName()).getId();
        authorRelatesDAO.putBeans(bookId, oldAuthorId, authorId);
    }

    @Transactional
    public void changeGenre(int bookId, int oldGenreId, Genre newGenre) {
        genreDAO.addEntity(newGenre);
        int newGenreId = genreDAO.getEntity(newGenre.getGenreName()).getId();
        genreRelatesDAO.putBeans(bookId, oldGenreId, newGenreId);
    }

    public void changeBook(int oldBookId, Book newBook) {
        bookDAO.putEntity(newBook, oldBookId);
    }

    @Transactional
    public void deleteBook(int bookId) {
        authorRelatesDAO.deleteBeanRelates(bookId);
        genreRelatesDAO.deleteBeanRelates(bookId);
        bookDAO.deleteEntity(bookId);
    }
}
