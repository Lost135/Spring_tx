package org.lost.service.impl;

import org.lost.dao.RoleDao;
import org.lost.dao.UserDao;
import org.lost.domain.Role;
import org.lost.domain.User;
import org.lost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "roleDao")
    private RoleDao roleDao;

    @Override
    public List<User> list() {
        List<User> userList = userDao.findAll();
        for(User user : userList) {
            Long id = user.getId();
            List<Role> roles = roleDao.findRoleByUserId(id);
            user.setRoles(roles);
        }
        return userList;
    }

    @Override
    public void save(User user, Long[] roleIds) {
        Long userId = userDao.save(user);
        userDao.saveUserRoleRel(userId,roleIds);
    }

    @Override
    public void del(Long userId) {
        userDao.delUserRoleRel(userId);
        userDao.delUser(userId);
    }

    @Override
    public User login(String username, String password) {
        try {
            User user = userDao.findByUsernameAndPassword(username,password);
            return user;
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }
}
