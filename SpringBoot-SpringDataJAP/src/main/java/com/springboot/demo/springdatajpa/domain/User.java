package com.springboot.demo.springdatajpa.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="user1")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Transient
    private String email;

    /**
     * 不写默认的构造方法报错
     * com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.springboot.demo.jdbctemplate.domain.User` (no Creators, like default construct, exist):
     * cannot deserialize from Object value (no delegate- or property-based Creator)
     */
    public User() {

    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'';
    }
}
