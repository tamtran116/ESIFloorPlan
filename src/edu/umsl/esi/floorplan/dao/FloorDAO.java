package edu.umsl.esi.floorplan.dao;

import java.util.List;
import java.util.Set;

import edu.umsl.esi.floorplan.domain.CubeDO;
import edu.umsl.esi.floorplan.domain.FloorDO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class FloorDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addFloorEntity(FloorDO floor) {
        sessionFactory.getCurrentSession().save(floor);
        System.out.print("Saved " + floor.toString());
	}
	
	public void updateFloorEntity(FloorDO floor) {
//		FloorDO floor = (FloorDO) sessionFactory.getCurrentSession().load(FloorDO.class, floor_id);
		System.out.println("FloorDO Id " + floor.getFloorId());
		sessionFactory.getCurrentSession().update(floor);
		System.out.println("Updated " + floor.toString());
	}
	
	public void removeFloorEntity(int floor_id) {
		FloorDO floor = (FloorDO) sessionFactory.getCurrentSession().load(FloorDO.class, floor_id);
		Set<CubeDO> cubeDOSet = floor.getCubeDOs();
		if (null!= floor) {
			sessionFactory.getCurrentSession().delete(floor);
			if (!cubeDOSet.isEmpty()) {
				for (CubeDO cubeDO : cubeDOSet) {
					sessionFactory.getCurrentSession().delete(cubeDO);
				}
			}
		}
	}
	
    public List<FloorDO> listFloorEntity() {
		System.out.println("Getting floor list from DAO");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FloorDO.class);
		return criteria.list();

	}
	
	public FloorDO getFloorEntityById(int floor_id) {
		FloorDO floor = (FloorDO) sessionFactory.getCurrentSession().load(FloorDO.class, floor_id);
		System.out.println(floor);
		return floor;
	}
	
	/*public List<FloorDO> getFloorEntitysByTeam(String team) {
		Query query = sessionFactory.getCurrentSession().createQuery("from FloorEntity where TEAM_LEADER = :team");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FloorDO.class)
				.add(Restrictions.eq());

		query.setParameter("team",team);
		return query.list();
	}
	
	public List<FloorDO> getFreeFloorEntitys() {
		return sessionFactory.getCurrentSession().createQuery("from FloorEntity where OCCUPIED = 0").list();
	}*/
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
