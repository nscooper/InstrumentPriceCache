package com.nscooper.mizuho.dao;
import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * This class serves as the Base class for all other Daos - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.</p>
 *
 * @author nick
 */
@SuppressWarnings("rawtypes")
public abstract class BaseDaoHibernate extends BaseHibernate implements Dao {
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveObject(Object o) {
		super.saveObject(o);
    }
	
    public void update(Object o) {
        super.update(o);
    }

    public Object getObject(Class clazz, Serializable id) {
        return super.getObject(clazz, id);
    }

    public List getObjects(Class clazz) {
        return super.getObjects(clazz);
    }

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void removeObject(Class clazz, Serializable id) {
        super.removeObject(clazz, id);
    }

}
