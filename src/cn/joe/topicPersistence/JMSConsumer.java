package cn.joe.topicPersistence;

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

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ����������Ϣ
 * 
 * @author wanqiao
 *
 */

public class JMSConsumer {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�������û���
	private static final String PASSWD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKERURL = "tcp://192.168.239.128:61616";; // ���ӵ�ַ
	private static final int SENDNUM = 10; // ���͵���Ϣ����

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;// ���ӹ���
		Connection connection = null; // ����
		Session session; // �Ự���ջ�����Ϣ���߳�
		Destination destination;// ��Ϣ���͵ĵ�ַ
		MessageConsumer messageConsumer; // ��Ϣ������������

		// ʵ�������ӹ���
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWD, BROKERURL);
		// ͨ�����ӹ�����ȡ����
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			// ����session �Ƿ��������� ��Ϣȷ�ϵķ�ʽ (���Ѳ���Ҫ������)
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// �������ӵ���Ϣ����
			destination = session.createTopic("FirestTopic");
			messageConsumer = session.createConsumer(destination);
			messageConsumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						System.out.println("���յ���Ϣ2�� " + ((TextMessage) message).getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} catch (JMSException e) {
			System.out.println(e.getMessage());
		}finally {
			/*if (connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			
		}
	}

}
