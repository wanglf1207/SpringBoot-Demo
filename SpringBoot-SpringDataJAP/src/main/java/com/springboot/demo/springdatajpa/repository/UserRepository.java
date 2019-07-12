package com.springboot.demo.springdatajpa.repository;

import com.springboot.demo.springdatajpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
