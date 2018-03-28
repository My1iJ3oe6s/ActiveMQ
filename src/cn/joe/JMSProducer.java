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
 * ����������Ϣ
 * 
 * @author wanqiao
 *
 */

public class JMSProducer {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�������û���
	private static final String PASSWD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKERURL = "tcp://192.168.239.128:61616"; // ���ӵ�ַ
	private static final int SENDNUM = 10; // ���͵���Ϣ����

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;// ���ӹ���
		Connection connection = null; // ����
		Session session; // �Ự���ջ�����Ϣ���߳�
		Destination destination;// ��Ϣ���͵ĵ�ַ
		MessageProducer messageProduce; // ��Ϣ������������

		// ʵ�������ӹ���
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWD, BROKERURL);
		// ͨ�����ӹ�����ȡ����
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			// ����session �Ƿ��������� ��Ϣȷ�ϵķ�ʽ
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// ��������
			destination = session.createQueue("FirestQueue");
			messageProduce = session.createProducer(destination);
			sendMessage(session, messageProduce);
			//��Ϊ�����ύ������ ��Ҫcommit��
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
			System.out.println("������Ϣ�� " + i + "��");
			messageProduce.send(createTextMessage);
			
		}
	}
}
