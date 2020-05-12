package com.nscooper.mizuho.dao;

import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;

public interface InstrumentDao extends Dao {

	/**
	 * Returns a set of instruments with a certain isin
	 * @param name
	 * @return
	 */
	public Instrument findInstrumentWithIsin(String isin) throws InstrumentNotFoundByIsin;
	
}