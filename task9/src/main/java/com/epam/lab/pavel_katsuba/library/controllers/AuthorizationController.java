package com.epam.lab.pavel_katsuba.library.controllers;

import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.Beans.Role;
import com.epam.lab.pavel_katsuba.library.interfaces.ReaderService;
import com.epam.lab.pavel_katsuba.library.validators.ReaderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthorizationController {
    private ReaderService readerService;
    private ReaderValidator readerValidator;

    @Autowired
    public AuthorizationController(ReaderService readerService, ReaderValidator readerValidator) {
        this.readerService = readerService;
        this.readerValidator = readerValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(readerValidator);
    }

    @RequestMapping("/registration")
    public String registration(@ModelAttribute @Validated Reader formReader, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            throw new RuntimeException("Input error");
//        }
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        readerService.createReader(Reader.builder()
                .username(formReader.getUsername())
                .password(formReader.getPassword())
                .authorities(roles)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .enabled(true)
                .build());
        Reader dbReader = readerService.getReaderByName(formReader.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(dbReader, null, dbReader.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String login, @RequestParam("password") String password, Model model) {
        Reader reader = readerService.getReaderByName(login);
        model.addAttribute("reader", reader);
        return "start";
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }
}
