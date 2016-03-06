package com.tamtran.myreceipt.business.services;


import com.tamtran.myreceipt.data.domain.FloorDO;

import java.util.List;

public interface FloorService {

	void addFloor(FloorDO floor);

	FloorDO getFloorInfo(int floorId);

	List<FloorDO> listFloor();

	void removeFloor(int floorId);
//	List<CubeDO> listFreeCube();
//	List<CubeDO> listCubesByTeam(String team);
//	List<CubeDO> getClosest(String team, int c_choice);
//	void updateCube(CubeDO cube);

}
