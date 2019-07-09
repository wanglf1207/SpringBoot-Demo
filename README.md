SpringBoot 是为了简化 Spring 应用的创建、运行、调试、部署等一系列问题而诞生的产物，自动装配的特性让我们可以更好的关注业务本身而不是外部的XML配置，我们只需遵循规范，引入相关的依赖就可以轻易的搭建出一个 WEB 工程。
未接触 SpringBoot 之前，搭建一个普通的 WEB 工程往往需要花费很长时间，使用SpringBoot可以快速的搭建一个SpringBoot项目。

## SpringBoot入门
SpringBoot-HelloWorld  

```$xslt
server.port=8080
server.servlet.context-path=/springboot-hello
local.server.port=8080
```

```java
package com.springboot.demo.hello.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class HelloWorldController {

    @RequestMapping("/")
    @ResponseBody
    String sayHello() {
        return "Hello World!";
    }

}
```
测试类
```java
package com.springboot.demo.hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootHelloApplicationTests {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/springboot-hello");
    }

    @Test
    public void testHello() {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertEquals(response.getBody(), "Hello World!");
    }

}

```
### 自定义banner
借助在线工具生成banner文件，将文件放到resource文件夹中
http://www.bootschool.net/ascii

## SpringBoot配置
* 运行方法：浏览器输入 
http://localhost:8080/dev/properties/default
http://localhost:8080/dev/properties/connection

* 掌握@ConfigurationProperties、@PropertySource 等注解的用法及作用

* 掌握编写自定义配置

* 掌握外部命令引导配置的方式
在命令行输入java -jar app.jar --spring.profiles.active=test --connection.username=root

遇到了一个坑，记录一下：
springboot2.1.6 spring-boot-configuration-processor一直下载不到，把国内阿里的镜像去掉使用默认的就好了
特此记录-20190709
电脑上maven的镜像一直是阿里的
```xml
<mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>
          http://maven.aliyun.com/nexus/content/groups/public/
      </url>
      <mirrorOf>central</mirrorOf>        
    </mirror>
```
## 整合Thymeleaf模板
Thymeleaf是现代化服务器端的Java模板引擎，不同与其它几种模板的是Thymeleaf的语法更加接近HTML，并且具有很高的扩展性。

* 访问地址：http://localhost:8080/index


