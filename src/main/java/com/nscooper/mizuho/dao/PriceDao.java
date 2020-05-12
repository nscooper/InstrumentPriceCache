package com.nscooper.mizuho.dao;

import java.util.Set;

import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.domain.Vendor;

public interface PriceDao extends Dao {

	/**
	 * Returns a list of prices provided by specific vendor
	 * @param vendor
	 * @return
	 */
	public Set<Price> findAllPricesFromVendor(Vendor vendor);

	/**
	 * Returns a list of prices for a specific instrument
	 * @param instrument
	 * @return
	 */
	public Set<Price> findAllPricesForInstrument(Instrument instrument);
	
	/**
	 * Returns a price for a specific instrument, from a specific vendor
	 * @param instrument
	 * @return
	 */
	public Price findPriceForInstrumentFromVendor(Instrument instrument, Vendor vendor);
	
	/**
	 * Removes price records created beyond the submitted number of days
	 * @param ageInDays
	 * @return
	 */
	public Boolean deletePricesCreatedBeyondSubmittedAge(int ageInDays);
}