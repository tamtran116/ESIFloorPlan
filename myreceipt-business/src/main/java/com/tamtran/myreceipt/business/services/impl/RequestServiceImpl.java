package com.tamtran.myreceipt.business.services.impl;

import com.tamtran.myreceipt.business.services.RequestService;
import com.tamtran.myreceipt.data.dao.RequestDao;
import com.tamtran.myreceipt.data.domain.RequestDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	private RequestDao requestDao;
	
	@Override
	@Transactional
	public void addRequest(RequestDO re) {
		// TODO Auto-generated method stub
		requestDao.addRequest(re);
	}

	@Override
	@Transactional
	public List<RequestDO> listRequest() {
		return requestDao.listRequest();
	}

	@Override
	@Transactional
	public void updateRequest(RequestDO re) {
		// TODO Auto-generated method stub
		requestDao.updateRequest(re);
	}

	@Override
	@Transactional
	public void removeRequest(int re_id) {
		// TODO Auto-generated method stub
		requestDao.removeRequest(re_id);
	}

}
