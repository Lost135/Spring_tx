package org.lost.dao.impl;

import org.lost.dao.UserDao;
import org.lost.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        List<User> userList =  jdbcTemplate.query("select * from sys_user ", new BeanPropertyRowMapper<>(User.class));
        return userList;
    }

    @Override
    public Long save(User user) {
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into sys_user values(?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1,null);
                preparedStatement.setString(2,user.getUsername());
                preparedStatement.setString(3,user.getEmail());
                preparedStatement.setString(4,user.getPassword());
                preparedStatement.setString(5,user.getPhoneNum());
                return preparedStatement;
            }
        };

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(creator,keyHolder);

        Long userId = keyHolder.getKey().longValue();
        return userId;
    }

    @Override
    public void saveUserRoleRel(Long id, Long[] roleIds) {
        for (Long roleId : roleIds){
            jdbcTemplate.update("insert into sys_user_role values(?,?)",id,roleId);
        }
    }

    @Override
    public void delUserRoleRel(Long userId) {
        jdbcTemplate.update("delete from sys_user_role where userId = ? ",userId);
    }

    @Override
    public void delUser(Long userId) {
        jdbcTemplate.update("delete from sys_user where id = ? ",userId);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws EmptyResultDataAccessException {
        User user = jdbcTemplate.queryForObject("select * from sys_user where username = ? and password = ? ",
                new BeanPropertyRowMapper<>(User.class),username,password);
        return user;
    }
}
