package com.nscooper.mizuho.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nscooper.mizuho.BaseTest;
import com.nscooper.mizuho.domain.Vendor;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

public class VendorDaoTest extends BaseTest {
	
	private static final String TEST_VENDOR = "VendorXXXX";

	@Autowired
	private VendorDao vendorDao = null;

	@Before
	public void setUp() throws Exception {
		vendorDao.saveObject(new Vendor(TEST_VENDOR));
	}
	
	@After
	public void tearDown() throws Exception {
		try {
			vendorDao.removeObject(Vendor.class, vendorDao.findVendorWithName(TEST_VENDOR).getId());
		} catch (VendorNotFoundByName e) {
			//Ignore as may already have been deleted
		}
	}

	@Test
	public void testFindVendorWithName() {
		try {
			assertNotNull(vendorDao.findVendorWithName(TEST_VENDOR));
		} catch (VendorNotFoundByName e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testSaveObject() {
		try {
			assertNotNull(vendorDao.findVendorWithName(TEST_VENDOR));  // should exist after setup
		} catch (VendorNotFoundByName e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testRemoveObject() {
		try {
			vendorDao.removeObject(Vendor.class, vendorDao.findVendorWithName(TEST_VENDOR).getId());
			try {
				vendorDao.findVendorWithName(TEST_VENDOR);
				fail("Vendor with name " +TEST_VENDOR+ "found, but should've been deleted!");
			} catch (VendorNotFoundByName e) {
				// ignore as correctly deleted
			}
		} catch (VendorNotFoundByName e) {
			fail(e.getLocalizedMessage());
		}
	}

}