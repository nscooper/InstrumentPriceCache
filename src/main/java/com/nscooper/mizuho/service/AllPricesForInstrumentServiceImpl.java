/**
 * 
 */
package com.nscooper.mizuho.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscooper.mizuho.dao.InstrumentDao;
import com.nscooper.mizuho.dao.PriceDao;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;

/**
 * @author nick
 *
 */
@Service
public class AllPricesForInstrumentServiceImpl implements AllPricesForInstrumentService {
	@Autowired
	private PriceDao priceDao = null;
	@Autowired
	private InstrumentDao instrumentDao = null;

	/* (non-Javadoc)
	 * @see com.nscooper.mizuho.service.AllPricesForInstrumentService#getAllPricesForInstrument(java.lang.String)
	 */
	@Override
	public Set<Price> getAllPricesForInstrument(final String isin) throws InstrumentNotFoundByIsin {
		return priceDao.findAllPricesForInstrument(instrumentDao.findInstrumentWithIsin(isin));
	}

}