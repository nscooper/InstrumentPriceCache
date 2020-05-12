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

import com.nscooper.mizuho.domain.Instrument;
import com.nscooper.mizuho.exceptions.InstrumentNotFoundByIsin;

/**
 * @author nick
 */
@Repository
public class InstrumentDaoImpl<T> extends BaseHibernate implements InstrumentDao {
	
	@Autowired
	public void init(SessionFactory factory) {
	    setSessionFactory(factory);
	}

	/* (non-Javadoc)
	 * @see com.nscooper.mizuho.dao.InstrumentDao#findInstrumentWithIsin(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Instrument findInstrumentWithIsin(final String isin) throws InstrumentNotFoundByIsin {
		Instrument instrument = 
		(Instrument) this.getHibernateTemplate().execute((HibernateCallback<T>) new HibernateCallback<Instrument>() {

			@Override
			public Instrument doInHibernate(final Session session) throws HibernateException {
				
				Criteria criteria = session.createCriteria(Instrument.class);
				criteria.add(Restrictions.eq("isin", isin));

				List<Instrument> instruments = criteria.list();
				if (instruments.size()<1) {
					return null;
				}
				return (Instrument) instruments.get(0);
			}
		});
		if (instrument==null) {
			throw new InstrumentNotFoundByIsin("ISIN "+isin+" not found in Instrument table!");
		}
		return instrument;
	}
}