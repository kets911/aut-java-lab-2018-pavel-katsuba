package com.epam.lab.pavel_katsuba.vneklasniki.dao_impl;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Present;
import com.epam.lab.pavel_katsuba.vneklasniki.exceptions.DataBaseException;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PresentDao implements VneklasnikiDao<Present> {
    private static final Logger logger = Logger.getLogger(PresentDao.class.getSimpleName());
    private DBManager dbManager;

    public PresentDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public int create(Present entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getAddPresentQuery());
            preparedStatement.setDate(1, Date.valueOf(entity.getCreateDate()));
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getOwnerId());
            preparedStatement.setInt(4, entity.getReceiverId());
            if (preparedStatement.executeUpdate() > 0) {
                return getEntityId(entity);
            }
            throw new DataBaseException("Present isn't added");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException("Present isn't added", e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public int getEntityId(Present entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getPresentIdQuery());
            preparedStatement.setDate(1, Date.valueOf(entity.getCreateDate()));
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getOwnerId());
            preparedStatement.setInt(4, entity.getReceiverId());
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
    public List<Present> getAllEntities() {
        List<Present> presents = new ArrayList<>();
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getAllPresentsQuery());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                presents.add(getPresent(resultSet));
            }
            return presents;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.closeResultSet(resultSet);
        }
    }

    @Override
    public Present getEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getPresentQuery());
            preparedStatement.setInt(1, entityId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getPresent(resultSet);
            }
            throw new DataBaseException("There isn't present with id: " + entityId);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.closeResultSet(resultSet);
        }
    }

    private Present getPresent(ResultSet resultSet) throws SQLException {
        Date creationDate = resultSet.getDate("creationDate");
        String description = resultSet.getString("description");
        int fromId = resultSet.getInt("fromId");
        int toId = resultSet.getInt("toId");
        return new Present(fromId, toId, description, creationDate.toLocalDate());
    }

    @Override
    public boolean putEntity(int oldEntityId, Present entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getUpdatePresentQuery());
            preparedStatement.setDate(1, Date.valueOf(entity.getCreateDate()));
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getOwnerId());
            preparedStatement.setInt(4, entity.getReceiverId());
            preparedStatement.setInt(5, oldEntityId);
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
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getDeletePresentQuery());
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
