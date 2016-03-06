package com.tamtran.myreceipt.data.dao.impl;

import com.tamtran.myreceipt.data.dao.BaseDao;
import com.tamtran.myreceipt.data.dao.CubeDao;
import com.tamtran.myreceipt.data.domain.CubeDO;
import com.tamtran.myreceipt.data.domain.FloorDO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class CubeDaoImpl extends BaseDao implements CubeDao {

	@Override
	public void addCube(CubeDO cubeDO) {
        getSessionFactory().getCurrentSession().save(cubeDO);
        System.out.print("Saved " + cubeDO.toString());
	}

	@Override
	public void updateCube(CubeDO cubeDO) {
		System.out.println("CubeDO Id " + cubeDO.getCubeId());
		getSessionFactory().getCurrentSession().update(cubeDO);
		System.out.println("Updated " + cubeDO.toString());
	}

	@Override
	public void removeCube(String cubeId) {
		CubeDO cubeDO = (CubeDO) getSessionFactory().getCurrentSession().load(CubeDO.class, cubeId);
		if (null!= cubeDO) {
			getSessionFactory().getCurrentSession().delete(cubeDO);
		}
	}

	@Override
    public List<CubeDO> listCube() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CubeDO.class);
		return listAndCast(criteria);

    }

	@Override
    public List<CubeDO> listCubeByFloorId(int floorId) {
		List<CubeDO> cubeDOList = new ArrayList<CubeDO>();
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FloorDO.class)
				.add(Restrictions.eq("floorId", floorId));
		FloorDO floorDO = (FloorDO) criteria.uniqueResult();
		if (null != floorDO) {
			cubeDOList.addAll(floorDO.getCubeDOs());
		}
		return cubeDOList;
    }

	@Override
	public CubeDO getCubeById(String cubeId) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CubeDO.class)
				.add(Restrictions.eq("cube_id", cubeId));
		return (CubeDO) criteria.uniqueResult();
	}

	@Override
	public List<CubeDO> listCubesByTeam(String teamLead) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CubeDO.class)
				.add(Restrictions.eq("teamLead", teamLead));
		return listAndCast(criteria);
	}

	@Override
	public List<CubeDO> listFreeCubes() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CubeDO.class)
				.add(Restrictions.eq("occupied", false));
		return listAndCast(criteria);
	}


}
