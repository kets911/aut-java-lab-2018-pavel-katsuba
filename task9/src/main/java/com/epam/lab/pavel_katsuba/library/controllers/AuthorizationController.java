package com.epam.lab.pavel_katsuba.library.controllers;

import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.Beans.Role;
import com.epam.lab.pavel_katsuba.library.db_utils.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthorizationController {
    private ReaderService readerService;

    @Autowired
    public AuthorizationController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @RequestMapping("/registration")
    public String registration(@RequestParam("login") String login, @RequestParam("password") String password) {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        readerService.createReader(Reader.builder()
                .username(login)
                .password(password)
                .authorities(roles)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .enabled(true)
                .build());
        Reader reader = readerService.getReaderByName(login);
        Authentication authentication = new UsernamePasswordAuthenticationToken(reader, null, reader.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String login, @RequestParam("password") String password , Model model) {
        Reader reader = readerService.getReaderByName(login);
//        if (passwordValidate(reader, password)) {
            model.addAttribute("reader", reader);
            return "start";

//        }
//        throw new  IllegalArgumentException("wrong password");
    }

    @RequestMapping("/logout")
    public ModelAndView login(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }

    private boolean passwordValidate(Reader reader, String requestPassword) {
        return reader.getPassword().equals(requestPassword);
    }
}
