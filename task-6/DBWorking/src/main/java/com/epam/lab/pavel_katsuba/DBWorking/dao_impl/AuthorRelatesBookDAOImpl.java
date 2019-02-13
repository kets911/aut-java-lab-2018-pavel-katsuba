package com.epam.lab.pavel_katsuba.DBWorking.dao_impl;


import com.epam.lab.pavel_katsuba.DBWorking.Constants;
import com.epam.lab.pavel_katsuba.DBWorking.SQLConstants;
import com.epam.lab.pavel_katsuba.DBWorking.beans.Author;
import com.epam.lab.pavel_katsuba.DBWorking.databases.DBManager;
import com.epam.lab.pavel_katsuba.DBWorking.exceptions.DAOException;
import com.epam.lab.pavel_katsuba.DBWorking.interfaces.RelatesBookDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorRelatesBookDAOImpl implements RelatesBookDAO<Author> {
    private static final Logger LOGGER = Logger.getLogger(AuthorRelatesBookDAOImpl.class.getName());
    private static final String INSERT_AUTHOR_RELATE = "insert ignore into authorsOfBooks values (?, ?)";
    private static final String UPDATE_AUTHOR_RELATE = "update authorsOfBooks set authorsOfBooks.authorId = (?) " +
            "where bookId = (?) and authorId = (?)";
    private static final String DELETE_AUTHOR_RELATE = "delete from authorsOfBooks where bookId = (?)";
    private static final String DELETE_ONE_AUTHOR_RELATE = "delete from authorsOfBooks where bookId = (?) " +
            "and authorId = (?)";
    private final DBManager dbManager;
    private static final String SELECT_AUTHORS = "select * from author left join authorsOfBooks " +
            "on author.idAuthor = authorsOfBooks.authorId where authorsOfBooks.bookId = (?)";

    public AuthorRelatesBookDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Author> getEntities(int bookId) {
            List<Author> authors = new ArrayList<>();
            Connection connection = dbManager.getConnection();
            PreparedStatement getPreparedStatement = null;
            ResultSet resultSet = null;
            try {
                getPreparedStatement = connection.prepareStatement(SELECT_AUTHORS);
                getPreparedStatement.setInt(1, bookId);
                resultSet = getPreparedStatement.executeQuery();
                while (resultSet.next()){
                    authors.add(new Author(resultSet.getInt(SQLConstants.ID_AUTHOR),
                            resultSet.getString(SQLConstants.AUTHOR_NAME)));
                }
                return authors;
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
    public void addEntities(List<Author> authors, int bookId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(INSERT_AUTHOR_RELATE);
            for (Author author : authors){
                getPreparedStatement.setInt(1, bookId);
                getPreparedStatement.setInt(2, author.getId());
                getPreparedStatement.addBatch();
            }
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
    public void putEntities(int bookId, int oldAuthorId, int newAuthorId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(UPDATE_AUTHOR_RELATE);
            getPreparedStatement.setInt(1, newAuthorId);
            getPreparedStatement.setInt(2, bookId);
            getPreparedStatement.setInt(3, oldAuthorId);
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
    public void deleteEntityRelate(int bookId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(DELETE_AUTHOR_RELATE);
            getPreparedStatement.setInt(1, bookId);
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
    public void delete(int authorId, int bookId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(DELETE_ONE_AUTHOR_RELATE);
            getPreparedStatement.setInt(1, bookId);
            getPreparedStatement.setInt(2, authorId);
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
