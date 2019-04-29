package com.epam.lab.pavel_katsuba.vneklasniki.dao_impl;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Service;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.ServiceAccessRelate;
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

public class ServiceAccessDao implements VneklasnikiDao<ServiceAccessRelate> {
    private final Logger logger = Logger.getLogger(ServiceAccessDao.class.getSimpleName());
    private final DBManager dbManager;

    public ServiceAccessDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public int create(ServiceAccessRelate entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.ADD_SERVICE_ACCESS);
            preparedStatement.setInt(1, entity.getServiceId());
            preparedStatement.setInt(2, entity.getAccessId());
            if (preparedStatement.executeUpdate() > 0) {
                return getEntityId(entity);
            }
            throw new DataBaseException("Relate isn't added");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    public int getEntityId(ServiceAccessRelate entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.GET_SERVICE_ACCESS_ID);
            preparedStatement.setInt(1, entity.getServiceId());
            preparedStatement.setInt(2, entity.getAccessId());
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

    public List<ServiceAccessRelate> getAllEntities() {
        List<ServiceAccessRelate> relates = new ArrayList<>();
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.GET_ALL_SERVICE_ACCESSES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int serviceId = resultSet.getInt("serviceId");
                String login = resultSet.getString("login");
                String pass = resultSet.getString("pass");
                String countStatus = resultSet.getString("countStatus");
                int requestCount = resultSet.getInt("requestCount");
                int accessId = resultSet.getInt("accessId");
                String access = resultSet.getString("access");
                relates.add(new ServiceAccessRelate(new Service(login, pass, countStatus, requestCount), access, serviceId, accessId));
            }
            return relates;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closeResultSet(resultSet);
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    public ServiceAccessRelate getEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.GET_SERVICE_ACCESS);
            preparedStatement.setInt(1, entityId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int serviceId = resultSet.getInt("serviceId");
                String login = resultSet.getString("login");
                String pass = resultSet.getString("pass");
                String countStatus = resultSet.getString("countStatus");
                int requestCount = resultSet.getInt("requestCount");
                int accessId = resultSet.getInt("accessId");
                String access = resultSet.getString("access");
                return new ServiceAccessRelate(new Service(login, pass, countStatus, requestCount), access, serviceId, accessId);
            }
            throw new DataBaseException("There isn't relate with id: " + entityId);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closeResultSet(resultSet);
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    public boolean putEntity(int oldEntityId, ServiceAccessRelate entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.UPDATE_SERVICE_ACCESS);
            preparedStatement.setInt(1, entity.getServiceId());
            preparedStatement.setInt(2, entity.getAccessId());
            preparedStatement.setInt(3, oldEntityId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    public boolean deleteEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQuery.DELETE_SERVICE_ACCESS);
            preparedStatement.setInt(1, entityId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }
}
