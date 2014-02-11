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
import edu.umsl.esi.floorplan.domain.Cube;

@Service
public class CubeServiceImpl implements CubeService {
	
	@Autowired
	private CubeDAO cubeDAO;

	@Override
	@Transactional
	public void addCube(Cube cube) {
		// TODO Auto-generated method stub
		cubeDAO.addCube(cube);
	}

	@Override
	@Transactional
	public List<Cube> listCube() {
		// TODO Auto-generated method stub
		return cubeDAO.listCube();
	}
	
	@Override
	@Transactional
	public void updateCube(Cube cube) {
		cubeDAO.updateCube(cube);
	}

	@Override
	@Transactional
	public void removeCube(String cube_id) {
		cubeDAO.removeCube(cube_id);
	}
	@Override
	@Transactional
	public List<Cube> listFreeCube() {
		return cubeDAO.getFreeCubes();
	}
	
	@Override
	@Transactional
	public Cube getCubeInfo(String cube_id) {
		return cubeDAO.getCubeById(cube_id);
	}
	@Override
	@Transactional
	public List<Cube> listCubesByTeam(String team) {
		return cubeDAO.getCubesByTeam(team);
	}
	
	@SuppressWarnings("null")
	@Override
	@Transactional
	public List<Cube> getClosest(String team, int c_choice){
		List<Cube> TeamList = cubeDAO.getCubesByTeam(team);
		List<Cube> FreeList = cubeDAO.getFreeCubes();
		List<Cube> ResultList;
		Cube[] carray = new Cube[5];
		float[] maxes = {50000,50000,50000,50000,50000};
		float avg_centery = 0;
		float avg_centerx = 0;
		int counter = 0;
		float minval = 50000;

		//Iterate through the TeamList	
		for(Cube cube : TeamList) {
			avg_centerx += cube.getCx();
			avg_centery += cube.getCy();
			counter++;
		}
		//Calculates the mean center of an entire Team
		avg_centery /= counter;
		avg_centerx /= counter;
		System.out.println("center x and center y = " + avg_centerx + " " + avg_centery);

		//Find the nearest open cubicle to a team
		for(Cube cube : FreeList) {
		//Use the pythagorean theorem to find their distance from the center
		//of the team
			Double var1 = cube.getCx() - avg_centerx;
			Double var2 = cube.getCy() - avg_centery;
			cube.setCurrent_distance(Math.sqrt(Math.abs(Math.pow(var1, 2) + Math.pow(var2,2))));
		}
		Collections.sort(FreeList, new Comparator<Cube>() {
			@Override
			public int compare(Cube c1, Cube c2) {
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
		List<Cube> c_union = ListUtils.union(FreeList,TeamList);
		return c_union;
	}
}
