package edu.umsl.esi.floorplan.services;

import java.util.List;

import edu.umsl.esi.floorplan.domain.FloorDO;

public interface FloorService {
	void addFloor(FloorDO floor);
	FloorDO getFloorInfo(int floorId);
	List<FloorDO> listFloor();
//	List<CubeDO> listFreeCube();
//	List<CubeDO> listCubesByTeam(String team);
//	List<CubeDO> getClosest(String team, int c_choice);
//	void updateCube(CubeDO cube);
	void removeFloor(int floorId);
}
