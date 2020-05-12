package com.nscooper.mizuho.components;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class VendorPriceSenderTestTool {

	public static void main(String[] args) {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		Connection con = null;
		try {
			con = factory.createConnection();
			Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue queue = session.createQueue("instrumentPriceFromVendors");

			MessageProducer producer = session.createProducer(queue);
			Message msg = session.createTextMessage(args[0].replaceAll(" ", "%20"));
			producer.send(msg);
			
		} catch (JMSException e) {
			for (final StackTraceElement ste : e.getStackTrace()) {
				System.out.println(ste.toString());
			}
		} finally {
			try {
				con.close();
			} catch (JMSException e) {
				for (final StackTraceElement ste : e.getStackTrace()) {
					System.out.println(ste.toString());
				}
			}
		}
	}
}
