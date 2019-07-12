package com.springboot.demo.springdatajpa;

import com.springboot.demo.springdatajpa.domain.User;
import com.springboot.demo.springdatajpa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataJpaApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(SpringDataJpaApplicationTests.class);

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        final  User user = userRepository.save(new User("u1","p1"));
        logger.info("保存用户成功{}",user);
    }

    @Test
    public void testFindAll() {
        List<User> userList = userRepository.findAll();
        logger.info("查询用户成功{}",userList);
    }
    @Test
    public void contextLoads() {
    }

}
