package org.lost.service.impl;

import org.lost.dao.RoleDao;
import org.lost.domain.Role;
import org.lost.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource(name = "roleDao")
    private RoleDao roleDao;

    @Override
    public List<Role> list() {
        List<Role> roleList = roleDao.findAll();
        return roleList;
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }
}
