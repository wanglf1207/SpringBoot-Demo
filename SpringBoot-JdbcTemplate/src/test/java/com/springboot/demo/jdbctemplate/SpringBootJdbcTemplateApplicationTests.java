package com.springboot.demo.jdbctemplate;

import com.springboot.demo.jdbctemplate.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 本来这里手误，SpringBootJdbcTemplateApplication写成SpringBootJdbcTemplateApplicationTest了，
 * 一直报ApplicationContextException: Unable to start ServletWebServerApplicationContext du
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootJdbcTemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class SpringBootJdbcTemplateApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootJdbcTemplateApplicationTests.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testAddUser() {
        testRestTemplate.postForEntity("http://localhost:" + port + "/users", new User("wanglf", "wanglf1207"), String.class);
        logger.info("添加用户成功");
    }

    @Test
    public void testQueryAllUsers() {
        ResponseEntity<List<User>> result = testRestTemplate.exchange("http://localhost:" + port + "/users/queryUsers", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        List<User> userList = result.getBody();
        logger.info("查询到的用户列表为{}",userList);
    }

    @Test
    public void testQueryUserById()  {
        User user = new User();
        user.setId(1);
        ResponseEntity<User> result = testRestTemplate.getForEntity("http://localhost:" + port + "/users/queryUserById/{id}",User.class,user.getId());
        logger.info("按ID查询到到的用户为{}",result.getBody());
    }

    @Test
    public void testEditUser() {
        testRestTemplate.put("http://localhost:" + port + "/users/editUser", new User(1,"WANGHAO", "PASSWORD"));
        logger.info("修改用户成功");
    }

    @Test
    public void testDeleteUser() {
        testRestTemplate.delete("http://localhost:" + port + "/users/deleteUser/{id}", 5);
        logger.info("删除用户成功");
    }
}
