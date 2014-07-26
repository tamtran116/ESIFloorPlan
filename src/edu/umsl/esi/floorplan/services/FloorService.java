package edu.umsl.esi.floorplan.services;

import java.util.List;

import edu.umsl.esi.floorplan.domain.FloorEntity;

public interface FloorService {
	public void addFloor(FloorEntity floor);
	public FloorEntity getFloorInfo(int floorId);
	public List<FloorEntity> listFloor();
//	public List<Cube> listFreeCube();
//	public List<Cube> listCubesByTeam(String team);
//	public List<Cube> getClosest(String team, int c_choice);
//	public void updateCube(Cube cube);
//	public void removeCube(String cube_id);
}
