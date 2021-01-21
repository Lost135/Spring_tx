package org.lost.dao;

import org.lost.domain.Role;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface RoleDao {

    public List<Role> findAll();

    void save(Role role);

    List<Role> findRoleByUserId(Long id);
}
