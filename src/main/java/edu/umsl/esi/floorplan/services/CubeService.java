package edu.umsl.esi.floorplan.services;

import java.util.List;

import edu.umsl.esi.floorplan.domain.CubeDO;

public interface CubeService {
	public void addCube(CubeDO cubeDO);
	public CubeDO getCubeInfo(String cube_id);
	public List<CubeDO> listCube();
	public List<CubeDO> listCubeByFloorId(int floorId);
	public List<CubeDO> listFreeCube();
	public List<CubeDO> listCubesByTeam(String team);
	public List<CubeDO> getClosest(String team, int c_choice);
	public void updateCube(CubeDO cubeDO);
	public void removeCube(String cube_id);
}
