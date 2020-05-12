/**
 * 
 */
package com.nscooper.mizuho.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscooper.mizuho.dao.InstrumentDao;
import com.nscooper.mizuho.dao.PriceDao;
import com.nscooper.mizuho.dao.VendorDao;
import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.domain.Vendor;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

/**
 * @author nick
 *
 */
@Service
public class InstrumentPriceFromVendorServiceImpl implements InstrumentPriceFromVendorService {
	@Autowired
	private PriceDao priceDao = null;
	@Autowired
	private VendorDao vendorDao = null;
	@Autowired
	private InstrumentDao instrumentDao = null;

	/* (non-Javadoc)
	 * @see com.nscooper.mizuho.service.AllPricesFromVendorService#getAllPricesFromVendor(java.lang.String)
	 */
	@Override
	public Price getInstrumentPriceFromVendor(String isin, String vendorName) throws VendorNotFoundByName, InstrumentNotFoundByIsin {
		Instrument instrument = instrumentDao.findInstrumentWithIsin(isin);
		Vendor vendor = vendorDao.findVendorWithName(vendorName);
		return priceDao.findPriceForInstrumentFromVendor(instrument, vendor);
	}
}