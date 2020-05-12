package com.nscooper.mizuho.service;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nscooper.mizuho.components.PricePublisherFromCache;
import com.nscooper.mizuho.dao.InstrumentDao;
import com.nscooper.mizuho.dao.PriceDao;
import com.nscooper.mizuho.dao.VendorDao;
import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.domain.Vendor;
import com.nscooper.mizuho.domain.transit.PriceNotification;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

@Service
public class MaintainPricesServiceImpl implements MaintainPricesService {

	private static Logger logger = Logger.getLogger(MaintainPricesServiceImpl.class);
	
	@Autowired
	PriceDao priceDao;
	@Autowired
	InstrumentDao instrumentDao;
	@Autowired
	VendorDao vendorDao;
	@Autowired
	PricePublisherFromCache pricePublisherFromCache;

	@Override
	public Boolean removeOldPrices(int ageInDays) {
		return priceDao.deletePricesCreatedBeyondSubmittedAge(ageInDays);

	}

	@Override
	public void addPriceDetailsFromNotification(final PriceNotification priceNotification) throws JsonProcessingException {
		
        // does instrument exist? if not, add it
		Instrument instrument = null;
        try {
			instrument = instrumentDao.findInstrumentWithIsin(priceNotification.getInstrumentIsin());
		} catch (InstrumentNotFoundByIsin e) {
			instrument = new Instrument(priceNotification.getInstrumentIsin(),
					priceNotification.getInstrumentName());
			instrumentDao.saveObject(instrument);
	        logger.info("Created : "+instrument);
		}
        
        // does vendor exist? if not, add it
		Vendor vendor = null;
        try {
        	vendor = vendorDao.findVendorWithName(priceNotification.getVendorName());
		} catch (VendorNotFoundByName e) {
			vendor = new Vendor(priceNotification.getVendorName());
			vendorDao.saveObject(vendor);
	        logger.info("Created : "+vendor);
		}
        
        // does price exist? if not, add it
        Price price = priceDao.findPriceForInstrumentFromVendor(instrument, vendor);
        if (price==null) {
        	price = new Price();
        	price.setVendor(vendor);
        	price.setInstrument(instrument);
        }
        price.setBidPrice(priceNotification.getBidPrice());
        price.setOfferPrice(priceNotification.getOfferPrice());
        price.setCreated(new Timestamp(new java.util.Date().getTime()));
        priceDao.saveObject(price);
        logger.info("Created : "+price);
        
        //Now post priceNotification onto topic 
        pricePublisherFromCache.publish(priceNotification);
        
	}

}
