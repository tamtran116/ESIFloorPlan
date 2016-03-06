package com.tamtran.myreceipt.business.services;


import com.tamtran.myreceipt.data.domain.CubeDO;

import java.util.List;

public interface CubeService {
	void addCube(CubeDO cubeDO);
	CubeDO getCubeInfo(String cube_id);
	List<CubeDO> listCube();
	List<CubeDO> listCubeByFloorId(int floorId);
	List<CubeDO> listFreeCube();
	List<CubeDO> listCubesByTeam(String team);
	List<CubeDO> getClosest(String team, int c_choice);
	void updateCube(CubeDO cubeDO);
	void removeCube(String cube_id);
}
