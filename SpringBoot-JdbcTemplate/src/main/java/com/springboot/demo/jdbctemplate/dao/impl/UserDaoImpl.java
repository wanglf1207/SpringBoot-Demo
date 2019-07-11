package com.springboot.demo.jdbctemplate.dao.impl;

import com.springboot.demo.jdbctemplate.dao.UserDao;
import com.springboot.demo.jdbctemplate.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<User> selectAll() {
        String querySql = "select * from user";
        return jdbcTemplate.query(querySql, new Object[]{}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int insert(User user) {
        String sql = "insert into user(username, password) values(?, ?)";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    @Override
    public User selectById(Long id) {
        String sql = "select * from user where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int update(User user) {
        String updateSql = "update user set username=?, password=? where id=?";
        return jdbcTemplate.update(updateSql,user.getUsername(),user.getPassword(),user.getId());
    }

    @Override
    public int deleteById(Long id) {
        String deleteSQL = "delete from user where id = ?";
        return jdbcTemplate.update(deleteSQL,id);
    }
}
