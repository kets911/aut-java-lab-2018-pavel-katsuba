package com.epam.lab.pavel_katsuba.library.db_utils.dao_impls;

import com.epam.lab.pavel_katsuba.library.Beans.Reader;
import com.epam.lab.pavel_katsuba.library.Beans.Role;
import com.epam.lab.pavel_katsuba.library.db_utils.DaoConstants;
import com.epam.lab.pavel_katsuba.library.interfaces.RelatesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReaderRoleImpl implements RelatesDao<Reader, Role> {
    private static final String SELECT_ID_ROLE = "select idRole from roles where roles.roleName = (?)";
    private static final String INSERT_ROLE = "insert into roles (roleName) values (?) ON DUPLICATE KEY UPDATE roleName = (?)";
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Role> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Role.valueOf(resultSet.getString("roleName").toUpperCase());

    @Autowired
    public ReaderRoleImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Role> getBeans(int readerId) {
        return jdbcTemplate.query(DaoConstants.SELECT_ROLES_FOR_READER, new Object[]{readerId}, ROW_MAPPER);
    }

    @Override
    public void addBeans(List<Role> roles, int readerId) {
        jdbcTemplate.batchUpdate(DaoConstants.INSERT_ROLE_FOR_READER, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Role role = roles.get(i);
                Integer roleId = jdbcTemplate.queryForObject(SELECT_ID_ROLE, new Object[]{role.name().toLowerCase()}, Integer.class);
                ps.setInt(1, readerId);
                ps.setInt(2, roleId);
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    @Override
    public void putBeans(int readerId, int oldRoleId, int newRoleId) {
        jdbcTemplate.update(DaoConstants.UPDATE_ROLE_FOR_READER, newRoleId, readerId, oldRoleId);
    }

    @Override
    public void deleteBeanRelates(int readerId) {
        jdbcTemplate.update(DaoConstants.DELETE_ROLE_FOR_READER_BY_ID, readerId);
    }

    @Override
    public void delete(int readerId, int roleId) {
        jdbcTemplate.update(DaoConstants.DELETE_ROLE_FOR_READER, readerId, roleId);
    }

    @Override
    public boolean relateIsExist(int entityId, int beanId) {
        Integer count = jdbcTemplate.queryForObject(DaoConstants.IS_EXIST_ROLE_FOR_READER, new Object[]{entityId, beanId}, Integer.class);
        return count > 0;
    }

    @PostConstruct
    public void RoleInit() {
        Role[] roles = Role.values();
        jdbcTemplate.batchUpdate(INSERT_ROLE, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Role role = roles[i];
                ps.setString(1, role.name().toLowerCase());
                ps.setString(2, role.name().toLowerCase());
            }

            @Override
            public int getBatchSize() {
                return roles.length;
            }
        });
    }
}
