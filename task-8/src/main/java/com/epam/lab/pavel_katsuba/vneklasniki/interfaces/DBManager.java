package com.epam.lab.pavel_katsuba.vneklasniki.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DBManager {
    Connection getConnection();

    void putConnection(Connection connection);

    void closePreparedStatement(PreparedStatement ps);

    void closeResultSet(ResultSet rs);

    void destroy();

    Query getQuery();
}
