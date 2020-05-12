package com.nscooper.mizuho.components;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nscooper.mizuho.domain.transit.PriceNotification;
import com.nscooper.mizuho.service.TranslatePricesService;

@Component
public class PricePublisherFromCache {

	private static Logger logger = Logger.getLogger(PricePublisherFromCache.class);
	
	@Value("${activeMqHostnameAndPort}")
	private String activeMqHostnameAndPort;

	@Value("${outboundPricesTopicName}")
	private String outboundPricesTopicName;
	
	@Value("${publishedPriceLifetime}")
	private Long publishedPriceLifetime;

	@Autowired
	TranslatePricesService translatePricesService;

	private ConnectionFactory factory = null;
	private Connection con = null;
	private Session session = null;
	private MessageProducer producer = null;

	@PostConstruct
	public void init() {
		factory = new ActiveMQConnectionFactory(activeMqHostnameAndPort);
	}

	public void publish(final PriceNotification priceNotification) throws JsonProcessingException {

		try {
			con = factory.createConnection();
			session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(session.createTopic(outboundPricesTopicName));
			
			String textMessage = translatePricesService.priceNotificationToJson(priceNotification);
			Message msg = session.createTextMessage(textMessage.replaceAll(" ", "%20"));
			
			msg.setJMSExpiration(publishedPriceLifetime.longValue());  // leave on the topic for set time
			producer.send(msg);
			logger.info("Published: "+textMessage);

		} catch (JMSException e) {
			for (final StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
		} finally {
			try {
				con.close();
			} catch (JMSException e) {
				for (final StackTraceElement ste : e.getStackTrace()) {
					logger.error(ste.toString());
				}
			}
		}
	}
}
