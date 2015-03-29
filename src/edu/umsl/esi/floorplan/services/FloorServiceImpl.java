package edu.umsl.esi.floorplan.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.Math;

import org.apache.commons.collections.ListUtils;

import edu.umsl.esi.floorplan.dao.CubeDAO;
import edu.umsl.esi.floorplan.dao.FloorDAO;
import edu.umsl.esi.floorplan.domain.Cube;
import edu.umsl.esi.floorplan.domain.FloorEntity;

@Service
public class FloorServiceImpl implements FloorService {
	
	@Autowired
	private FloorDAO floorDAO;

	@Override
	@Transactional
	public void addFloor(FloorEntity floor) {
		// TODO Auto-generated method stub
		System.out.println(floor.toString());
		floorDAO.addFloorEntity(floor);
		System.out.println("Finished adding floor "+floor);
	}
	
	@Override
	@Transactional
	public List<FloorEntity> listFloor() {return floorDAO.listFloorEntity();}

	@Override
	@Transactional
	public void removeFloor(int floorId) {floorDAO.removeFloorEntity(floorId);}

	@Override
	@Transactional
	public FloorEntity getFloorInfo(int floorId) {
		// TODO Auto-generated method stub
		return floorDAO.getFloorEntityById(floorId);
	}

}
