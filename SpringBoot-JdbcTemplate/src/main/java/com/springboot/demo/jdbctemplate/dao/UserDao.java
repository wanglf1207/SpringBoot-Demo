package com.springboot.demo.jdbctemplate.dao;

import com.springboot.demo.jdbctemplate.domain.User;

import java.util.List;

public interface UserDao {

    List<User> selectAll();
    int insert(User user);
    User selectById(Long id);
    int update(User user);
    int deleteById(Long id);
}
