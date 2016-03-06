package com.tamtran.myreceipt.business.services.impl;

import com.tamtran.myreceipt.business.services.FloorService;
import com.tamtran.myreceipt.data.dao.FloorDao;
import com.tamtran.myreceipt.data.domain.FloorDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {
	
	@Autowired
	private FloorDao floorDAO;

	@Override
	public void addFloor(FloorDO floor) {
		floorDAO.addFloorEntity(floor);
		System.out.println("Finished adding floor "+floor);
	}
	
	@Override
	public List<FloorDO> listFloor() {return floorDAO.listFloorEntity();}

	@Override
	public void removeFloor(int floorId) {floorDAO.removeFloorEntity(floorId);}

	@Override
	public FloorDO getFloorInfo(int floorId) {
		return floorDAO.getFloorEntityById(floorId);
	}

}
