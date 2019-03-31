package com.epam.lab.pavel_katsuba.vneklasniki.db_utils;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.exceptions.DataBaseException;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySqlDBManager implements DBManager {
    public static final String UNREGISTERING_JDBC_DRIVER = "unregistering jdbc driver: ";
    public static final String ERROR_UNREGISTERING_JDBC_DRIVER = "error unregistering jdbc driver: ";
    private final Query query = new SqlQuery();
    private final static String URL = "jdbc:mysql://localhost:3306/vneklasniki?useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
//    private static final String URL = "jdbc:mysql://192.168.99.100:3306/vneklasniki?serverTimezone=UTC&useSSL=false&useLegacyDatetimeCode=false";
    private final static String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private final static String USER = "root";
//    private final static String PASS = "root";
    private final static String PASS = "";

    private static final Logger log = Logger.getLogger(MySqlDBManager.class.getSimpleName());
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
            log.log(Level.WARNING, e.getMessage(), e);
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
            log.log(Level.WARNING, e.getMessage(), e);
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
            log.log(Level.INFO, e.getMessage(), e);
        }
    }
    public void closeResultSet(ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            log.log(Level.INFO, e.getMessage(), e);
        }
    }

    public void destroy() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.log(Level.INFO, e.getMessage(), e);
            }
        }
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log.info(UNREGISTERING_JDBC_DRIVER + driver);
            } catch (SQLException e) {
                log.log(Level.WARNING, ERROR_UNREGISTERING_JDBC_DRIVER + driver, e);
            }
        }

    }

    public Query getQuery() {
        return query;
    }
}
