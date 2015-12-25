package edu.umsl.esi.floorplan.dao;

import java.util.ArrayList;
import java.util.List;

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
	
    public List<CubeDO> listCube() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CubeDO.class);
		return criteria.list();
    }
	
    public List<CubeDO> listCubeFromFloorId(int floorId) {
		List<CubeDO> cubeDOList = new ArrayList<CubeDO>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FloorDO.class)
				.add(Restrictions.eq("floorId", floorId));
		FloorDO floorDO = (FloorDO) criteria.uniqueResult();
		if (null != floorDO) {
			cubeDOList.addAll(floorDO.getCubeDOs());
		}
		return cubeDOList;
    }
	
	public CubeDO getCubeById(String cube_id) {
		CubeDO cubeDO = (CubeDO) sessionFactory.getCurrentSession().load(CubeDO.class, cube_id);
		System.out.println(cubeDO);
		return cubeDO;
	}
	
	@SuppressWarnings("unchecked")
	public List<CubeDO> getCubesByTeam(String teamLead) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CubeDO.class)
				.add(Restrictions.eq("teamLead", teamLead));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<CubeDO> getFreeCubes() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CubeDO.class)
				.add(Restrictions.eq("occupied", false));
		return criteria.list();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
