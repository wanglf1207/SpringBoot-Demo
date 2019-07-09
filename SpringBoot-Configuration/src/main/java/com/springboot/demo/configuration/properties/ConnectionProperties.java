package com.springboot.demo.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 演示自定义的properties文件
 */
@Component
@PropertySource("classpath:connection.properties")
@ConfigurationProperties(prefix = "connection")
public class ConnectionProperties {

    private  String username;
    private  String password;

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


}
