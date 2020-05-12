package com.nscooper.mizuho.dao;

import com.nscooper.mizuho.domain.Vendor;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

public interface VendorDao extends Dao {

	/**
	 * Returns a set of vendors with a certain name
	 * @param name
	 * @return
	 */
	public Vendor findVendorWithName(String name) throws VendorNotFoundByName;
	
}