package com.springboot.demo.jdbctemplate.service.impl;

import com.springboot.demo.jdbctemplate.dao.UserDao;
import com.springboot.demo.jdbctemplate.domain.User;
import com.springboot.demo.jdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int addUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public List<User> queryUsers() {
        return userDao.selectAll();
    }
}
