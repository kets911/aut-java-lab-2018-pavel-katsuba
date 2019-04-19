package com.epam.lab.pavel_katsuba.library.db_utils.dao_impls;

import com.epam.lab.pavel_katsuba.library.Beans.Genre;
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
public class GenreImpl implements CrudDao<Genre> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Genre> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Genre(resultSet.getInt("idGenre"), resultSet.getString("genreName"));

    @Autowired
    public GenreImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addEntity(Genre entity) {
        jdbcTemplate.update(DaoConstants.INSERT_GENRE, entity.getGenreName());
    }

    @Override
    public List<Genre> getAllEntities() {
        return jdbcTemplate.query(DaoConstants.SELECT_GENRES, ROW_MAPPER);
    }

    @Override
    public Genre getEntity(int id) {
        return jdbcTemplate.queryForObject(DaoConstants.SELECT_GENRE_BY_ID, new Object[]{id}, ROW_MAPPER);
    }

    @Override
    public Genre getEntity(String name) {
        return jdbcTemplate.queryForObject(DaoConstants.SELECT_GENRE_BY_NAME, new Object[]{name}, ROW_MAPPER);
    }

    @Override
    public Genre putEntity(Genre entity, int oldId) {
        Genre oldEntity = getEntity(oldId);
        jdbcTemplate.update(DaoConstants.UPDATE_GENRE, entity.getGenreName(), oldId);
        return oldEntity;
    }

    @Override
    public void deleteEntity(int entityId) {
        jdbcTemplate.update(DaoConstants.DELETE_GENRE, entityId);
    }

    @Override
    public boolean isExist(String name) {
        Integer count = jdbcTemplate.queryForObject(DaoConstants.GENRE_IS_EXIST, new Object[]{name}, Integer.class);
        return count > 0;
    }
}
