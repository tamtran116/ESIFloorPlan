package edu.umsl.esi.floorplan.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.umsl.esi.floorplan.dao.CubeDAO;
import edu.umsl.esi.floorplan.dao.requestDAO;
import edu.umsl.esi.floorplan.domain.Request;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	private requestDAO requestDao;
	
	@Override
	@Transactional
	public void addRequest(Request re) {
		// TODO Auto-generated method stub
		requestDao.addRequest(re);
	}

	@Override
	@Transactional
	public List<Request> listRequest() {
		return requestDao.listRequest();
	}

	@Override
	@Transactional
	public void updateRequest(Request re) {
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
