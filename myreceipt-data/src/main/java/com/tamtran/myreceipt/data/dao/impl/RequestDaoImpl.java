package com.tamtran.myreceipt.data.dao.impl;

import com.tamtran.myreceipt.data.dao.BaseDao;
import com.tamtran.myreceipt.data.dao.RequestDao;
import com.tamtran.myreceipt.data.domain.RequestDO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class RequestDaoImpl extends BaseDao implements RequestDao {

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
	
    public List<RequestDO> listRequest() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RequestDO.class);
		return criteria.list();
	}
	
	public RequestDO getRequestById(int request_id) {
		RequestDO requestDO = (RequestDO) sessionFactory.getCurrentSession().load(RequestDO.class, request_id);
		return requestDO;
	}
}
