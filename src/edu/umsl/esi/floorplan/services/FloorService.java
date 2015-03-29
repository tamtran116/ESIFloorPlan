package edu.umsl.esi.floorplan.services;

import java.util.List;

import edu.umsl.esi.floorplan.domain.FloorEntity;

public interface FloorService {
	void addFloor(FloorEntity floor);
	FloorEntity getFloorInfo(int floorId);
	List<FloorEntity> listFloor();
//	List<Cube> listFreeCube();
//	List<Cube> listCubesByTeam(String team);
//	List<Cube> getClosest(String team, int c_choice);
//	void updateCube(Cube cube);
	void removeFloor(int floorId);
}
