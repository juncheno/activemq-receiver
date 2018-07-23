package com.example.activemq.activemq_receiver;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.soap.Text;

import org.apache.activemq.ActiveMQConnectionFactory;


public class JMSTopicConsumer02 {
     public static void main(String[] args) {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		
		Connection connection = null;
		try {
			
			 connection = connectionFactory.createConnection();
			 connection.start();
			 
			Session session= connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//创建目的地
			Destination destination = session.createTopic("myTopic");
			//创建发送者
			MessageConsumer messageConsumer = session.createConsumer(destination);
			 //接收消息
			
			 TextMessage message = (TextMessage) messageConsumer.receive();
//			 message.getStringProperty(name);获取到消息属性
			 System.out.println(message.getText());
			
			session.commit();
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
