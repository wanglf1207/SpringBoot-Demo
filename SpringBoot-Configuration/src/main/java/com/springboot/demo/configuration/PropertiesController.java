package com.springboot.demo.configuration;


import com.springboot.demo.configuration.properties.MyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/properties")
@RestController
public class PropertiesController {

    private static final Logger log = LoggerFactory.getLogger(PropertiesController.class);

    private final MyProperties myProperties;

    // 用构造函数的方式注入myProperties
    @Autowired
    public PropertiesController(MyProperties myProperties) {
        this.myProperties = myProperties;
    }

    @GetMapping("/1")
    public MyProperties myProperties1() {
        log.info("=================================================================================================");
        log.info(myProperties.toString());
        log.info("=================================================================================================");
        return myProperties;
    }
}
