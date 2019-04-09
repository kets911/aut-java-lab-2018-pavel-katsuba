package com.epam.lab.pavel_katsuba.library.controllers;

import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.db_utils.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    public String showAccount(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reader reader = (Reader) authentication.getPrincipal();
        model.addAttribute("reader", reader);
        model.addAttribute("books", reader.getBooks());
        return "account";
    }

    @RequestMapping("/takeBooks")
    public ModelAndView addBooks(HttpServletRequest request, @RequestParam("bookCheckbox") String[] checkboxValues) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reader reader = (Reader) authentication.getPrincipal();
        List<Integer> bookIds = new ArrayList<>();
        for (String s: checkboxValues) {
            bookIds.add(Integer.valueOf(s));
        }
        readerService.takeBooks(reader.getId(), bookIds);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/returnBook")
    public ModelAndView returnBook(HttpServletRequest request, @RequestParam("returnCheckbox") int[] checkboxValues) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Reader reader = (Reader) authentication.getPrincipal();
        readerService.returnBook(reader.getId(), checkboxValues);
        return new ModelAndView("redirect:/");
    }
}
