package com.epam.lab.pavel_katsuba.library.db_utils.dao_impls;

import com.epam.lab.pavel_katsuba.library.Beans.Author;
import com.epam.lab.pavel_katsuba.library.db_utils.DaoConstants;
import com.epam.lab.pavel_katsuba.library.interfaces.RelatesBookDao;
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
public class AuthorRelatesBookDAOImpl implements RelatesBookDao<Author> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Author> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Author(resultSet.getInt("idAuthor"), resultSet.getString("authorName"));

    @Autowired
    public AuthorRelatesBookDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Author> getEntities(int bookId) {
        return jdbcTemplate.query(DaoConstants.SELECT_AUTHORS_FOR_BOOK, new Object[]{bookId}, ROW_MAPPER);
    }

    @Override
    public void addEntities(List<Author> entities, int bookId) {
        jdbcTemplate.batchUpdate(DaoConstants.SELECT_AUTHORS_FOR_BOOK, new BatchPreparedStatementSetter() {

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
    public void putEntities(int bookId, int oldEntityId, int newEntityId) {
        jdbcTemplate.update(DaoConstants.UPDATE_AUTHOR_RELATE, newEntityId, bookId, oldEntityId);
    }

    @Override
    public void deleteEntityRelate(int bookId) {
        jdbcTemplate.update(DaoConstants.DELETE_AUTHOR_RELATE_FOR_BOOK, bookId);
    }

    @Override
    public void delete(int entityId, int bookId) {
        jdbcTemplate.update(DaoConstants.DELETE_ONE_AUTHOR_RELATE, bookId, entityId);
    }
}
