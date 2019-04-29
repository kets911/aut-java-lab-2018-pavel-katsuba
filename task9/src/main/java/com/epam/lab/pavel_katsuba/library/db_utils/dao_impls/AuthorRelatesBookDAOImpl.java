package com.epam.lab.pavel_katsuba.library.db_utils.dao_impls;

import com.epam.lab.pavel_katsuba.library.Beans.Author;
import com.epam.lab.pavel_katsuba.library.Beans.Book;
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
public class AuthorRelatesBookDAOImpl implements RelatesDao<Book, Author> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Author> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Author(resultSet.getInt("idAuthor"), resultSet.getString("authorName"));

    @Autowired
    public AuthorRelatesBookDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Author> getBeans(int bookId) {
        return jdbcTemplate.query(DaoConstants.SELECT_AUTHORS_FOR_BOOK, new Object[]{bookId}, ROW_MAPPER);
    }

    @Override
    public void addBeans(List<Author> entities, int bookId) {
        jdbcTemplate.batchUpdate(DaoConstants.INSERT_AUTHOR_RELATE, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Author author = entities.get(i);
                ps.setInt(1, bookId);
                ps.setInt(2, author.getId());
            }

            @Override
            public int getBatchSize() {
                return entities.size();
            }
        });
    }

    @Override
    public void putBeans(int bookId, int oldEntityId, int newEntityId) {
        jdbcTemplate.update(DaoConstants.UPDATE_AUTHOR_RELATE, newEntityId, bookId, oldEntityId);
    }

    @Override
    public void deleteBeanRelates(int bookId) {
        jdbcTemplate.update(DaoConstants.DELETE_AUTHOR_RELATE_FOR_BOOK, bookId);
    }

    @Override
    public void delete(int entityId, int bookId) {
        jdbcTemplate.update(DaoConstants.DELETE_ONE_AUTHOR_RELATE, bookId, entityId);
    }

    @Override
    public boolean relateIsExist(int bookId, int authorId) {
        Integer count = jdbcTemplate.queryForObject(DaoConstants.AUTHOR_RELATE_IS_EXIST, new Object[]{bookId, authorId}, Integer.class);
        return count > 0;
    }
}
