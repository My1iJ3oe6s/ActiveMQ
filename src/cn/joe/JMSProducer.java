package cn.joe;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 用来生产消息
 * 
 * @author wanqiao
 *
 */

public class JMSProducer {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认连接用户名
	private static final String PASSWD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKERURL = "tcp://192.168.239.128:61616"; // 连接地址
	private static final int SENDNUM = 10; // 发送的消息次数

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;// 连接工厂
		Connection connection = null; // 连接
		Session session; // 会话接收或发送消息的线程
		Destination destination;// 消息发送的地址
		MessageProducer messageProduce; // 消息发送者生产者

		// 实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWD, BROKERURL);
		// 通过连接工厂获取连接
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			// 创建session 是否加事务操作 消息确认的方式
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// 创建队列
			destination = session.createQueue("FirestQueue");
			messageProduce = session.createProducer(destination);
			sendMessage(session, messageProduce);
			//因为上面提交了事务 需要commit下
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
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
			TextMessage createTextMessage = session.createTextMessage("ACTIVEMQ FIRST MEAASGE:" + i);
			System.out.println("发送消息： " + i + "次");
			messageProduce.send(createTextMessage);
			
		}
	}
}
