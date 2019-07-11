package com.springboot.demo.jdbctemplate.controller;

import com.springboot.demo.jdbctemplate.domain.User;
import com.springboot.demo.jdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public int addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/queryUsers")
    public List<User> queryUsers() {
       return userService.queryUsers();
    }
    // @GetMapping("/queryUserById/{id}")的ID，必须和@PathVariable Long id保持一致
    @GetMapping("/queryUserById/{id}")
    public User queryUserById(@PathVariable Long id) {
        return userService.queryUserById(id);
    }

    @PutMapping("/editUser")
    public int editUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public int deleteUser(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
