package edu.umsl.esi.floorplan.dao;

import java.util.List;

import edu.umsl.esi.floorplan.domain.RequestDO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class requestDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addRequest(RequestDO requestDO) {
        sessionFactory.getCurrentSession().save(requestDO);
        System.out.print("Saved " + requestDO.toString());
	}
	
	public void updateRequest(RequestDO requestDO) {
		sessionFactory.getCurrentSession().update(requestDO);
	}
	
	public void removeRequest(int request_id) {
		RequestDO requestDO = (RequestDO) sessionFactory.getCurrentSession().load(RequestDO.class, request_id);
		if (null!= requestDO) {
			sessionFactory.getCurrentSession().delete(requestDO);
		}
	}
	
	@SuppressWarnings("unchecked")
    public List<RequestDO> listRequest() {
 
        return sessionFactory.getCurrentSession().createQuery("from Request").list();
    }
	
	public RequestDO getRequestById(int request_id) {
		RequestDO requestDO = (RequestDO) sessionFactory.getCurrentSession().load(RequestDO.class, request_id);
		return requestDO;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
