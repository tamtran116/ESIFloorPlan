package edu.umsl.esi.floorplan.services;

import java.util.List;

import edu.umsl.esi.floorplan.domain.FloorDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.umsl.esi.floorplan.dao.FloorDAO;

@Service
public class FloorServiceImpl implements FloorService {
	
	@Autowired
	private FloorDAO floorDAO;

	@Override
	@Transactional
	public void addFloor(FloorDO floor) {
		// TODO Auto-generated method stub
		System.out.println(floor.toString());
		floorDAO.addFloorEntity(floor);
		System.out.println("Finished adding floor "+floor);
	}
	
	@Override
	@Transactional
	public List<FloorDO> listFloor() {return floorDAO.listFloorEntity();}

	@Override
	@Transactional
	public void removeFloor(int floorId) {floorDAO.removeFloorEntity(floorId);}

	@Override
	@Transactional
	public FloorDO getFloorInfo(int floorId) {
		// TODO Auto-generated method stub
		return floorDAO.getFloorEntityById(floorId);
	}

}
