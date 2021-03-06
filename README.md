SpringBoot 是为了简化 Spring 应用的创建、运行、调试、部署等一系列问题而诞生的产物，自动装配的特性让我们可以更好的关注业务本身而不是外部的XML配置，我们只需遵循规范，引入相关的依赖就可以轻易的搭建出一个 WEB 工程。
未接触 SpringBoot 之前，搭建一个普通的 WEB 工程往往需要花费很长时间，使用SpringBoot可以快速的搭建一个SpringBoot项目。

## SpringBoot入门
对应项目为SpringBoot-HelloWorld  

### 项目的创建
![创建项目](./SpringBoot-HelloWorld/src/main/resources/images/createModule.jpg)


修改配置文件/resources/application.properties
```properties
server.port=8080
server.servlet.context-path=/springboot-hello
local.server.port=8080
```

创建HelloWorldController
```java

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

* 在真实的应用中，常常会有多个环境（如：开发，测试，生产等），不同的环境数据库连接都不一样，这个时候就需要用到spring.profile.active 的强大功能了，它的格式为 application-{profile}.properties，这里的 application 为前缀不能改，{profile} 是我们自己定义的。

* 遇到了一个坑，记录一下：
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

将html文件放到resources/templates目录下。在html标签中接入thymeleaf命名空间
```xml
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
```

controller中内容如下
```java
@Controller
@RequestMapping
public class ThymeleafController {

    @GetMapping("/index")
    public ModelAndView index() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("title","这是我的第一个thymleaf页面");
        mv.addObject("desc","坚持各种学习");

        Student student = new Student();
        student.setName("王利峰");
        student.setAge(35);
        student.setEmail("wanglf1207@163.com");

        mv.addObject("student",student);
        return mv;
    }
}
```

## 使用JdbcTemplate访问数据库

创建数据库
```sql
create database springboot;
```
创建表
```sql
use springboot;

create table user(
  id int(8) not null auto_increment,
  username varchar(50) not null,
  password varchar(50) not null,
  primary key (id)
);

```

连接数据库的时候碰到一个错误：
```text
com.mysql.cj.exceptions.InvalidConnectionAttributeException: The server time zone value 'EDT' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method) ~[na:1.8.0_121]
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62) ~[na:1.8.0_121]
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45) ~[na:1.8.0_121]
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423) ~[na:1.8.0_121]
```

解决办法：
* 在mysql中执行show variables like '%time_zone%';
* 输入select nows();
* 在终端执行date命令 此时发现终端显示的时间和MySql中显示的时间不一致，这就是问题所在。
* 在mysql中执行 set time_zone=SYSTEM;
* 再次在mysql中执行select now();
* 执行 set global time_zone='+8:00';
* 执行 flush privileges;

## 集成Spring Data JPA
Spring JdbcTemplate的使用，对比原始的JDBC而言，它更加的简洁。但随着表的增加，重复的CRUD工作让我们苦不堪言，这时候Spring Data Jpa的作用就体现出来了…

在application.properties中添加数据库配置信息
```properties
spring.datasource.url=jdbc:mysql://192.168.231.130:3306/springboot?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=Wanglf1207!

# JPA配置
spring.jpa.hibernate.ddl-auto=update
# 输出日志
spring.jpa.show-sql=true
# 数据库类型
spring.jpa.database=mysql

```

ddl-auto 几种属性

* create： 每次运行程序时，都会重新创建表，故而数据会丢失
* create-drop： 每次运行程序时会先创建表结构，然后待程序结束时清空表
* upadte： 每次运行程序，没有表时会创建表，如果对象发生改变会更新表结构，原有数据不会清空，只会更新（推荐使用）
* validate： 运行程序会校验数据与数据库的字段类型是否相同，字段不同会报错

创建实体类User
```java
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

```

定义数据访问接口
```java
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
```
创建UserRepository数据访问层接口，需要继承JpaRepository<T,K>，第一个泛型参数是实体对象的名称，第二个是主键类型。只需要这样简单的配置，该UserRepository就拥常用的CRUD功能，JpaRepository本身就包含了常用功能，剩下的查询我们按照规范写接口即可。

测试类
```java

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
```
可以看出，使用springdata JPA比JdbcTemplate要简单很多。

## 整合Lettuce Redis
Spring Boot 除了支持常见的ORM框架外，更是对常用的中间件提供了非常好封装，随着Spring Boot2.x的到来，支持的组件越来越丰富，也越来越成熟，其中对Redis的支持不仅仅是丰富了它的API，更是替换掉底层Jedis的依赖，取而代之换成了Lettuce(生菜)
### Redis介绍
Redis是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。相比Memcached它支持存储的类型相对更多（字符、哈希、集合、有序集合、列表、GEO），同时Redis是线程安全的。2010年3月15日起，Redis的开发工作由VMware主持，2013年5月开始，Redis的开发由Pivotal赞助。


```java
Caused by: io.lettuce.core.RedisCommandExecutionException: ERR Client sent AUTH, but no password is set
	at io.lettuce.core.ExceptionFactory.createExecutionException(ExceptionFactory.java:135)
	at io.lettuce.core.ExceptionFactory.createExecutionException(ExceptionFactory.java:108)
	at io.lettuce.core.protocol.AsyncCommand.completeResult(AsyncCommand.java:120)
	at io.lettuce.core.protocol.AsyncCommand.complete(AsyncCommand.java:111)
	at io.lettuce.core.protocol.CommandHandler.complete(CommandHandler.java:646)
	at io.lettuce.core.protocol.CommandHandler.decode(CommandHandler.java:604)
	at io.lettuce.core.protocol.CommandHandler.channelRead(CommandHandler.java:556)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:374)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:360)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:352)
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1408)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:374)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:360)
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:930)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:682)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:617)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:534)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:496)
	at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:906)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	... 1 more
```
上面的的错误提示：客户端发送认证，但是redis没有设置密码，我的redis服务器的确没有设置密码。
他妈的在redis.conf配置文件中修改了requirepass居然不好用，后来在命令行中设置了临时密码才生效,config set requirepass wanglf1207
### Luttuce介绍
Lettuce 和 Jedis 的都是连接Redis Server的客户端程序。Jedis在实现上是直连redis server，多线程环境下非线程安全，除非使用连接池，为每个Jedis实例增加物理连接。Lettuce基于Netty的连接实例（StatefulRedisConnection），可以在多个线程间并发访问，且线程安全，满足多线程环境下的并发访问，同时它是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。

## RabbitMQ
RabbitMQ是用Erlang语言开发的基于高级消息队列协议(AMQP)的消息队列中间件。因为它开源，而且版本更新快，所以在国内互联网公司被广泛使用。其它使用的消息中间件还有ActiveMQ，RocketMQ，Kafka等，有兴趣的同学可以自行研究。

* 还有2个专业术语要了解下：
生产者：发送消息的应用程序被称为生产者。
消费者：接收消息的应用程序被称为消费者。

* 本次采用RabbitMQ源码安装，具体安装步骤已经整理到《皓月当空》文档里。

首先加入连接RabbitMQ所需要的maven依赖
```xml
 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
 </dependency>
```

写一个消费者程序
```java
public class Producer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 的主机名
        factory.setHost("192.168.231.139");

        /**
         * 下面三行不写也可以创建队列并将消息放到队列上
         * 目前还搞不清楚为什么-20190806
         */
        factory.setUsername("wanghao");
        factory.setPassword("wanghao");
        factory.setPort(5672);

        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        /**
         * 指定一个队列,不存在的话自动创建
         * 如果将下面这行注释掉，程序也不会报错
         */
        // channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 发送消息
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
```
上面程序要注意一个问题，我现在也没想清楚，就是写不写下面三句话看上去好像没关系
```java
factory.setUsername("wanghao");
factory.setPassword("wanghao");
factory.setPort(5672);
```

现在编写一个消费者的程序

```java

public class Consumer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 的主机名
        factory.setHost("192.168.231.139");
        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 创建队列消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received Message '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
```

