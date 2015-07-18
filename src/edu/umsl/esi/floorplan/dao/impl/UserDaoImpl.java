package edu.umsl.esi.floorplan.dao.impl;

import edu.umsl.esi.floorplan.dao.UserDAO;
import edu.umsl.esi.floorplan.domain.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tam Tran on 7/18/2015.
 */
@Repository
public class UserDaoImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findByUserName(String username) {
        List<User> users = new ArrayList<User>();

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username ));

        /*users = sessionFactory.getCurrentSession()
                .createQuery("from User where username=?")
                .setParameter(0, username)
                .list();*/

        users = criteria.list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
