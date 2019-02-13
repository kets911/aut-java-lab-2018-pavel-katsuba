package com.epam.lab.pavel_katsuba.DBWorking.dao_impl;

import com.epam.lab.pavel_katsuba.DBWorking.Constants;
import com.epam.lab.pavel_katsuba.DBWorking.SQLConstants;
import com.epam.lab.pavel_katsuba.DBWorking.beans.Book;
import com.epam.lab.pavel_katsuba.DBWorking.databases.DBManager;
import com.epam.lab.pavel_katsuba.DBWorking.exceptions.DAOException;
import com.epam.lab.pavel_katsuba.DBWorking.interfaces.LibraryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAOImpl implements LibraryDAO<Book> {
    private static final Logger LOGGER = Logger.getLogger(BookDAOImpl.class.getName());
    private final DBManager dbManager;
    private static final String INSERT_BOOK = "insert ignore into books (bookName, publishingDate, isTaken) values(?, ?, ?)";
    private static final String SELECT_ALL_BOOKS = "select * from books";
    private static final String SELECT_BOOK_BY_NAME = "select * from books where bookName = (?)";
    private static final String SELECT_BOOK_BY_ID = "select * from books where idBook = (?)";
    private static final String UPDATE_BOOK = "update books set books.bookName = (?), books.publishingDate = (?), " +
            "books.isTaken = (?) where books.idBook = (?)";
    private static final String DELETE_BOOK = "delete from books where books.idBook = (?)";

    public BookDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void addEntity(Book entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(INSERT_BOOK);
            getPreparedStatement.setString(1, entity.getNameBook());
            getPreparedStatement.setDate(2, entity.getPublishingDate());
            getPreparedStatement.setBoolean(3, entity.isTaken());
            getPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.CONNECTION_EXCEPTION, e);
            throw new DAOException(Constants.CONNECTION_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(getPreparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public List<Book> getAllEntities() {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<Book> books = new ArrayList<>();
            getPreparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS);
            resultSet = getPreparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(resultSet.getInt(SQLConstants.ID_BOOK), resultSet.getString(SQLConstants.BOOK_NAME),
                        resultSet.getDate(SQLConstants.PUBLISHING_DATE), resultSet.getBoolean(SQLConstants.IS_TAKEN)));
            }
            return books;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.CONNECTION_EXCEPTION, e);
            throw new DAOException(Constants.CONNECTION_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(getPreparedStatement);
            dbManager.closeResultSet(resultSet);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public Book getEntity(int id) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            getPreparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID);
            getPreparedStatement.setInt(1, id);
            resultSet = getPreparedStatement.executeQuery();
            return getBook(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.CONNECTION_EXCEPTION, e);
            throw new DAOException(Constants.CONNECTION_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(getPreparedStatement);
            dbManager.closeResultSet(resultSet);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public Book getEntity(String name) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            getPreparedStatement = connection.prepareStatement(SELECT_BOOK_BY_NAME);
            getPreparedStatement.setString(1,name);
            resultSet = getPreparedStatement.executeQuery();
            return getBook(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.CONNECTION_EXCEPTION, e);
            throw new DAOException(Constants.CONNECTION_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(getPreparedStatement);
            dbManager.closeResultSet(resultSet);
            dbManager.putConnection(connection);
        }
    }
    private Book getBook(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Book(resultSet.getInt(SQLConstants.ID_BOOK), resultSet.getString(SQLConstants.BOOK_NAME),
                    resultSet.getDate(SQLConstants.PUBLISHING_DATE), resultSet.getBoolean(SQLConstants.IS_TAKEN));
        }
        throw new DAOException(Constants.NO_BOOK_EXCEPTION);
    }

    @Override
    public Book putEntity(Book entity, int oldId) {
        Book oldBook = getEntity(oldId);
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(UPDATE_BOOK);
            getPreparedStatement.setString(1, entity.getNameBook());
            getPreparedStatement.setDate(2, entity.getPublishingDate());
            getPreparedStatement.setBoolean(3, entity.isTaken());
            getPreparedStatement.setInt(4, oldId);
            getPreparedStatement.executeUpdate();
            return oldBook;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.CONNECTION_EXCEPTION, e);
            throw new DAOException(Constants.CONNECTION_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(getPreparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public void deleteEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(DELETE_BOOK);
            getPreparedStatement.setInt(1, entityId);
            getPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.CONNECTION_EXCEPTION, e);
            throw new DAOException(Constants.CONNECTION_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(getPreparedStatement);
            dbManager.putConnection(connection);
        }
    }
}
