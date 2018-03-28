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
 * �־û�����
 * 
 * ��Ϣ�м���ĳ־û�
 * 
 * 1.�ڷ�����Ϣ��ʱ����Ҫ����DeliveryMode.PERSISTENT
 * 2.������֮��connection�ڿ���
 * 
 * @author wanqiao
 *
 */

public class JMSProducer {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�������û���
	private static final String PASSWD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKERURL = "tcp://192.168.239.128:61616";; // ���ӵ�ַ
	private static final int SENDNUM = 10; // ���͵���Ϣ����

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;// ���ӹ���
		Connection connection = null; // ����
		Session session; // �Ự���ջ�����Ϣ���߳�
		Destination destination;// ��Ϣ���͵ĵ�ַ
		MessageProducer messageProduce; // ��Ϣ������������
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWD, BROKERURL);
		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic("FirestTopic");
			messageProduce = session.createProducer(destination);
			//1.���ó־û�����   startҪ������֮��
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
			System.out.println("������Ϣ�� " + i + "��");
			messageProduce.send(createTextMessage);
			
		}
	}
}
