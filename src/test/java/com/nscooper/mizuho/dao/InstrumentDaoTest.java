package com.nscooper.mizuho.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nscooper.mizuho.BaseTest;
import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;

public class InstrumentDaoTest extends BaseTest {
	
	private static final String ISIN = "XYZ123";

	@Autowired
	private InstrumentDao instrumentDao = null;

	@Before
	public void setUp() throws Exception {
		instrumentDao.saveObject(new Instrument(ISIN, "The "+ISIN+" instrument"));
	}
	
	@After
	public void tearDown() throws Exception {
		try {
			instrumentDao.removeObject(Instrument.class, instrumentDao.findInstrumentWithIsin(ISIN).getId());
		} catch (InstrumentNotFoundByIsin e) {
			//Ignore as may already have been deleted
		}
	}

	@Test
	public void testFindInstrumentWithIsin() {
		try {
			assertNotNull(instrumentDao.findInstrumentWithIsin(ISIN));
		} catch (InstrumentNotFoundByIsin e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testSaveObject() {

		try {
			assertNotNull(instrumentDao.findInstrumentWithIsin(ISIN));  //Ensure Instrument saved during @Before
		} catch (InstrumentNotFoundByIsin e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testRemoveObject() {
		try {
			instrumentDao.removeObject(Instrument.class, instrumentDao.findInstrumentWithIsin(ISIN).getId());
			try {
				instrumentDao.findInstrumentWithIsin(ISIN);
				fail("Instrument with ISIN " + ISIN + "found, but should've been deleted!");
			} catch (InstrumentNotFoundByIsin e) {
				// ignore as correctly deleted
			}
		} catch (InstrumentNotFoundByIsin e) {
			fail(e.getLocalizedMessage());
		}
	}

}