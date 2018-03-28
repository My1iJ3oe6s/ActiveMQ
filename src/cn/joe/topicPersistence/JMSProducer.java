package cn.joe.topicPersistence;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 持久化测试
 * 
 * 消息中间件的持久化
 * 
 * 1.在发送消息的时候需要设置DeliveryMode.PERSISTENT
 * 2.在设置之后connection在开启
 * 
 * @author wanqiao
 *
 */

public class JMSProducer {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认连接用户名
	private static final String PASSWD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKERURL = "tcp://192.168.239.128:61616";; // 连接地址
	private static final int SENDNUM = 10; // 发送的消息次数

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;// 连接工厂
		Connection connection = null; // 连接
		Session session; // 会话接收或发送消息的线程
		Destination destination;// 消息发送的地址
		MessageProducer messageProduce; // 消息发送者生产者
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWD, BROKERURL);
		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic("FirestTopic");
			messageProduce = session.createProducer(destination);
			//1.设置持久化订阅   start要在设置之后
			messageProduce.setDeliveryMode(DeliveryMode.PERSISTENT);
			connection.start();
			sendMessage(session, messageProduce);
		} catch (JMSException e) {
			e.printStackTrace();
		}finally {
			if (connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	public static void sendMessage(Session session, MessageProducer messageProduce) throws JMSException {
		for (int i = 0; i < SENDNUM; i++) {
			TextMessage createTextMessage = session.createTextMessage("ACTIVEMQ FIRST TOPIC MEAASGE:" + i);
			System.out.println("发送消息： " + i + "次");
			messageProduce.send(createTextMessage);
			
		}
	}
}
