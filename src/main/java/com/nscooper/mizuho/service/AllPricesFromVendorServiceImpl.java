/**
 * 
 */
package com.nscooper.mizuho.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nscooper.mizuho.dao.PriceDao;
import com.nscooper.mizuho.dao.VendorDao;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

/**
 * @author nick
 *
 */
@Service
public class AllPricesFromVendorServiceImpl implements AllPricesFromVendorService {
	@Autowired
	private PriceDao priceDao = null;
	@Autowired
	private VendorDao vendorDao = null;

	/* (non-Javadoc)
	 * @see com.nscooper.mizuho.service.AllPricesFromVendorService#getAllPricesFromVendor(java.lang.String)
	 */
	@Override
	public Set<Price> getAllPricesFromVendor(String name) throws VendorNotFoundByName {
		return priceDao.findAllPricesFromVendor(vendorDao.findVendorWithName(name));
	}
}