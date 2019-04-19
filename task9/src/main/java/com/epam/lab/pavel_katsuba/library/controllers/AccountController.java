package com.epam.lab.pavel_katsuba.library.controllers;

import com.epam.lab.pavel_katsuba.library.Beans.Book;
import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.db_utils.ReaderServiceImpl;
import com.epam.lab.pavel_katsuba.library.interfaces.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {
    private ReaderService readerService;

    @Autowired
    public AccountController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @RequestMapping("/account")
    public String showAccount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reader reader = (Reader) authentication.getPrincipal();
        List<Book> books = readerService.getReaderById(reader.getId()).getBooks();
        model.addAttribute("reader", reader);
        model.addAttribute("books", books);
        return "account";
    }

    @RequestMapping("/takeBooks")
    public ModelAndView addBooks(@RequestParam("bookCheckbox") String[] checkboxValues) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reader reader = (Reader) authentication.getPrincipal();
        List<Integer> bookIds = new ArrayList<>();
        for (String s : checkboxValues) {
            bookIds.add(Integer.valueOf(s));
        }
        readerService.takeBooks(reader.getId(), bookIds);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/returnBook")
    public ModelAndView returnBook(@RequestParam("returnCheckbox") int[] checkboxValues) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reader reader = (Reader) authentication.getPrincipal();
        readerService.returnBook(reader.getId(), checkboxValues);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/admin")
    public String adminStart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reader reader = (Reader) authentication.getPrincipal();
        model.addAttribute("reader", reader);
        return "admin";
    }

    @RequestMapping("/admin/{id}")
    public String getReader(@PathVariable("id") int id, Model model) {
        Reader reader = readerService.getReaderById(id);
        model.addAttribute("reader", reader);
        return "redirect:/admin/readers";
    }

    @RequestMapping("/admin/readers")
    public String getReaders(Model model) {
        List<Reader> allReaders = readerService.getAllReaders();
        model.addAttribute("readers", allReaders);
        return "readerAdmin";
    }

    @RequestMapping("/admin/readerDelete")
    public String readerDelete(@RequestParam("readerId") int id) {
        readerService.deleteReader(id);
        return "redirect:/admin/readers";
    }

    @RequestMapping("/admin/readerBan")
    public String readerBan(@RequestParam("readerId") int id) {
        Reader reader = readerService.getReaderById(id);
        reader.setAccountNonLocked(false);
        readerService.changeReader(id, reader);
        return "redirect:/admin/readers";
    }

    @RequestMapping("/admin/readerUnBan")
    public String readerUnBan(@RequestParam("readerId") int id) {
        Reader reader = readerService.getReaderById(id);
        reader.setAccountNonLocked(true);
        readerService.changeReader(id, reader);
        return "redirect:/admin/readers";
    }
}
