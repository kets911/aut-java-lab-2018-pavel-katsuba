package com.epam.lab.pavel_katsuba.library.db_utils.dao_impls;

import com.epam.lab.pavel_katsuba.library.Beans.Reader;
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
public class ReaderImpl implements CrudDao<Reader> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Reader> ROW_MAPPER = (ResultSet resultSet, int rowNum) ->
            Reader.builder()
                    .id(resultSet.getInt("idReader"))
                    .username(resultSet.getString("readerName"))
                    .password(resultSet.getString("passw"))
                    .accountNonExpired(resultSet.getBoolean("accountNonExpired"))
                    .accountNonLocked(resultSet.getBoolean("accountNonLocked"))
                    .credentialsNonExpired(resultSet.getBoolean("credentialsNonExpired"))
                    .enabled(resultSet.getBoolean("enabled"))
                    .build();

    @Autowired
    public ReaderImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addEntity(Reader entity) {
        jdbcTemplate.update(DaoConstants.INSERT_READER, entity.getUsername(), entity.getPassword(), entity.isAccountNonExpired(),
                entity.isAccountNonLocked(), entity.isCredentialsNonExpired(), entity.isEnabled());
    }

    @Override
    public List<Reader> getAllEntities() {
        return jdbcTemplate.query(DaoConstants.SELECT_READERS, ROW_MAPPER);
    }

    @Override
    public Reader getEntity(int id) {
        return jdbcTemplate.queryForObject(DaoConstants.SELECT_READER_BY_ID, new Object[]{id}, ROW_MAPPER);
    }

    @Override
    public Reader getEntity(String name) {
        return jdbcTemplate.queryForObject(DaoConstants.SELECT_READER_BY_NAME, new Object[]{name}, ROW_MAPPER);
    }

    @Override
    public Reader putEntity(Reader entity, int oldId) {
        Reader oldReader = getEntity(oldId);
        jdbcTemplate.update(DaoConstants.UPDATE_READER, entity.getUsername(), entity.getPassword(), entity.isAccountNonExpired(),
                entity.isAccountNonLocked(), entity.isCredentialsNonExpired(), entity.isEnabled(), oldId);
        return oldReader;
    }

    @Override
    public void deleteEntity(int entityId) {
        jdbcTemplate.update(DaoConstants.DELETE_READER, entityId);
    }

    @Override
    public boolean isExist(String name) {
        Integer count = jdbcTemplate.queryForObject(DaoConstants.READER_IS_EXIST, new Object[]{name}, Integer.class);
        return count > 0;
    }
}
