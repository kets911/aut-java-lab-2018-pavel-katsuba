package com.epam.lab.pavel_katsuba.DBWorking.interfaces;

import com.epam.lab.pavel_katsuba.DBWorking.beans.Author;
import com.epam.lab.pavel_katsuba.DBWorking.beans.Book;
import com.epam.lab.pavel_katsuba.DBWorking.beans.Genre;

import java.util.List;

public interface DAOService {
    List<Book> getBooks();
    Book getBook(int bookId);
    void addBook(Book newBook);
    void changeBook(int oldBookId, Book newBook);
    void changeAuthor(int bookId, int oldAuthorId, Author newAuthor);
    void changeGenre(int bookId, int oldGenreId, Genre newGenre);
    void deleteBook(int BookId);
}
