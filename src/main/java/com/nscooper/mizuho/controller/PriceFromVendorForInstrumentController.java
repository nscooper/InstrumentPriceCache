package com.nscooper.mizuho.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;
import com.nscooper.mizuho.service.InstrumentPriceFromVendorService;
import com.nscooper.mizuho.service.TranslatePricesService;

@RestController
public class PriceFromVendorForInstrumentController {

	@Autowired
	private InstrumentPriceFromVendorService instrumentPriceFromVendorService;
	@Autowired
	private TranslatePricesService translatePricesService;

	private JSONObject jsonOutcome = null;

	@RequestMapping(value = "/instrumentpricefromvendor", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getAllPricesFromVendor(
			@RequestParam(value = "isin", defaultValue = "None") final String isin,
			@RequestParam(value = "vendorName", defaultValue = "None") final String vendorName,
			final HttpServletRequest req) {

		Price price;
		String returnString = null;
		jsonOutcome = new JSONObject();
		try {
			price = instrumentPriceFromVendorService.getInstrumentPriceFromVendor(isin, vendorName);
			PriceNotification pn = translatePricesService.fromPriceToPriceNotification(price);
			returnString = translatePricesService.priceNotificationToJson(pn);
		} catch (VendorNotFoundByName e1) {
			jsonOutcome.put("VendorNotFoundByName", e1.getLocalizedMessage());
		} catch (InstrumentNotFoundByIsin e1) {
			jsonOutcome.put("InstrumentNotFoundByIsin", e1.getLocalizedMessage());
		} catch (JsonProcessingException e) {
			jsonOutcome.put("JsonProcessingException", e.getLocalizedMessage());
		}
		return (returnString == null) ? jsonOutcome.toString(5) : returnString;
	}
}