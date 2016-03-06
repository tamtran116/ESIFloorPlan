package com.tamtran.myreceipt.business.services.impl;

import com.tamtran.myreceipt.business.services.CubeService;
import com.tamtran.myreceipt.data.dao.CubeDao;
import com.tamtran.myreceipt.data.domain.CubeDO;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CubeServiceImpl implements CubeService {
	
	@Autowired
	private CubeDao cubeDao;

	@Override
	public void addCube(CubeDO cubeDO) {
		// TODO Auto-generated method stub
		cubeDao.addCube(cubeDO);
	}

	@Override
	public List<CubeDO> listCube() {
		// TODO Auto-generated method stub
		return cubeDao.listCube();
	}
	
	@Override
	public void updateCube(CubeDO cubeDO) {
		cubeDao.updateCube(cubeDO);
	}

	@Override
	public void removeCube(String cube_id) {
		cubeDao.removeCube(cube_id);
	}

	@Override
	public List<CubeDO> listFreeCube() {
		return cubeDao.listCube();
	}
	
	@Override
	public CubeDO getCubeInfo(String cube_id) {
		return cubeDao.getCubeById(cube_id);
	}

	@Override
	public List<CubeDO> listCubesByTeam(String team) {
		return cubeDao.listCubesByTeam(team);
	}
	
	@Override
	public List<CubeDO> getClosest(String team, int c_choice){
		List<CubeDO> TeamList = cubeDao.listCubesByTeam(team);
		List<CubeDO> FreeList = cubeDao.listFreeCubes();
		List<CubeDO> ResultList;
		CubeDO[] carray = new CubeDO[5];
		float[] maxes = {50000,50000,50000,50000,50000};
		float avg_centery = 0;
		float avg_centerx = 0;
		int counter = 0;
		float minval = 50000;

		//Iterate through the TeamList	
		for(CubeDO cubeDO : TeamList) {
			avg_centerx += cubeDO.getCx();
			avg_centery += cubeDO.getCy();
			counter++;
		}
		//Calculates the mean center of an entire Team
		avg_centery /= counter;
		avg_centerx /= counter;
		System.out.println("center x and center y = " + avg_centerx + " " + avg_centery);

		//Find the nearest open cubicle to a team
		for(CubeDO cubeDO : FreeList) {
		//Use the pythagorean theorem to find their distance from the center
		//of the team
			Double var1 = cubeDO.getCx() - avg_centerx;
			Double var2 = cubeDO.getCy() - avg_centery;
			cubeDO.setCurrent_distance(Math.sqrt(Math.abs(Math.pow(var1, 2) + Math.pow(var2,2))));
		}
		Collections.sort(FreeList, new Comparator<CubeDO>() {
			@Override
			public int compare(CubeDO c1, CubeDO c2) {
				// TODO Auto-generated method stub
				return (int) (c1.getCurrent_distance() - c2.getCurrent_distance());
			}
		});
		//get 2 closest points then remove the rest open spot.
		int c_counter = FreeList.size();
		for(int i = c_counter; i>c_choice ; i--) {
				FreeList.remove(i-1);
		};
		
		//The first element in the list is the smallest (best)
		@SuppressWarnings("unchecked")
		List<CubeDO> c_union = ListUtils.union(FreeList,TeamList);
		return c_union;
	}

	@Override
	public List<CubeDO> listCubeByFloorId(int floorId) {
		return cubeDao.listCubeByFloorId(floorId);
	}
}
