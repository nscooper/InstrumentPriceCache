package com.nscooper.mizuho.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.domain.Vendor;
import com.nscooper.mizuho.domain.transit.PriceNotification;

@Service
public class TranslatePricesServiceImpl implements TranslatePricesService {
	
	@Override
	public String priceNotificationToJson(PriceNotification priceNotification) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(priceNotification);
		return jsonInString;
	}

	@Override
	public PriceNotification fromJsonToPriceNotification(String priceNotification) throws IOException {
		priceNotification = priceNotification.replaceAll(" ", "%20");
		ObjectMapper mapper = new ObjectMapper();
		PriceNotification priceNotificationOut = mapper.readValue(priceNotification, PriceNotification.class);
		priceNotificationOut.setInstrumentName(priceNotificationOut.getInstrumentName().replaceAll("%20", " "));
		priceNotificationOut.setVendorName(priceNotificationOut.getVendorName().replaceAll("%20", " "));
		return priceNotificationOut;
	}

	@Override
	public PriceNotification fromPriceToPriceNotification(final Price price) {
		Instrument instrument = price.getInstrument();
		Vendor vendor = price.getVendor();
		return new PriceNotification(vendor.getName(), instrument.getIsin(), instrument.getName(), 
				price.getBidPrice(), price.getOfferPrice());
	}

}
