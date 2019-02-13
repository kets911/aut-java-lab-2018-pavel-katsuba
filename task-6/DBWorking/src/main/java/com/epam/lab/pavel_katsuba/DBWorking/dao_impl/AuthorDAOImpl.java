package com.epam.lab.pavel_katsuba.DBWorking.dao_impl;

import com.epam.lab.pavel_katsuba.DBWorking.Constants;
import com.epam.lab.pavel_katsuba.DBWorking.SQLConstants;
import com.epam.lab.pavel_katsuba.DBWorking.beans.Author;
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

public class AuthorDAOImpl implements LibraryDAO<Author> {
    private static final Logger LOGGER = Logger.getLogger(AuthorDAOImpl.class.getName());
    private final DBManager dbManager;
    private static final String INSERT_AUTHOR = "insert ignore into author (authorName) values(?)";
    private static final String SELECT_AUTHOR = "select * from author";
    private static final String SELECT_AUTHOR_BY_ID = "select * from author where author.idAuthor = (?)";
    private static final String SELECT_AUTHOR_BY_NAME = "select * from author where author.authorName = (?)";
    private static final String UPDATE_AUTHOR = "update author set author.authorName = (?) where authorName = (?)";
    private static final String DELETE_AUTHOR = "delete from author where idAuthor = (?)";

    public AuthorDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void addEntity(Author entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement addPreparedStatement = null;
        try {
            addPreparedStatement = connection.prepareStatement(INSERT_AUTHOR);
            addPreparedStatement.setString(1, entity.getAuthorName());
            addPreparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.CONNECTION_EXCEPTION, e);
            throw new DAOException(Constants.CONNECTION_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(addPreparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public List<Author> getAllEntities() {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<Author> authors = new ArrayList<>();
            getPreparedStatement = connection.prepareStatement(SELECT_AUTHOR);
            resultSet = getPreparedStatement.executeQuery();
            while (resultSet.next()) {
                authors.add(new Author(resultSet.getInt(SQLConstants.ID_AUTHOR), resultSet.getString(SQLConstants.AUTHOR_NAME)));
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
    public Author getEntity(int id) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            getPreparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID);
            getPreparedStatement.setInt(1, id);
            resultSet = getPreparedStatement.executeQuery();
            return getAuthor(resultSet);
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
    public Author getEntity(String name) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            getPreparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAME);
            getPreparedStatement.setString(1, name);
            resultSet = getPreparedStatement.executeQuery();
            return getAuthor(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.CONNECTION_EXCEPTION, e);
            throw new DAOException(Constants.CONNECTION_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(getPreparedStatement);
            dbManager.closeResultSet(resultSet);
            dbManager.putConnection(connection);
        }
    }

    private Author getAuthor(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Author(resultSet.getInt(SQLConstants.ID_AUTHOR), resultSet.getString(SQLConstants.AUTHOR_NAME));
        }
        throw new DAOException(Constants.GET_AUTHOR_EXCEPTION);
    }

    @Override
    public Author putEntity(Author entity, int oldId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement putPreparedStatement = null;
        try {
            Author oldAuthor = getEntity(oldId);
            putPreparedStatement = connection.prepareStatement(UPDATE_AUTHOR);
            putPreparedStatement.setString(1, entity.getAuthorName());
            putPreparedStatement.setInt(2, oldId);
            putPreparedStatement.executeUpdate();
            return oldAuthor;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOST_CONNECT_EXCEPTION, e);
            throw new DAOException(Constants.LOST_CONNECT_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(putPreparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public void deleteEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement deletePreparedStatement = null;
        try {
            deletePreparedStatement = connection.prepareStatement(DELETE_AUTHOR);
            deletePreparedStatement.setInt(1, entityId);
            deletePreparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.DELETE_EXCEPTION, e);
            throw new DAOException(Constants.DELETE_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(deletePreparedStatement);
            dbManager.putConnection(connection);
        }
    }
}
