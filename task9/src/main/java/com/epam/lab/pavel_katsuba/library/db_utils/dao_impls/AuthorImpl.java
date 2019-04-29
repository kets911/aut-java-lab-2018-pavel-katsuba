package com.epam.lab.pavel_katsuba.library.db_utils.dao_impls;

import com.epam.lab.pavel_katsuba.library.Beans.Author;
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
public class AuthorImpl implements CrudDao<Author> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Author> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Author(resultSet.getInt("idAuthor"), resultSet.getString("authorName"));

    @Autowired
    public AuthorImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addEntity(Author entity) {
        jdbcTemplate.update(DaoConstants.INSERT_AUTHOR, entity.getAuthorName());
    }

    @Override
    public List<Author> getAllEntities() {
        return jdbcTemplate.query(DaoConstants.SELECT_AUTHORS, ROW_MAPPER);
    }

    @Override
    public Author getEntity(int id) {
        return jdbcTemplate.queryForObject(DaoConstants.SELECT_AUTHOR_BY_ID, new Object[]{id}, ROW_MAPPER);
    }

    @Override
    public Author getEntity(String name) {
        return jdbcTemplate.queryForObject(DaoConstants.SELECT_AUTHOR_BY_NAME, new Object[]{name}, ROW_MAPPER);
    }

    @Override
    public Author putEntity(Author entity, int oldId) {
        Author oldAuthor = getEntity(oldId);
        jdbcTemplate.update(DaoConstants.UPDATE_AUTHOR, entity.getAuthorName(), oldAuthor.getAuthorName());
        return oldAuthor;
    }

    @Override
    public void deleteEntity(int entityId) {
        jdbcTemplate.update(DaoConstants.DELETE_AUTHOR, entityId);
    }

    @Override
    public boolean isExist(String name) {
        Integer count = jdbcTemplate.queryForObject(DaoConstants.AUTHOR_IS_EXIST, new Object[]{name}, Integer.class);
        return count > 0;
    }
}
