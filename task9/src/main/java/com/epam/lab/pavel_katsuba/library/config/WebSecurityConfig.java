package com.epam.lab.pavel_katsuba.library.config;

import com.epam.lab.pavel_katsuba.library.Beans.Role;
import com.epam.lab.pavel_katsuba.library.db_utils.ReaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.epam.lab.pavel_katsuba.library.*")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ReaderServiceImpl readerService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/", "/start", "/login", "/registration").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/account").hasAuthority(Role.USER.name())
                .antMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
                .and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/start")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(readerService);
    }
}
