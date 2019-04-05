package com.epam.lab.pavel_katsuba.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan("com.epam.lab.pavel_katsuba.library.*")
public class JdbcConfig {
    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:~/library");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/library?useLegacyDatetimeCode=false&amp&serverTimezone=UTC");
        dataSource.setUsername("web");
        dataSource.setPassword("web");
        return dataSource;
    }

//    @Bean(initMethod="start",destroyMethod="stop")
//    public org.h2.tools.Server h2WebConsoleServer () throws SQLException {
//        return org.h2.tools.Server.createWebServer("-web","-webAllowOthers","-webDaemon","-webPort", "8089");
//    }
}
