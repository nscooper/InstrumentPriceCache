package com.nscooper.mizuho.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nscooper.mizuho.components.DataMaintenancePoller;
import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.domain.Price;
import com.nscooper.mizuho.domain.Vendor;

/**
 * @author nick
 * @param <T>
 */
@Repository
public class PriceDaoImpl<T> extends BaseDaoHibernate implements PriceDao {

	private static Logger logger = Logger.getLogger(DataMaintenancePoller.class);
	
	@Autowired
	public void init(SessionFactory factory) {
		setSessionFactory(factory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Price> findAllPricesFromVendor(final Vendor vendor) {
		return (Set<Price>) this.getHibernateTemplate()
				.execute((HibernateCallback<T>) new HibernateCallback<Set<Price>>() {

					@Override
					public Set<Price> doInHibernate(final Session session) throws HibernateException {

						Criteria criteria = session.createCriteria(Price.class);
						criteria.add(Restrictions.eq("vendor", vendor));
						return new HashSet<Price>(criteria.list());

					}
				});
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Set<Price> findAllPricesForInstrument(final Instrument instrument) {
		if (instrument == null) {
			return null;
		}
		return (Set<Price>) getHibernateTemplate().execute((HibernateCallback<T>) new HibernateCallback<Set<Price>>() {

			@Override
			public Set<Price> doInHibernate(final Session session) throws HibernateException {

				Criteria criteria = session.createCriteria(Price.class);
				criteria.add(Restrictions.eq("instrument", instrument));
				return new HashSet<Price>(criteria.list());
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Boolean deletePricesCreatedBeyondSubmittedAge(final int ageInDays) {
		return (Boolean) getHibernateTemplate().execute((HibernateCallback<T>) new HibernateCallback<Boolean>() {

			@Override
			public Boolean doInHibernate(final Session session) throws HibernateException {
				logger.info("Deleting old prices, those that are over "+Integer.valueOf(ageInDays).toString()+" days old...");
				session.createQuery("delete from Price where created < TIMESTAMPADD ( SQL_TSI_DAY, -"
						+ Integer.valueOf(ageInDays).toString() + ", CURRENT_DATE )").executeUpdate();
				return Boolean.TRUE;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Price findPriceForInstrumentFromVendor(final Instrument instrument, final Vendor vendor) {
		if (instrument == null || vendor == null) {
			return null;
		}		
		return (Price) getHibernateTemplate().execute((HibernateCallback<T>) new HibernateCallback<Price>() {
			@Override
			public Price doInHibernate(final Session session) throws HibernateException {

				Criteria criteria = session.createCriteria(Price.class);
				criteria.add(Restrictions.eq("instrument", instrument));
				criteria.add(Restrictions.eq("vendor", vendor));
				List<Price> prices = criteria.list();
				if (prices !=null && prices.size()>0)  {
					return prices.get(0);
				} else {
					return null;
				}
			}
		});
	}

}