package com.nscooper.mizuho.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.domain.transit.PriceNotification;

public interface TranslatePricesService {

	/**
	 * Converts a PriceNotification object to JSON 
	 * @param priceNotification
	 * @return
	 * @throws JsonProcessingException
	 */
	public String priceNotificationToJson(final PriceNotification priceNotification) throws JsonProcessingException;
	
	/**
	 * Converts a JSON string to a PriceNotification object 
	 * @param priceNotification
	 * @return
	 * @throws IOException
	 */
	public PriceNotification fromJsonToPriceNotification(final String priceNotification) throws IOException;
	
	/**
	 * Translates a Price to a PriceNotification object
	 * @param Price
	 * @return
	 * @throws IOException
	 */
	public PriceNotification fromPriceToPriceNotification(final Price price);
}
