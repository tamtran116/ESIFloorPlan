package edu.umsl.esi.floorplan.dao.impl;

import edu.umsl.esi.floorplan.dao.UserDAO;
import edu.umsl.esi.floorplan.domain.UsersDO;
import edu.umsl.esi.floorplan.domain.UserInfoDO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tam Tran on 7/18/2015.
 */
@Transactional
@Repository
public class UserDaoImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public UsersDO findByUserName(String username) {
        List<UsersDO> usersDOs = new ArrayList<UsersDO>();

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UsersDO.class)
                .add(Restrictions.eq("username", username ));

        /*usersDOs = sessionFactory.getCurrentSession()
                .createQuery("from UsersDO where username=?")
                .setParameter(0, username)
                .list();*/

        usersDOs = criteria.list();

        if (usersDOs.size() > 0) {
            return usersDOs.get(0);
        } else {
            return null;
        }
    }

    @Override
    public UserInfoDO addUserInfo(UserInfoDO userInfoDO) {
        sessionFactory.getCurrentSession().save(userInfoDO);
        return userInfoDO;
    }

    @Override
    public void addUser(UsersDO usersDO) {
        sessionFactory.getCurrentSession().save(usersDO);
    }
}
