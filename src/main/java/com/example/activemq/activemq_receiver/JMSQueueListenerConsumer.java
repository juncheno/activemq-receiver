package com.example.activemq.activemq_receiver;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.soap.Text;

import org.apache.activemq.ActiveMQConnectionFactory;


public class JMSQueueListenerConsumer {
     public static void main(String[] args) {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		
		Connection connection = null;
		try {
			
			 connection = connectionFactory.createConnection();
			 connection.start();
			 
			Session session= connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//创建目的地
			 Destination destination = session.createQueue("myQueue");
			 //创建发送者
			MessageConsumer messageConsumer = session.createConsumer(destination);
			
			MessageListener listener = new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
                   try {
					System.out.println(((TextMessage)message).getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
				}
			};
			  
			while(true){
				messageConsumer.setMessageListener(listener);
				session.commit();
			}
			 //接收消息
			
//			 TextMessage message = (TextMessage) messageConsumer.receive();
//			 System.out.println(message.getText());
//			
//			session.close();
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
