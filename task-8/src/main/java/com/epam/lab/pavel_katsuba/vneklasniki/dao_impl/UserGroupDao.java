package com.epam.lab.pavel_katsuba.vneklasniki.dao_impl;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Group;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.GroupUserRelate;
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


public class UserGroupDao implements VneklasnikiDao<GroupUserRelate> {
    private static final Logger logger = Logger.getLogger(GroupDao.class.getSimpleName());
    private DBManager dbManager;

    public UserGroupDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }


    @Override
    public int create(GroupUserRelate entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getADD_RELATE_GROUP_USER());
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getGroupId());
            preparedStatement.setDate(3, Date.valueOf(entity.getSubscribeDate()));
            if (preparedStatement.executeUpdate() > 0) {
                return getEntityId(entity);
            }
            throw new DataBaseException("Relate isn't added");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public int getEntityId(GroupUserRelate entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_GROUP_USER_ID());
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getGroupId());
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
    public List<GroupUserRelate> getAllEntities() {
        List<GroupUserRelate> relates = new ArrayList<>();
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_ALL_GROUPS_USERS());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                int groupId = resultSet.getInt("groupId");
                LocalDate subscribeDate = resultSet.getDate("subscribeDate").toLocalDate();
                String groupName = resultSet.getString("groupName");
                String author = resultSet.getString("author");
                LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
                Group group = new Group(groupId, groupName, creationDate, author);
                String userName = resultSet.getString("name");
                String userSurname = resultSet.getString("surname");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                User user = new User(userId, userName, userSurname, birthday);
                relates.add(new GroupUserRelate(userId, groupId, user, group, subscribeDate));
            }
            return relates;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.closeResultSet(resultSet);
        }
    }

    @Override
    public GroupUserRelate getEntity(int id) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_GROUP_USERS());
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                int groupId = resultSet.getInt("groupId");
                LocalDate subscribeDate = resultSet.getDate("subscribeDate").toLocalDate();
                String groupName = resultSet.getString("groupName");
                String author = resultSet.getString("author");
                LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
                Group group = new Group(groupId, groupName, creationDate, author);
                String userName = resultSet.getString("name");
                String userSurname = resultSet.getString("surname");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                User user = new User(userId, userName, userSurname, birthday);
                return new GroupUserRelate(userId, groupId, user, group, subscribeDate);
            }
            throw new DataBaseException("There aren't relates with id: " + id);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.closeResultSet(resultSet);
        }
    }

    @Override
    public boolean putEntity(int oldEntityId, GroupUserRelate entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getUPDATE_RELATE_GROUP_USER());
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getGroupId());
            preparedStatement.setDate(3, Date.valueOf(entity.getSubscribeDate()));
            preparedStatement.setInt(4, oldEntityId);
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
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getDELETE_RELATE_GROUP_USER());
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
