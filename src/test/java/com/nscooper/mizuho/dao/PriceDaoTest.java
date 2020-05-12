package com.nscooper.mizuho.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nscooper.mizuho.BaseTest;
import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.domain.Vendor;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

public class PriceDaoTest extends BaseTest {

	private static final String TEST_VENDOR1 = "PriceDaoTest_Vendor1";
	private static final String TEST_VENDOR2 = "PriceDaoTest_Vendor2";
	private static final String ISIN1 = "XYZ123";
	private static final String ISIN2 = "XYZ456";
	private static final String ISIN3 = "XYZ456";

	private Vendor vendor1 = null;
	private Vendor vendor2 = null;
	private Instrument instrument1 = null;
	private Instrument instrument2 = null;
	private Instrument instrument3 = null;
	private Price price1 = null;
	private Price price2 = null;
	private Price price3 = null;
	private Price price4 = null;

	@Autowired
	private InstrumentDao instrumentDao = null;

	@Autowired
	private VendorDao vendorDao = null;

	@Autowired
	private PriceDao priceDao = null;

	@Before
	public void setUp() throws Exception {

		vendorDao.saveObject(new Vendor(TEST_VENDOR1));
		vendorDao.saveObject(new Vendor(TEST_VENDOR2));
		vendor1 = vendorDao.findVendorWithName(TEST_VENDOR1);
		vendor2 = vendorDao.findVendorWithName(TEST_VENDOR2);

		instrumentDao.saveObject(new Instrument(ISIN1, "The " + ISIN1 + " instrument"));
		instrumentDao.saveObject(new Instrument(ISIN2, "The " + ISIN2 + " instrument"));
		instrumentDao.saveObject(new Instrument(ISIN3, "The " + ISIN3 + " instrument"));
		instrument1 = instrumentDao.findInstrumentWithIsin(ISIN1);
		instrument2 = instrumentDao.findInstrumentWithIsin(ISIN2);
		instrument3 = instrumentDao.findInstrumentWithIsin(ISIN3);

		priceDao.saveObject(new Price(vendor1, instrument1, 10d, 15d, new Timestamp(new java.util.Date().getTime())));
		priceDao.saveObject(new Price(vendor2, instrument1, 10d, 15d, new Timestamp(new java.util.Date().getTime())));
		priceDao.saveObject(new Price(vendor1, instrument2, 10d, 15d, new Timestamp(new java.util.Date().getTime())));
		priceDao.saveObject(new Price(vendor1, instrument3, 10d, 15d, new Timestamp(new java.util.Date().getTime())));
	}

	@After
	public void tearDown() throws Exception {
		try {
			price1 = priceDao.findPriceForInstrumentFromVendor(instrument1, vendor1);
			if (price1!=null) {
				priceDao.removeObject(Price.class, price1.getId());
			}
			
			price2 = priceDao.findPriceForInstrumentFromVendor(instrument1, vendor2);
			if (price2!=null) {
				priceDao.removeObject(Price.class, price2.getId());
			}
			
			price3 = priceDao.findPriceForInstrumentFromVendor(instrument2, vendor1);
			if (price3!=null) {
				priceDao.removeObject(Price.class, price3.getId());
			}
			
			price4 = priceDao.findPriceForInstrumentFromVendor(instrument3, vendor1);
			if (price4!=null) {
				priceDao.removeObject(Price.class, price4.getId());
			}
			
			vendorDao.removeObject(Vendor.class, vendorDao.findVendorWithName(TEST_VENDOR1).getId());
			vendorDao.removeObject(Vendor.class, vendorDao.findVendorWithName(TEST_VENDOR2).getId());

			instrumentDao.removeObject(Instrument.class, instrumentDao.findInstrumentWithIsin(ISIN1).getId());
			instrumentDao.removeObject(Instrument.class, instrumentDao.findInstrumentWithIsin(ISIN2).getId());
			instrumentDao.removeObject(Instrument.class, instrumentDao.findInstrumentWithIsin(ISIN3).getId());

		} catch (VendorNotFoundByName | InstrumentNotFoundByIsin e) {
			// Ignore as may already have been deleted
		}
	}

	@Test
	public void testSaveObject() {

		price1 = priceDao.findPriceForInstrumentFromVendor(instrument1, vendor1);
		assertNotNull(price1);
		price2 = priceDao.findPriceForInstrumentFromVendor(instrument1, vendor2);
		assertNotNull(price2);
		price3 = priceDao.findPriceForInstrumentFromVendor(instrument2, vendor1);
		assertNotNull(price2);
		price4 = priceDao.findPriceForInstrumentFromVendor(instrument3, vendor1);
		assertNotNull(price3);
	}

	@Test
	public void testRemoveObject() {

		priceDao.removeObject(Price.class, priceDao.findPriceForInstrumentFromVendor(instrument1, vendor1).getId());
		try {
			priceDao.findPriceForInstrumentFromVendor(instrumentDao.findInstrumentWithIsin(ISIN1),
					vendorDao.findVendorWithName(TEST_VENDOR1));
			fail("Price with Isin " + ISIN1 + " for vendor " + TEST_VENDOR1 + " found, but should've been deleted!");
		} catch (VendorNotFoundByName | InstrumentNotFoundByIsin e) {
			// ignore as correctly deleted
		}
	}

	@Test
	public void testFindAllPricesFromVendor() {
		try {
			Set<Price> prices = priceDao.findAllPricesFromVendor(vendorDao.findVendorWithName(TEST_VENDOR1));
			assertTrue("Should be three price records for vendor: "+TEST_VENDOR1, prices.size()==3);
		} catch (VendorNotFoundByName e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testFindAllPricesForInstrument() {
		try {
			Set<Price> prices = priceDao.findAllPricesForInstrument(instrumentDao.findInstrumentWithIsin(ISIN1));
			assertTrue("Should be two price records for instrument: "+ISIN1, prices.size()==2);
		} catch (InstrumentNotFoundByIsin e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void findPriceForInstrumentFromVendor() {
		assertNotNull(priceDao.findPriceForInstrumentFromVendor(instrument3, vendor1));
	}

}