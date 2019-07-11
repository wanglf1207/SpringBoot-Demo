package com.springboot.demo.jdbctemplate.service;

import com.springboot.demo.jdbctemplate.domain.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> queryUsers();

    User queryUserById(Long id);

    int updateUser(User user);

    int deleteUserById(Long id);
}
