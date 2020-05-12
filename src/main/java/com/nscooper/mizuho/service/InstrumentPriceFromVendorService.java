package com.nscooper.mizuho.service;

import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

public interface InstrumentPriceFromVendorService {

	/**
	 * Returns a set of all prices from a vendor, denoted by vendor name
	 * @param name
	 * @return
	 * @throws VendorNotFoundByName 
	 */
	public Price getInstrumentPriceFromVendor(String isin, String vendorName) throws VendorNotFoundByName, InstrumentNotFoundByIsin;


}
