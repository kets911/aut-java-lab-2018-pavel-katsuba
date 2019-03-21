package com.epam.lab.pavel_katsuba.vneklasniki.dao_impl;

import com.epam.lab.pavel_katsuba.vneklasniki.Constants;
import com.epam.lab.pavel_katsuba.vneklasniki.beans.Message;
import com.epam.lab.pavel_katsuba.vneklasniki.exceptions.DataBaseException;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.DBManager;
import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.VneklasnikiDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MessageDao implements VneklasnikiDao<Message> {
    private static final Logger logger = Logger.getLogger(MessageDao.class.getSimpleName());
    private DBManager dbManager;

    public MessageDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public int create(Message entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getADD_MESSAGE());
            preparedStatement.setInt(1, entity.getFromUserId());
            preparedStatement.setInt(2, entity.getToUserId());
            preparedStatement.setDate(3, Date.valueOf(entity.getCreateDate()));
            preparedStatement.setString(4, entity.getMessage());
            if (preparedStatement.executeUpdate() > 0) {
                return getEntityId(entity);
            }
            throw new DataBaseException("Message isn't added");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException("Message isn't added", e);
        } finally {
            dbManager.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public int getEntityId(Message message) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getMessageIDQuery());
            preparedStatement.setInt(1, message.getFromUserId());
            preparedStatement.setInt(2, message.getToUserId());
            preparedStatement.setDate(3, Date.valueOf(message.getCreateDate()));
            preparedStatement.setString(4, message.getMessage());
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
        }
    }

    @Override
    public List<Message> getAllEntities() {
        List<Message> messages = new ArrayList<>();
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_ALL_MESSAGES());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userFromId = resultSet.getInt("userFromId");
                int userToId = resultSet.getInt("userToId");
                LocalDate date = resultSet.getDate("creationDate").toLocalDate();
                String message = resultSet.getString("message");
                messages.add(new Message(id, userFromId, userToId, date, message));
            }
            return messages;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closeResultSet(resultSet);
            dbManager.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public Message getEntity(int entityId) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getGET_MESSAGE());
            preparedStatement.setInt(1, entityId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userFromId = resultSet.getInt("userFromId");
                int userToId = resultSet.getInt("userToId");
                LocalDate date = resultSet.getDate("creationDate").toLocalDate();
                String message = resultSet.getString("message");
                return new Message(id, userFromId, userToId, date, message);
            }
            throw new DataBaseException("There isn't message with id: " + entityId);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new DataBaseException(e);
        } finally {
            dbManager.closeResultSet(resultSet);
            dbManager.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public boolean putEntity(int oldEntityId, Message entity) {
        Connection connection = dbManager.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getUPDATE_MESSAGE());
            preparedStatement.setInt(1, entity.getToUserId());
            preparedStatement.setInt(2, entity.getFromUserId());
            preparedStatement.setDate(3, Date.valueOf(entity.getCreateDate()));
            preparedStatement.setString(4, entity.getMessage());
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
            preparedStatement = connection.prepareStatement(dbManager.getQuery().getDELETE_MESSAGE());
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
