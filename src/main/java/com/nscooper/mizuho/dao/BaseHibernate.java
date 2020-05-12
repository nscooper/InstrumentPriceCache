package com.nscooper.mizuho.dao;
import java.io.Serializable;
import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class serves as the Base class for all access
 * {@link org.springframework.orm.hibernate3.support.HibernateDaoSupport}
 * functionality. This base class also facilitates seemless flushing for <a
 * href="http://en.wikipedia.org/wiki/Create,_read,_update_and_delete">CRUD</a>
 * operations.
 *
 * @author nick
 */
@SuppressWarnings("rawtypes")
public abstract class BaseHibernate extends HibernateDaoSupport {

    /**
     * Calls {@link org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdate(Object)}<br>
     * followed by {@link org.springframework.orm.hibernate3.HibernateTemplate#flush()}
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveObject(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    /**
     * Calls {@link org.springframework.orm.hibernate3.HibernateTemplate#update(Object)}<br>
     * followed by {@link org.springframework.orm.hibernate3.HibernateTemplate#flush()}
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void update(Object o) {
        getHibernateTemplate().update(o);
    }

    /**
     * Calls {@link org.springframework.orm.hibernate3.HibernateTemplate#get(Class, Serializable)
     * @throws ObjectRetrievalFailureException
     */
	@SuppressWarnings("unchecked")
	public Object getObject(Class clazz, Serializable id) {
		Object o = getHibernateTemplate().get(clazz, id);

        if (o == null) {
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return o;
    }

    /**
     * Calls {@link org.springframework.orm.hibernate3.HibernateTemplate#loadAll(Class)}
     */
	@SuppressWarnings("unchecked")
	public List getObjects(Class clazz) {
        return getHibernateTemplate().loadAll(clazz);
    }

    /**
     * Calls {@link org.springframework.orm.hibernate3.HibernateTemplate#delete(Object)}<br>
     * followed by {@link org.springframework.orm.hibernate3.HibernateTemplate#flush()}
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void removeObject(Class clazz, Serializable id) {
        getHibernateTemplate().delete(getObject(clazz, id));
        getHibernateTemplate().flush();
    }

}
