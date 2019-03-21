package com.epam.lab.pavel_katsuba.vneklasniki.dao_impl;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Friend;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.User;
import com.epam.lab.pavel_katsuba.vneklasniki.exceptions.DataBaseException;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FriendDao implements VneklasnikiDao<Friend> {
    private static final Logger logger = Logger.getLogger(AccessDao.class.getSimpleName());
    private DBManager dbManager;

    public FriendDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public int create(Friend entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getADD_FRIED());
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getFriendId());
            preparedStatement.setDate(3, Date.valueOf(entity.getCreateDate()));
            if (preparedStatement.executeUpdate() > 0) {
                return getEntityId(entity);
            }
            throw new DataBaseException("Friend isn't added");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException("Friend isn't added", e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public int getEntityId(Friend friend) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_FRIED_ID());
            preparedStatement.setInt(1, friend.getUserId());
            preparedStatement.setInt(2, friend.getFriendId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("friendsId");
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
    public List<Friend> getAllEntities() {
        Map<Integer, Friend> friends = new HashMap<>();
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_ALL_FRIEDS());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Friend friend = getFriend(resultSet);
                int key = (friend.getUserId() + 31) * (friend.getFriendId() + 31);
                friends.put(key, friend);
            }
            return new ArrayList<>(friends.values());
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.closeResultSet(resultSet);
        }
    }

    @Override
    public Friend getEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_FRIED());
            preparedStatement.setInt(1, entityId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getFriend(resultSet);
            }
            throw new DataBaseException("There isn't friend relate with id: " + entityId);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
            dbManager.closeResultSet(resultSet);
        }
    }

    private Friend getFriend(ResultSet resultSet) throws SQLException {
        int selfUserId = resultSet.getInt("selfUserId");
        int friendUserId = resultSet.getInt("friendUserId");
        Date creationDate = resultSet.getDate("creationDate");

        Date userFriendBirthday = resultSet.getDate("userFriendBirthday");
        String userFriendName = resultSet.getString("userFriendName");
        String userFriendSurname = resultSet.getString("userFriendSurname");
        User userFriend = new User(friendUserId, userFriendName, userFriendSurname, userFriendBirthday.toLocalDate());

        Date userBirthday = resultSet.getDate("userBirthday");
        String userName = resultSet.getString("userName");
        String userSurname = resultSet.getString("userSurname");
        User user = new User(selfUserId, userName, userSurname, userBirthday.toLocalDate());
        return new Friend(selfUserId, friendUserId, user, userFriend, creationDate.toLocalDate());
    }

    @Override
    public boolean putEntity(int oldEntityId, Friend entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getUPDATE_FRIED());
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getFriendId());
            preparedStatement.setDate(3, Date.valueOf(entity.getCreateDate()));
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
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getDELETE_FRIED());
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
