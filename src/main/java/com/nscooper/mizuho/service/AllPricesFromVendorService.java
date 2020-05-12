package com.nscooper.mizuho.service;

import java.util.Set;

import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

public interface AllPricesFromVendorService {

	/**
	 * Returns a set of all prices from a vendor, denoted by vendor name
	 * @param name
	 * @return
	 * @throws VendorNotFoundByName 
	 */
	public Set<Price> getAllPricesFromVendor(final String name) throws VendorNotFoundByName;


}
