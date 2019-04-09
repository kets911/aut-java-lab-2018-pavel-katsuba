package com.epam.lab.pavel_katsuba.library.db_utils;

import com.epam.lab.pavel_katsuba.library.Beans.Author;
import com.epam.lab.pavel_katsuba.library.Beans.Book;
import com.epam.lab.pavel_katsuba.library.Beans.Genre;
import com.epam.lab.pavel_katsuba.library.db_utils.dao_impls.BookImpl;
import com.epam.lab.pavel_katsuba.library.interfaces.CrudDao;
import com.epam.lab.pavel_katsuba.library.interfaces.RelatesBookDao;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class BookService {
    private final CrudDao<Book> bookDAO;
    private final CrudDao<Author> authorDAO;
    private final CrudDao<Genre> genreDAO;
    private final RelatesBookDao<Author> authorRelatesDAO;
    private final RelatesBookDao<Genre> genreRelatesDAO;

    public List<Book> getBooks() {
        List<Book> books = bookDAO.getAllEntities();
        for (Book book : books) {
            int id = book.getId();
            List<Author> authors = authorRelatesDAO.getEntities(id);
            List<Genre> genres = genreRelatesDAO.getEntities(id);
            book.setAuthors(authors);
            book.setGenres(genres);
        }
        return books;
    }

    public Book getBook(int bookId ) {
        Book book = bookDAO.getEntity(bookId);
        List<Author> authors = authorRelatesDAO.getEntities(bookId);
        List<Genre> genres = genreRelatesDAO.getEntities(bookId);
        book.setAuthors(authors);
        book.setGenres(genres);
        return book;
    }

    public void addBook(Book book) {
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
        int bookId = bookDAO.getEntity(book.getNameBook()).getId();
        authorRelatesDAO.addEntities(authors, bookId);
        genreRelatesDAO.addEntities(genres, bookId);
    }

    public void changeAuthor(int bookId, int oldAuthorId, Author newAuthor) {
        authorDAO.addEntity(newAuthor);
        int authorId = authorDAO.getEntity(newAuthor.getAuthorName()).getId();
        authorRelatesDAO.putEntities(bookId, oldAuthorId, authorId);
    }

    public void changeGenre(int bookId, int oldGenreId, Genre newGenre) {
        genreDAO.addEntity(newGenre);
        int newGenreId = genreDAO.getEntity(newGenre.getGenreName()).getId();
        genreRelatesDAO.putEntities(bookId, oldGenreId, newGenreId);
    }

    public void changeBook(int oldBookId, Book newBook){
        bookDAO.putEntity(newBook, oldBookId);
    }

    public void deleteBook(int bookId) {
        authorRelatesDAO.deleteEntityRelate(bookId);
        genreRelatesDAO.deleteEntityRelate(bookId);
        bookDAO.deleteEntity(bookId);
    }
}
