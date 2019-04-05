package com.epam.lab.pavel_katsuba.library.db_utils.dao_impls;

import com.epam.lab.pavel_katsuba.library.Beans.Genre;
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
public class GenreRelatesBookDAOImpl implements RelatesBookDao<Genre> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Genre> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Genre(resultSet.getInt("idGenre"), resultSet.getString("genreName"));

    @Autowired
    public GenreRelatesBookDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Genre> getEntities(int bookId) {
        return jdbcTemplate.query(DaoConstants.SELECT_GENRES_FOR_BOOK, new Object[] {bookId}, ROW_MAPPER);
    }

    @Override
    public void addEntities(List<Genre> entities, int bookId) {
        jdbcTemplate.batchUpdate(DaoConstants.INSERT_GENRE_RELATE, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Genre genre = entities.get(i);
                ps.setInt(1, bookId);
                ps.setInt(2, genre.getId());
            }

            @Override
            public int getBatchSize() {
                return entities.size();
            }
        });
    }

    @Override
    public void putEntities(int bookId, int oldEntityId, int newEntityId) {
        jdbcTemplate.update(DaoConstants.UPDATE_GENRE_RELATE, newEntityId, bookId, oldEntityId);
    }

    @Override
    public void deleteEntityRelate(int bookId) {
        jdbcTemplate.update(DaoConstants.DELETE_GENRE_RELATES_FOR_BOOK, bookId);
    }

    @Override
    public void delete(int entityId, int bookId) {
        jdbcTemplate.update(DaoConstants.DELETE_ONE_GENRE_RELATE, bookId, entityId);
    }
}
