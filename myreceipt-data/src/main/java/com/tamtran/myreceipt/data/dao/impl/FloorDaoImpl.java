package com.tamtran.myreceipt.data.dao.impl;

import com.tamtran.myreceipt.data.dao.BaseDao;
import com.tamtran.myreceipt.data.dao.FloorDao;
import com.tamtran.myreceipt.data.domain.CubeDO;
import com.tamtran.myreceipt.data.domain.FloorDO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class FloorDaoImpl extends BaseDao implements FloorDao {

	public void addFloorEntity(FloorDO floor) {
        getSessionFactory().getCurrentSession().save(floor);
	}
	
	public void updateFloorEntity(FloorDO floor) {
		getSessionFactory().getCurrentSession().update(floor);
	}
	
	public void removeFloorEntity(int floor_id) {
		FloorDO floor = (FloorDO) getSessionFactory().getCurrentSession().load(FloorDO.class, floor_id);
		if (null!= floor) {
			Set<CubeDO> cubeDOSet = floor.getCubeDOs();
			getSessionFactory().getCurrentSession().delete(floor);
			if (!cubeDOSet.isEmpty()) {
				for (CubeDO cubeDO : cubeDOSet) {
					getSessionFactory().getCurrentSession().delete(cubeDO);
				}
			}
		}
	}
	
    public List<FloorDO> listFloorEntity() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FloorDO.class);
		return criteria.list();

	}
	
	public FloorDO getFloorEntityById(int floorId) {
		return (FloorDO) getSessionFactory().getCurrentSession().createCriteria(FloorDO.class)
				.add(Restrictions.eq("floorId", floorId)).uniqueResult();
	}
	
	/*public List<FloorDO> getFloorEntitysByTeam(String team) {
		Query query = getSessionFactory().getCurrentSession().createQuery("from FloorEntity where TEAM_LEADER = :team");
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FloorDO.class)
				.add(Restrictions.eq());

		query.setParameter("team",team);
		return query.list();
	}
	
	public List<FloorDO> getFreeFloorEntitys() {
		return getSessionFactory().getCurrentSession().createQuery("from FloorEntity where OCCUPIED = 0").list();
	}*/
}
