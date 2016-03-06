package com.tamtran.myreceipt.data.dao;

import com.tamtran.myreceipt.data.domain.FloorDO;
import org.hibernate.SessionFactory;

import java.util.List;

public interface FloorDao {

	void addFloorEntity(FloorDO floor);
	
	void updateFloorEntity(FloorDO floor);
	
	void removeFloorEntity(int floor_id);
	
    List<FloorDO> listFloorEntity();
	
	FloorDO getFloorEntityById(int floor_id);
	
}
