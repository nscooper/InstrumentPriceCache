package com.nscooper.mizuho.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nscooper.mizuho.domain.transit.PriceNotification;

public interface MaintainPricesService {
	
	/**
	 * Method to remove redundant data
	 */
	Boolean removeOldPrices(int ageInDays);
	
	/**
	 * PriceNotification used to extract price, instrument and vendor details, then persist all
	 * @param priceNotification
	 * @return
	 * @throws JsonProcessingException 
	 */
	public void addPriceDetailsFromNotification( PriceNotification priceNotification) throws JsonProcessingException;
}