/**
 * 
 */
package com.nscooper.mizuho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.nscooper.mizuho.domain.Vendor;
import com.nscooper.mizuho.exceptions.VendorNotFoundByName;

/**
 * @author nick
 *
 */
@Repository
public class VendorDaoImpl<T> extends BaseHibernate implements VendorDao {

	@Autowired
	public void init(SessionFactory factory) {
	    setSessionFactory(factory);
	}
	
	/* (non-Javadoc)
	 * @see com.nscooper.mizuho.dao.InstrumentDao#findInstrumentWithIsin(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Vendor findVendorWithName(final String name) throws VendorNotFoundByName{
		Vendor vendor = (Vendor) this.getHibernateTemplate().execute((HibernateCallback<T>) new HibernateCallback<Vendor>() {

			@Override
			public Vendor doInHibernate(final Session session) throws HibernateException {
				
				Criteria criteria = session.createCriteria(Vendor.class);
				criteria.add(Restrictions.eq("name", name));

				List<Vendor> vendors = criteria.list();
				if (vendors.size()<1) {
					return null;
				}
				return (Vendor) vendors.get(0);
			}
		});
		if (vendor==null) {
			throw new VendorNotFoundByName("Vendor "+name+" not found in Vendor table!");
		}
		return vendor;
	}
}
