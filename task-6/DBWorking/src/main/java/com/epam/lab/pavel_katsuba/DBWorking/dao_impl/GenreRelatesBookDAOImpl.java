package com.epam.lab.pavel_katsuba.DBWorking.dao_impl;

import com.epam.lab.pavel_katsuba.DBWorking.Constants;
import com.epam.lab.pavel_katsuba.DBWorking.SQLConstants;
import com.epam.lab.pavel_katsuba.DBWorking.beans.Genre;
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

public class GenreRelatesBookDAOImpl implements RelatesBookDAO<Genre> {
    private static final Logger LOGGER = Logger.getLogger(GenreRelatesBookDAOImpl.class.getName());
    private static final String INSERT_GENRE_RELATE = "insert ignore into genresOfBooks values (?, ?)";
    private static final String UPDATE_GENRE_RELATE = "update genresOfBooks set genresOfBooks.genreId = (?) " +
            "where bookId = (?) and genreId = (?)";
    private static final String DELETE_GENRE_RELATE = "delete from genresOfBooks where bookId = (?)";
    private static final String DELETE_ONE_GENRE_RELATE = "delete from genresOfBooks where bookId = (?) " +
            "and genreId = (?)";
    private final DBManager dbManager;
    private static final String SELECT_GENRES = "select * from genre left join genresOfBooks " +
            "on genre.idGenre = genresOfBooks.genreId where genresOfBooks.bookId = (?)";

    public GenreRelatesBookDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Genre> getEntities(int bookId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<Genre> genres = new ArrayList<>();
            getPreparedStatement = connection.prepareStatement(SELECT_GENRES);
            getPreparedStatement.setInt(1, bookId);
            resultSet = getPreparedStatement.executeQuery();
            while (resultSet.next()) {
                genres.add(new Genre(resultSet.getInt(SQLConstants.ID_GENRE), resultSet.getString(SQLConstants.GENRE_NAME)));
            }
            return genres;
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
    public void addEntities(List<Genre> genres, int bookId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(INSERT_GENRE_RELATE);
            for (Genre genre : genres){
                getPreparedStatement.setInt(1, bookId);
                getPreparedStatement.setInt(2, genre.getId());
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
    public void putEntities(int bookId, int oldEntityId, int newEntityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(UPDATE_GENRE_RELATE);
            getPreparedStatement.setInt(1, newEntityId);
            getPreparedStatement.setInt(2, bookId);
            getPreparedStatement.setInt(3, oldEntityId);
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
            getPreparedStatement = connection.prepareStatement(DELETE_GENRE_RELATE);
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
    public void delete(int entityId, int bookId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        try {
            getPreparedStatement = connection.prepareStatement(DELETE_ONE_GENRE_RELATE);
            getPreparedStatement.setInt(1, bookId);
            getPreparedStatement.setInt(2, entityId);
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
