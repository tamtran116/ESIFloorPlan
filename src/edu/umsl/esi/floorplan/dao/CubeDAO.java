package edu.umsl.esi.floorplan.dao;

import java.util.List;

import edu.umsl.esi.floorplan.domain.CubeDO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class CubeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addCube(CubeDO cubeDO) {
        sessionFactory.getCurrentSession().save(cubeDO);
        System.out.print("Saved " + cubeDO.toString());
	}
	
	public void updateCube(CubeDO cubeDO) {
//		CubeDO cubeDO = (CubeDO) sessionFactory.getCurrentSession().load(CubeDO.class, cube_id);
		System.out.println("CubeDO Id " + cubeDO.getCube_id());
		sessionFactory.getCurrentSession().update(cubeDO);
		System.out.println("Updated " + cubeDO.toString());
	}
	
	public void removeCube(String cube_id) {
		CubeDO cubeDO = (CubeDO) sessionFactory.getCurrentSession().load(CubeDO.class, cube_id);
		if (null!= cubeDO) {
			sessionFactory.getCurrentSession().delete(cubeDO);
		}
	}
	
	@SuppressWarnings("unchecked")
    public List<CubeDO> listCube() {
 
        return sessionFactory.getCurrentSession().createQuery("from Cube").list();
    }
	
	@SuppressWarnings("unchecked")
    public List<CubeDO> listCubeFromFloorId(int floorId) {
        Query query =  sessionFactory.getCurrentSession().createQuery("from Cube where floor_id =:floorId");
        query.setParameter("floorId",floorId);
		return query.list();
    }
	
	public CubeDO getCubeById(String cube_id) {
		CubeDO cubeDO = (CubeDO) sessionFactory.getCurrentSession().load(CubeDO.class, cube_id);
		System.out.println(cubeDO);
		return cubeDO;
	}
	
	@SuppressWarnings("unchecked")
	public List<CubeDO> getCubesByTeam(String team) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Cube where TEAM_LEADER = :team");
		query.setParameter("team",team);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<CubeDO> getFreeCubes() {
		return sessionFactory.getCurrentSession().createQuery("from Cube where OCCUPIED = 0").list();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
