package com.nscooper.mizuho.service;

import java.util.Set;

import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;

public interface AllPricesForInstrumentService {

	/**
	 * Returns a set of all prices for an Instrument, denoted by isin
	 * @param isin
	 * @return
	 * @throws InstrumentNotFoundByIsin 
	 */
	public Set<Price> getAllPricesForInstrument(final String isin) throws InstrumentNotFoundByIsin;

}
