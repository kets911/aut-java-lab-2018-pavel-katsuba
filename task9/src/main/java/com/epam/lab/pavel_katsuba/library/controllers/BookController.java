package com.epam.lab.pavel_katsuba.library.controllers;

import com.epam.lab.pavel_katsuba.library.Beans.Book;
import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.interfaces.BookService;
import com.epam.lab.pavel_katsuba.library.interfaces.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private BookService bookService;
    private ReaderService readerService;

    @Autowired
    public BookController(BookService bookService, ReaderService readerService) {
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @RequestMapping(value = "/getBookByName/{bookName}", method = RequestMethod.GET)
    public String getBook(@PathVariable("bookName") String bookName) {
        Book book = bookService.getBook(bookName);
        return "admin";
    }

    @RequestMapping("/getBooks")
    public String getBooks(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "bookAdmin";
    }

    @RequestMapping("/addBook")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "admin";
    }

    @RequestMapping("/changeBook")
    public String changeBook(@RequestParam("oldId") int id, @ModelAttribute Book book) {
        bookService.changeBook(id, book);
        return "admin";
    }

    @RequestMapping("/deleteBook")
    @Transactional
    public String deleteBook(@RequestParam("bookId") int id) {
        List<Reader> allReaders = readerService.getAllReaders();
        for (Reader reader : allReaders) {
            readerService.returnBook(reader.getId(), new int[]{id});
        }
        bookService.deleteBook(id);
        return "admin";
    }

}
