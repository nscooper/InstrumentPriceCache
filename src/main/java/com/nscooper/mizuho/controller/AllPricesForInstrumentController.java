package com.nscooper.mizuho.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.domain.transit.PriceNotification;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;
import com.nscooper.mizuho.service.AllPricesForInstrumentService;
import com.nscooper.mizuho.service.TranslatePricesService;

@RestController
public class AllPricesForInstrumentController {
	private static Logger logger = Logger.getLogger(AllPricesForInstrumentController.class);

	@Autowired
	private AllPricesForInstrumentService allPricesForInstrumentService = null;
	@Autowired
	private TranslatePricesService translatePricesService;
	
	private static final String RESULT_SIZE = "Number of price records found";
	private static final String ERROR = "Error trying to obtain instrument prices for ISIN";
	private JSONObject jsonOutcome = null;
	private String jsonString = null;

	@RequestMapping(value = "/allpricesforinstrument", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getAllPricesFromVendor(
			@RequestParam(value = "isin", defaultValue = "None") final String isin,
			final HttpServletRequest req) {
		
		jsonOutcome = new JSONObject();
		Set<Price> prices = null;
		try {
			prices = allPricesForInstrumentService.getAllPricesForInstrument(isin);
		
			if (prices==null || prices.size() < 1) {
				jsonOutcome.put(RESULT_SIZE, "0");
			} else {
				jsonOutcome.put(RESULT_SIZE, prices.size());
				int counter=1;
				for (final Price price : prices) {
					
					PriceNotification pn = translatePricesService.fromPriceToPriceNotification(price);
					jsonString = translatePricesService.priceNotificationToJson(pn);
					jsonOutcome.put(Integer.valueOf(counter++).toString(), jsonString);
				}
			}
			
		} catch (InstrumentNotFoundByIsin | JsonProcessingException e) {
			logger.error(e.getLocalizedMessage());
			jsonOutcome.put(ERROR, e.getLocalizedMessage());
		}

		return jsonOutcome.toString(20);
	}

}