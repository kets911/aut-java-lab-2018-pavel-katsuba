package com.epam.lab.pavel_katsuba.vneklasniki.db_utils;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.exceptions.DataBaseException;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.Query;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MySqlDBManager implements DBManager {
    private final Query query = new SqlQuery();
    private final static String URL = "jdbc:mysql://localhost:3306/vneklasniki?useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
    private final static String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private final static String USER = "web";
    private final static String PASS = "web";

    private static final Logger log = LoggerFactory.getLogger(MySqlDBManager.class);
    private static List<Connection> connections = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition sleeper = lock.newCondition();
    private static int connectionsToGenerateCount = 20;
    private static int freeConnectionsCount = 0;
    private static MySqlDBManager manager;

    public static MySqlDBManager instance() {
        if(manager == null) {
            manager = new MySqlDBManager();
        }return manager;
    }

    private MySqlDBManager() {
        try {
            Class.forName(DRIVER_PATH);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new DataBaseException(Constants.DB_EXCEPTION, e);
        }
    }

    public Connection getConnection() {
        try {
            lock.lock();
            if(freeConnectionsCount <=0) {
                if(connectionsToGenerateCount <=0) {
                    while(freeConnectionsCount <= 0) {
                        sleeper.await();
                    }
                }else {
                    connections.add(DriverManager.getConnection(URL, USER, PASS));
                    freeConnectionsCount++;
                    connectionsToGenerateCount--;
                }
            }
            freeConnectionsCount--;
            lock.unlock();
            return connections.get(freeConnectionsCount);
        } catch (InterruptedException | SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataBaseException(Constants.CONNECTION_EXCEPTION, e);
        }
    }

    public void putConnection(Connection connection) {
        lock.lock();
        connections.add(connection);
        freeConnectionsCount++;
        sleeper.signalAll();
        lock.unlock();
    }

    public void closePreparedStatement(PreparedStatement ps) {
        try {
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            log.info(e.getMessage(), e);
        }
    }
    public void closeResultSet(ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            log.info(e.getMessage(), e);
        }
    }

    public Query getQuery() {
        return query;
    }
}
