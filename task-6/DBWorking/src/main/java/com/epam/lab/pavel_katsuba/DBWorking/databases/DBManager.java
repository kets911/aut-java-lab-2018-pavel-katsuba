package com.epam.lab.pavel_katsuba.DBWorking.databases;

import com.epam.lab.pavel_katsuba.DBWorking.Constants;
import com.epam.lab.pavel_katsuba.DBWorking.exceptions.DataBaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
	private static final Logger LOGGER = Logger.getLogger( DBManager.class.getName() );
	private final static String URL = "jdbc:mysql://localhost:3306/library?useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
	private final static String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
	private final static String USER = "web";
	private final static String PASS = "web";
	private static List<Connection> connections = new ArrayList<>();
	private final Lock lock = new ReentrantLock();
	private final Condition sleeper = lock.newCondition();
	private static int connectionsToGenerateCount = 20;
	private static int freeConnectionsCount = 0; 
	private static DBManager manager;
	
	public static DBManager getInstance() {
		if(manager == null) {
			manager = new DBManager();
		}return manager;
	}
	
	private DBManager() {
		try {
			Class.forName(DRIVER_PATH);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, e.toString(), e);
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
			LOGGER.log(Level.WARNING, e.toString(), e);
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
			LOGGER.log(Level.FINE, e.getMessage(), e);
		}
	}
	public void closeResultSet(ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.FINE, e.getMessage(), e);
		}
	}
}