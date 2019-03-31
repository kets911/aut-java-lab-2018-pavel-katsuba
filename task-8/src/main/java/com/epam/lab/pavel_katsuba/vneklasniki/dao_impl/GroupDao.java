package com.epam.lab.pavel_katsuba.vneklasniki.dao_impl;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Group;
import com.epam.lab.pavel_katsuba.vneklasniki.exceptions.DataBaseException;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GroupDao implements VneklasnikiDao<Group> {
    private static final Logger logger = Logger.getLogger(GroupDao.class.getSimpleName());
    private DBManager dbManager;

    public GroupDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public int create(Group group) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getADD_GROUP());
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.getAuthor());
            preparedStatement.setDate(3, Date.valueOf(group.getCreationDate()));
            if (preparedStatement.executeUpdate() > 0) {
                return getEntityId(group);
            }
            throw new DataBaseException("Group isn't added");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException("Group isn't added", e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public int getEntityId(Group group) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_GROUP_ID());
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.getAuthor());
            preparedStatement.setDate(3, Date.valueOf(group.getCreationDate()));
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
    public List<Group> getAllEntities() {
        List<Group> groups = new ArrayList<>();
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_ALL_GROUP());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String groupName = resultSet.getString("groupName");
                String author = resultSet.getString("author");
                LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
                groups.add(new Group(id, groupName, creationDate, author));
            }
            return groups;
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
    public Group getEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_GROUP());
            preparedStatement.setInt(1, entityId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String groupName = resultSet.getString("groupName");
                String author = resultSet.getString("author");
                LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
                return new Group(id, groupName, creationDate, author);
            }
            throw new DataBaseException("There isn't group with id: " + entityId);
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
    public boolean putEntity(int oldEntityId, Group entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getUPDATE_GROUP());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getAuthor());
            preparedStatement.setDate(3, Date.valueOf(entity.getCreationDate()));
            preparedStatement.setInt(4, oldEntityId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.putConnection(connection);
        }
    }

    @Override
    public boolean deleteEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getDELETE_GROUP());
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
