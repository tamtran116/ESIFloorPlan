package edu.umsl.esi.floorplan.services;

import java.util.List;

import edu.umsl.esi.floorplan.domain.Cube;

public interface CubeService {
	public void addCube(Cube cube);
	public Cube getCubeInfo(String cube_id);
	public List<Cube> listCube();
	public List<Cube> listFreeCube();
	public List<Cube> listCubesByTeam(String team);
	public List<Cube> getClosest(String team, int c_choice);
	public void updateCube(Cube cube);
	public void removeCube(String cube_id);
}
