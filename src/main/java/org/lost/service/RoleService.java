package org.lost.service;

import org.lost.domain.Role;

import java.util.List;

public interface RoleService {
    public List<Role> list();

    void save(Role role);
}
