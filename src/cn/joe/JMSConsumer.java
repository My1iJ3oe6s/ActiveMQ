package cn.joe;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
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
	private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL; // ���ӵ�ַ
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
			destination = session.createQueue("FirestQueue");

			messageConsumer = session.createConsumer(destination);
			while(true){
				TextMessage receive = (TextMessage) messageConsumer.receive(10);
				if(receive != null){
					System.out.println("�յ�����Ϣ �� " + receive.getText());
				}
			}
			// ��Ϊ�����ύ������ ��Ҫcommit��

			//close ���������ӲŻ�Ͽ� ����һֱ����
			//session.close();
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
