package com.epam.lab.pavel_katsuba.library.db_utils.dao_impls;

import com.epam.lab.pavel_katsuba.library.Beans.Book;
import com.epam.lab.pavel_katsuba.library.db_utils.DaoConstants;
import com.epam.lab.pavel_katsuba.library.interfaces.CrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class BookImpl implements CrudDao<Book> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Book> ROW_MAPPER = (ResultSet resultSet, int rowNum) ->
            Book.builder()
                    .id(resultSet.getInt("idBook"))
                    .nameBook(resultSet.getString("bookName"))
                    .count(resultSet.getInt("count"))
                    .publishingDate(resultSet.getDate("publishingDate"))
                    .isTaken(resultSet.getBoolean("isTaken"))
                    .build();

    @Autowired
    public BookImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addEntity(Book entity) {
        jdbcTemplate.update(DaoConstants.INSERT_BOOK, entity.getNameBook(), entity.getPublishingDate(), entity.getCount(), entity.isTaken());
    }

    @Override
    public List<Book> getAllEntities() {
        return jdbcTemplate.query(DaoConstants.SELECT_ALL_BOOKS, ROW_MAPPER);
    }

    @Override
    public Book getEntity(int id) {
        return jdbcTemplate.queryForObject(DaoConstants.SELECT_BOOK_BY_ID, new Object[]{id}, ROW_MAPPER);
    }

    @Override
    public Book getEntity(String name) {
        return jdbcTemplate.queryForObject(DaoConstants.SELECT_BOOK_BY_NAME, new Object[]{name}, ROW_MAPPER);
    }

    @Override
    public Book putEntity(Book entity, int oldId) {
        Book oldEntity = getEntity(oldId);
        jdbcTemplate.update(DaoConstants.UPDATE_BOOK, entity.getNameBook(), entity.getPublishingDate(), entity.getCount(), entity.isTaken(), oldId);
        return oldEntity;
    }

    @Override
    public void deleteEntity(int entityId) {
        jdbcTemplate.update(DaoConstants.DELETE_BOOK, entityId);
    }

    @Override
    public boolean isExist(String name) {
        Integer count = jdbcTemplate.queryForObject(DaoConstants.BOOK_IS_EXIST, new Object[]{name}, Integer.class);
        return count > 0;
    }
}
