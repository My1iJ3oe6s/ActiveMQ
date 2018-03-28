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
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ����������Ϣ���־ö��ģ�
 * 
 * �־ö���һ��Ҫ������һ�Σ���������м��ע����������ߣ�Ȼ�������пͻ��˷�����Ϣ��
 * ���ʱ�������������Ƿ����ߣ�������յ������ڵĻ����´����ӵ�ʱ����û���յ���
 * ��������
 * 
 *��ͬ��Ҫע��ĵط�
 *1.��������Ҫ������������ID ��connection.setClientID("ccl");
 *2.������topic������topic��������֮ǰ��Destination
 *3.�����������߶����Ϊ������ ��TopicSubscriber��
 * 
 * @author wanqiao
 *
 */

public class JMSConsumer2 {

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
			//1.����������ID
			connection.setClientID("ccl");
			
			// ����session �Ƿ��������� ��Ϣȷ�ϵķ�ʽ (���Ѳ���Ҫ������)
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 2.�������ӵ���Ϣ����
			Topic topic = session.createTopic("FirestTopic");

			//3.�����־û�������
			TopicSubscriber createDurableSubscriber = session.createDurableSubscriber(topic, "");		
			connection.start();
			createDurableSubscriber.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message message) {
					try {
						System.out.println("���յ���Ϣ1�� " + ((TextMessage) message).getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} catch (JMSException e) {
			System.out.println(e.getMessage());
		}
	}

}
