package edu.umsl.esi.floorplan.dao;

import java.util.List;
import java.util.Set;

import edu.umsl.esi.floorplan.domain.Cube;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.umsl.esi.floorplan.domain.FloorEntity;

@Transactional
@Repository
public class FloorDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addFloorEntity(FloorEntity floor) {
        sessionFactory.getCurrentSession().save(floor);
        System.out.print("Saved " + floor.toString());
	}
	
	public void updateFloorEntity(FloorEntity floor) {
//		FloorEntity floor = (FloorEntity) sessionFactory.getCurrentSession().load(FloorEntity.class, floor_id);
		System.out.println("FloorEntity Id " + floor.getFloorId());
		sessionFactory.getCurrentSession().update(floor);
		System.out.println("Updated " + floor.toString());
	}
	
	public void removeFloorEntity(int floor_id) {
		FloorEntity floor = (FloorEntity) sessionFactory.getCurrentSession().load(FloorEntity.class, floor_id);
		Set<Cube> cubeSet = floor.getCubes();
		if (null!= floor) {
			sessionFactory.getCurrentSession().delete(floor);
			if (!cubeSet.isEmpty()) {
				for (Cube cube : cubeSet) {
					sessionFactory.getCurrentSession().delete(cube);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
    public List<FloorEntity> listFloorEntity() {
		System.out.println("Getting floor list from DAO");
        return sessionFactory.getCurrentSession().createQuery("from FloorEntity").list();
        
    }
	
	public FloorEntity getFloorEntityById(int floor_id) {
		FloorEntity floor = (FloorEntity) sessionFactory.getCurrentSession().load(FloorEntity.class, floor_id);
		System.out.println(floor);
		return floor;
	}
	
	@SuppressWarnings("unchecked")
	public List<FloorEntity> getFloorEntitysByTeam(String team) {
		Query query = sessionFactory.getCurrentSession().createQuery("from FloorEntity where TEAM_LEADER = :team");
		query.setParameter("team",team);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<FloorEntity> getFreeFloorEntitys() {
		return sessionFactory.getCurrentSession().createQuery("from FloorEntity where OCCUPIED = 0").list();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
