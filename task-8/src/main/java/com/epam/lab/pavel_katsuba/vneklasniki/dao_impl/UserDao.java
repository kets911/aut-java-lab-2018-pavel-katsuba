package com.epam.lab.pavel_katsuba.vneklasniki.dao_impl;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.User;
import com.epam.lab.pavel_katsuba.vneklasniki.exceptions.DataBaseException;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDao implements VneklasnikiDao<User> {
    private static final Logger logger = Logger.getLogger(UserDao.class.getSimpleName());
    private DBManager dbManager;

    public UserDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public int create(User user) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getAddUserQuery());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthday()));
            if (preparedStatement.executeUpdate() > 0) {
                return getEntityId(user);
            }
            throw new DataBaseException("User isn't added");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException("User isn't added", e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public int getEntityId(User user) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getUserIDQuery());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthday()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            return Constants.NAN_ID;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closeResultSet(resultSet);
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public List<User> getAllEntities() {
        List<User> users = new ArrayList<>();
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getReadAllUsersQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                users.add(new User(id, name, surname, birthday));
            }
            return users;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closeResultSet(resultSet);
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public User getEntity(int userId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getReadUserQuery());
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                return new User(id, name, surname, birthday);
            }
            throw new DataBaseException("There isn't user with id = " + userId);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closeResultSet(resultSet);
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public boolean putEntity(int oldUserId, User user) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getUpdateUserQuery());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthday()));
            preparedStatement.setInt(4, oldUserId);
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public boolean deleteEntity(int userId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getDeleteUserQuery());
            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }
}
