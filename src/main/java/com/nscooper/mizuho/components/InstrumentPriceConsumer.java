package com.nscooper.mizuho.components;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nscooper.mizuho.domain.transit.PriceNotification;
import com.nscooper.mizuho.service.MaintainPricesService;
import com.nscooper.mizuho.service.TranslatePricesService;

@Component
public class InstrumentPriceConsumer implements MessageListener {

	private static final Logger logger = Logger.getLogger(InstrumentPriceConsumer.class);

	@Value("${inboundPricesQueueName}")
	private String queueName;

	@Autowired
	TranslatePricesService translatePricesService;

	@Autowired
	MaintainPricesService maintainPricesService;

	@Override
	public void onMessage(Message message) {

		/* Receive the text message */
		if (message instanceof TextMessage) {

			try {
				String text = ((TextMessage) message).getText();
				logger.debug("Message received from the queue " + queueName + " is: " + text);

				PriceNotification priceNotification = translatePricesService.fromJsonToPriceNotification(text);
				maintainPricesService.addPriceDetailsFromNotification(priceNotification);

			} catch (JMSException | IOException e) {
				logger.error("Error : " + e.getLocalizedMessage());
			}

		} else {
			/* ignore non text message */
			logger.info("Only react to text messages sent to queue: " + queueName + ". Message of type:"
					+ message.getClass().getSimpleName() + " ignored!");
		}
	}
}
