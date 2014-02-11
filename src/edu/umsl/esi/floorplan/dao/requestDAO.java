package edu.umsl.esi.floorplan.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.umsl.esi.floorplan.domain.Request;

@Repository
public class requestDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addRequest(Request request) {
        sessionFactory.getCurrentSession().save(request);
        System.out.print("Saved " + request.toString());
	}
	
	public void updateRequest(Request request) {
		sessionFactory.getCurrentSession().update(request);
	}
	
	public void removeRequest(int request_id) {
		Request request = (Request) sessionFactory.getCurrentSession().load(Request.class, request_id);
		if (null!= request) {
			sessionFactory.getCurrentSession().delete(request);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
    public List<Request> listRequest() {
 
        return sessionFactory.getCurrentSession().createQuery("from Request").list();
    }
	
	public Request getRequestById(int request_id) {
		Request request = (Request) sessionFactory.getCurrentSession().load(Request.class, request_id);
		return request;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
