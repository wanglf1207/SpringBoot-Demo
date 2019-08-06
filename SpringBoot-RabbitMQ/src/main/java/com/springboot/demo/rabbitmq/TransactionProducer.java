package com.springboot.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TransactionProducer {
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
        // 指定一个队列,不存在的话自动创建
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for(int i=0;i<=10;i++) {
            try {
                // 用于将当前的信道设置成事务模式
                channel.txSelect();

                // 发送消息
                String message = "Hello World!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

                // 下面1/0一定会抛出异常，会触发catch中的回滚代码
                // int i = 1/0;

                // 提交事务
                channel.txCommit();
                System.out.println(" [x] Sent '" + message + "'");
            } catch (IOException e) {
                channel.txRollback();
            } finally {

            }
        }
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
