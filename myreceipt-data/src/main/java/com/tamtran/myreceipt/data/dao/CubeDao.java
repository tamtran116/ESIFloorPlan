package com.tamtran.myreceipt.data.dao;

import com.tamtran.myreceipt.data.domain.CubeDO;

import java.util.List;

public interface CubeDao {

    void addCube(CubeDO cubeDO);

    void updateCube(CubeDO cubeDO);

    void removeCube(String cubeId);

    List<CubeDO> listCube();

    List<CubeDO> listCubeByFloorId(int floorId);

    List<CubeDO> listCubesByTeam(String teamLead);

    List<CubeDO> listFreeCubes();

    CubeDO getCubeById(String cubeId);

}
