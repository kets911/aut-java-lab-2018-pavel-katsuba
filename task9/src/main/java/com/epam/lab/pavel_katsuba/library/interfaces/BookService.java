package com.epam.lab.pavel_katsuba.library.interfaces;

import com.epam.lab.pavel_katsuba.library.Beans.Author;
import com.epam.lab.pavel_katsuba.library.Beans.Book;
import com.epam.lab.pavel_katsuba.library.Beans.Genre;

import java.util.List;

public interface BookService {

    Book getBook(String bookName);

    List<Book> getBooks();

    void addBook(Book book);

    void changeBook(int id, Book book);

    void deleteBook(int id);

    Book getBook(int bookId);

    void changeAuthor(int bookId, int oldAuthorId, Author newAuthor);

    void changeGenre(int bookId, int oldGenreId, Genre newGenre);
}
