package com.example.activemq.activemq_receiver;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.xml.soap.Text;

import org.apache.activemq.ActiveMQConnectionFactory;


public class JMSPersistentTopicConsumer {
     public static void main(String[] args) {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		
		Connection connection = null;
		try {
			
			 connection = connectionFactory.createConnection();
			 connection.setClientID("chenjun-001");//持久化订阅消息
			 connection.start();
			 
			Session session= connection.createSession(Boolean.TRUE, 
					Session.AUTO_ACKNOWLEDGE);//如果为false,则session 不需要commit,会自动提交
			//创建目的地
			 Topic destination = session.createTopic("myTopic");
			 //创建发送者
			MessageConsumer messageConsumer = session.createDurableSubscriber(destination, "chenjun-001");
			 //接收消息
			
			 TextMessage message = (TextMessage) messageConsumer.receive();
//			 message.getStringProperty(name);获取到消息属性
			 System.out.println(message.getText());
			
			session.commit();//消息被确认
//			session.rollback();
			session.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
} 
