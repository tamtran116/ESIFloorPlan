package edu.umsl.esi.floorplan.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.umsl.esi.floorplan.domain.Cube;

@Transactional
@Repository
public class CubeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addCube(Cube cube) {
        sessionFactory.getCurrentSession().save(cube);
        System.out.print("Saved " + cube.toString());
	}
	
	public void updateCube(Cube cube) {
//		Cube cube = (Cube) sessionFactory.getCurrentSession().load(Cube.class, cube_id);
		System.out.println("Cube Id " + cube.getCube_id());
		sessionFactory.getCurrentSession().update(cube);
		System.out.println("Updated " + cube.toString());
	}
	
	public void removeCube(String cube_id) {
		Cube cube = (Cube) sessionFactory.getCurrentSession().load(Cube.class, cube_id);
		if (null!= cube) {
			sessionFactory.getCurrentSession().delete(cube);
		}
	}
	
	@SuppressWarnings("unchecked")
    public List<Cube> listCube() {
 
        return sessionFactory.getCurrentSession().createQuery("from Cube").list();
    }
	
	@SuppressWarnings("unchecked")
    public List<Cube> listCubeFromFloorId(int floorId) {
        Query query =  sessionFactory.getCurrentSession().createQuery("from Cube where floor_id =:floorId");
        query.setParameter("floorId",floorId);
		return query.list();
    }
	
	public Cube getCubeById(String cube_id) {
		Cube cube = (Cube) sessionFactory.getCurrentSession().load(Cube.class, cube_id);
		System.out.println(cube);
		return cube;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cube> getCubesByTeam(String team) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Cube where TEAM_LEADER = :team");
		query.setParameter("team",team);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Cube> getFreeCubes() {
		return sessionFactory.getCurrentSession().createQuery("from Cube where OCCUPIED = 0").list();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
