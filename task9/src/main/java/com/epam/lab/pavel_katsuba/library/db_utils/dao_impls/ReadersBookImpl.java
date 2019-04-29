package com.epam.lab.pavel_katsuba.library.db_utils.dao_impls;

import com.epam.lab.pavel_katsuba.library.Beans.Book;
import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.db_utils.DaoConstants;
import com.epam.lab.pavel_katsuba.library.interfaces.RelatesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReadersBookImpl implements RelatesDao<Reader, Book> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Book> BOOK_ROW_MAPPER = (ResultSet resultSet, int rowNum) ->
            Book.builder()
                    .id(resultSet.getInt("idBook"))
                    .nameBook(resultSet.getString("bookName"))
                    .count(resultSet.getInt("count"))
                    .publishingDate(resultSet.getDate("publishingDate"))
                    .isTaken(resultSet.getBoolean("isTaken"))
                    .build();

    @Autowired
    public ReadersBookImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Book> getBeans(int readerId) {
        return jdbcTemplate.query(DaoConstants.SELECT_BOOKS_FOR_READER, new Object[]{readerId}, BOOK_ROW_MAPPER);
    }

    @Override
    public void addBeans(List<Book> books, int readerId) {
        jdbcTemplate.batchUpdate(DaoConstants.INSERT_BOOK_FOR_READER, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Book book = books.get(i);
                ps.setInt(1, readerId);
                ps.setInt(2, book.getId());
            }

            @Override
            public int getBatchSize() {
                return books.size();
            }
        });
    }

    @Override
    public void putBeans(int readerId, int oldBookId, int newBookId) {
        jdbcTemplate.update(DaoConstants.UPDATE_BOOK_FOR_READER, newBookId, readerId, oldBookId);
    }

    @Override
    public void deleteBeanRelates(int readerId) {
        jdbcTemplate.update(DaoConstants.DELETE_BOOK_FOR_READER_BY_ID, readerId);
    }

    @Override
    public void delete(int entityId, int beanId) {
        jdbcTemplate.update(DaoConstants.DELETE_BOOK_FOR_READER, entityId, beanId);
    }

    @Override
    public boolean relateIsExist(int entityId, int beanId) {
        Integer count = jdbcTemplate.queryForObject(DaoConstants.IS_EXIST_BOOK_FOR_READER, new Object[]{entityId, beanId}, Integer.class);
        return count > 0;
    }
}
