package com.epam.lab.pavel_katsuba.DBWorking;

import com.epam.lab.pavel_katsuba.DBWorking.beans.Author;
import com.epam.lab.pavel_katsuba.DBWorking.beans.Book;
import com.epam.lab.pavel_katsuba.DBWorking.beans.Genre;
import com.epam.lab.pavel_katsuba.DBWorking.dao_impl.*;
import com.epam.lab.pavel_katsuba.DBWorking.databases.DBManager;
import com.epam.lab.pavel_katsuba.DBWorking.interfaces.LibraryDAO;
import com.epam.lab.pavel_katsuba.DBWorking.interfaces.RelatesBookDAO;
import com.epam.lab.pavel_katsuba.DBWorking.services.BookService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args){
        DBManager dbManager = DBManager.getInstance();
        LibraryDAO<Book> bookLibraryDAO = new BookDAOImpl(dbManager);
        LibraryDAO<Author> authorLibraryDAO = new AuthorDAOImpl(dbManager);
        LibraryDAO<Genre> genreLibraryDAO = new GenreDAOImpl(dbManager);
        RelatesBookDAO<Author> authorRelatesBookDAO = new AuthorRelatesBookDAOImpl(dbManager);
        RelatesBookDAO<Genre> genreRelatesBookDAO = new GenreRelatesBookDAOImpl(dbManager);
        BookService bookService = BookService.builder()
                .bookDAO(bookLibraryDAO)
                .authorDAO(authorLibraryDAO)
                .genreDAO(genreLibraryDAO)
                .authorRelatesDAO(authorRelatesBookDAO)
                .genreRelatesDAO(genreRelatesBookDAO)
                .build();
        print(bookService.getBooks());

        System.out.println("\nAdding the book");
        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        authors.add(new Author("Perumov"));
        genres.add(new Genre("fantastic"));
        Book book = new Book("Skull", Date.valueOf(LocalDate.of(1995, 6, 12)), false);
        book.setGenres(genres);
        book.setAuthors(authors);
        bookService.addBook(book);
        print(bookService.getBooks());

        System.out.println("\nChanging author of the book with id is 2");
        Author oldAuthor = bookService.getBook(2).getAuthors().get(0);
        Author newAuthor = new Author("Pashka of course");
        bookService.changeAuthor(2, oldAuthor.getId(), newAuthor);
        System.out.println(bookService.getBook(2));
        System.out.println("\nChanging genre of the book with id is 2");
        Genre oldGenre = bookService.getBook(2).getGenres().get(0);
        Genre newGenre = new Genre("Tail");
        bookService.changeGenre(2, oldGenre.getId(), newGenre);
        System.out.println(bookService.getBook(2));
        System.out.println("\nChanging the book with id is 2");
        Book newBook = new Book("Some Tail", Date.valueOf(LocalDate.of(2019, 2, 13)), false);
        bookService.changeBook(2, newBook);
        System.out.println(bookService.getBook(2));

        System.out.println("\nResult");
        print(bookService.getBooks());

    }

    private static void print(List<Book> books){
        for (Book book : books){
            System.out.println(book);
        }
    }
}
