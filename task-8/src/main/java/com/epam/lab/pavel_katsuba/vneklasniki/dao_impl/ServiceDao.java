package com.epam.lab.pavel_katsuba.vneklasniki.dao_impl;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Service;
import com.epam.lab.pavel_katsuba.vneklasniki.db_utils.SqlQuery;
import com.epam.lab.pavel_katsuba.vneklasniki.exceptions.DataBaseException;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceDao implements VneklasnikiDao<Service> {
    private final Logger logger = Logger.getLogger(ServiceDao.class.getSimpleName());
    private final DBManager dbManager;

    public ServiceDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public int create(Service entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.ADD_SERVICE);
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getStatus());
            preparedStatement.setInt(4, entity.getRequestCount());
            if (preparedStatement.executeUpdate() > 0) {
                return getEntityId(entity);
            }
            throw new DataBaseException("Service isn't added");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public int getEntityId(Service entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.GET_SERVICE_ID);
            preparedStatement.setString(1, entity.getLogin());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            return Constants.NAN_ID;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Service> getAllEntities() {
        List<Service> services = new ArrayList<>();
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.GET_ALL_SERVICES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String password = resultSet.getString("pass");
                String countStatus = resultSet.getString("countStatus");
                int requestCount = resultSet.getInt("requestCount");
                services.add(new Service(login, password, countStatus, requestCount));
            }
            return services;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.closeResultSet(resultSet);
        }
    }

    @Override
    public Service getEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.GET_SERVICE);
            preparedStatement.setInt(1, entityId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString("login");
                String password = resultSet.getString("pass");
                String countStatus = resultSet.getString("countStatus");
                int requestCount = resultSet.getInt("requestCount");
                return new Service(login, password, countStatus, requestCount);
            }
            throw new DataBaseException("There isn't service with id: " + entityId);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.closeResultSet(resultSet);
        }
    }

    @Override
    public boolean putEntity(int oldEntityId, Service entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.UPDATE_SERVICE);
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, oldEntityId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public boolean deleteEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.DELETE_SERVICE);
            preparedStatement.setInt(1, entityId);
           return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
        }
    }
}
