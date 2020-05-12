package com.nscooper.mizuho.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nscooper.mizuho.BaseTest;
import com.nscooper.mizuho.dao.InstrumentDao;
import com.nscooper.mizuho.dao.PriceDao;
import com.nscooper.mizuho.dao.VendorDao;
import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.domain.Vendor;
import com.nscooper.mizuho.domain.transit.PriceNotification;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

public class TranslatePricesServiceTest extends BaseTest {
	private static final String TEST_VENDOR1 = "TranslatePricesServiceTest_Vendor1";
	private static final String ISIN1 = "XYZ123";
	private static final String JSON = "{\"vendorName\":\"TranslatePricesServiceTest_Vendor1\",\"instrumentIsin\":\"XYZ123\",\"instrumentName\":\"The XYZ123 instrument\",\"bidPrice\":10.0,\"offerPrice\":15.0}";

	private Vendor vendor1 = null;
	private Instrument instrument1 = null;
	private Price price1 = null;

	@Autowired
	private InstrumentDao instrumentDao = null;
	@Autowired
	private VendorDao vendorDao = null;
	@Autowired
	private PriceDao priceDao = null;
	@Autowired
	private TranslatePricesService translatePricesService = null;

	@Before
	public void setUp() throws Exception {
		vendor1 = new Vendor(TEST_VENDOR1);
		vendorDao.saveObject(vendor1);
		instrument1 = new Instrument(ISIN1, "The " + ISIN1 + " instrument");
		instrumentDao.saveObject(instrument1);
		price1 = new Price(vendor1, instrument1, 10d, 15d, new Timestamp(new java.util.Date().getTime()));
		priceDao.saveObject(price1);
	}
	
	@After
	public void tearDown() throws Exception {
		try {
			price1 = priceDao.findPriceForInstrumentFromVendor(instrument1, vendor1);
			if (price1!=null) {
				priceDao.removeObject(Price.class, price1.getId());
			}
			vendorDao.removeObject(Vendor.class, vendorDao.findVendorWithName(TEST_VENDOR1).getId());
			instrumentDao.removeObject(Instrument.class, instrumentDao.findInstrumentWithIsin(ISIN1).getId());

		} catch (VendorNotFoundByName | InstrumentNotFoundByIsin e) {
			// Ignore as may already have been deleted
		}
	}
	
	@Test
	public void testFromPriceToPriceNotificationToJson() {
		PriceNotification pn = translatePricesService.fromPriceToPriceNotification(price1);
		try {
			String json = translatePricesService.priceNotificationToJson(pn);
			assertTrue(json.equals(JSON));
		} catch (JsonProcessingException e) {
			fail(e.getLocalizedMessage());
		}
		
	}

}
