package com.epam.lab.pavel_katsuba.library.controllers;

import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.db_utils.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;

@Controller()
public class MainController {
    private BookService bookService;

    @Autowired
    public MainController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping({"/", "start"})
    public String start(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!"anonymousUser".equals(authentication.getPrincipal())) {
            Reader reader = (Reader) authentication.getPrincipal();
            model.addAttribute("reader", reader);
        }
        model.addAttribute("books", bookService.getBooks());

        String auth = "dXNlcjpwYXNzd29yZA==";
        System.out.println(new String(Base64.getDecoder().decode(auth)));
        System.out.println(new String(Base64.getEncoder().encode("user:password".getBytes())));

        return "start";
    }
}
