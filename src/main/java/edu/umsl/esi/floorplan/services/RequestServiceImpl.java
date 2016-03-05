package edu.umsl.esi.floorplan.services;

import java.util.List;

import edu.umsl.esi.floorplan.domain.RequestDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.umsl.esi.floorplan.dao.requestDAO;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	private requestDAO requestDao;
	
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
