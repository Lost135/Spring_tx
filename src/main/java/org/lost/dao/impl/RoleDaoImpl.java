package org.lost.dao.impl;

import org.lost.dao.RoleDao;
import org.lost.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Role> findAll() {
        List<Role> roleList =  jdbcTemplate.query("select * from sys_role ", new BeanPropertyRowMapper<>(Role.class));
        return roleList;
    }

    @Override
    public void save(Role role) {
        jdbcTemplate.update("insert into sys_role value(?,?,?)",null,role.getRoleName(),role.getRoleDesc());
    }

    @Override
    public List<Role> findRoleByUserId(Long id) {
        List<Role> roles =  jdbcTemplate.query("select * from sys_user_role ur, sys_role r where ur.roleId=r.id and ur.userId = ?  ", new BeanPropertyRowMapper<>(Role.class),id);
        return roles;
    }
}
