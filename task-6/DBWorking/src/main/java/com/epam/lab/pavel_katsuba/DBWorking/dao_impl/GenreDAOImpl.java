package com.epam.lab.pavel_katsuba.DBWorking.dao_impl;

import com.epam.lab.pavel_katsuba.DBWorking.Constants;
import com.epam.lab.pavel_katsuba.DBWorking.SQLConstants;
import com.epam.lab.pavel_katsuba.DBWorking.beans.Genre;
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

public class GenreDAOImpl implements LibraryDAO<Genre> {
    private static final Logger LOGGER = Logger.getLogger(GenreDAOImpl.class.getName());
    private final DBManager dbManager;

    private static final String INSERT_GENRE = "insert ignore into genre (genreName) values(?)";
    private static final String SELECT_GENRE = "select * from genre";
    private static final String SELECT_GENRE_BY_ID = "select * from genre where genre.idGenre = (?)";
    private static final String SELECT_GENRE_BY_NAME = "select * from genre where genre.genreName = (?)";
    private static final String UPDATE_GENRE = "update books set genre.genreName = (?) where idGenre = (?)";
    private static final String DELETE_GENRE = "delete from genre where idGenre = (?)";

    public GenreDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void addEntity(Genre entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement addPreparedStatement = null;
        try {
            addPreparedStatement = connection.prepareStatement(INSERT_GENRE);
            addPreparedStatement.setString(1, entity.getGenreName());
            addPreparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.BOOK_ADDED_EXCEPTION, e);
            throw new DAOException(Constants.BOOK_ADDED_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(addPreparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public List<Genre> getAllEntities() {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<Genre> genres = new ArrayList<>();
            getPreparedStatement = connection.prepareStatement(SELECT_GENRE);
            resultSet = getPreparedStatement.executeQuery();
            while (resultSet.next()) {
                genres.add(new Genre(resultSet.getInt(SQLConstants.ID_GENRE) , resultSet.getString(SQLConstants.GENRE_NAME)));
            }
            return genres;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.BOOK_GOT_EXCEPTION, e);
            throw new DAOException(Constants.BOOK_GOT_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(getPreparedStatement);
            dbManager.closeResultSet(resultSet);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public Genre getEntity(int id) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            getPreparedStatement = connection.prepareStatement(SELECT_GENRE_BY_ID);
            getPreparedStatement.setInt(1, id);
            resultSet = getPreparedStatement.executeQuery();
            return getGenre(resultSet);
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
    public Genre getEntity(String name) {
        Connection connection = dbManager.getConnection();
        PreparedStatement getPreparedStatement = null;
        ResultSet resultSet = null;
        try {
            getPreparedStatement = connection.prepareStatement(SELECT_GENRE_BY_NAME);
            getPreparedStatement.setString(1, name);
            resultSet = getPreparedStatement.executeQuery();
            return getGenre(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.CONNECTION_EXCEPTION, e);
            throw new DAOException(Constants.CONNECTION_EXCEPTION, e);
        } finally {
            dbManager.closePreparedStatement(getPreparedStatement);
            dbManager.closeResultSet(resultSet);
            dbManager.putConnection(connection);
        }
    }

    private Genre getGenre(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Genre(resultSet.getInt(SQLConstants.ID_GENRE), resultSet.getString(SQLConstants.GENRE_NAME));
        }
        throw new DAOException(Constants.GET_GENRE_EXCEPTION);
    }

    @Override
    public Genre putEntity(Genre entity, int oldId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement putPreparedStatement = null;
        try {
            Genre oldGenre = getEntity(oldId);
            putPreparedStatement = connection.prepareStatement(UPDATE_GENRE);
            putPreparedStatement.setString(1, entity.getGenreName());
            putPreparedStatement.setInt(2, oldId);
            putPreparedStatement.executeUpdate();
            return oldGenre;
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
            deletePreparedStatement = connection.prepareStatement(DELETE_GENRE);
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
