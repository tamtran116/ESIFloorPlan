package com.tamtran.myreceipt.data.dao.impl;

import com.tamtran.myreceipt.data.dao.UserDao;
import com.tamtran.myreceipt.data.domain.UserInfoDO;
import com.tamtran.myreceipt.data.domain.UsersDO;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public UsersDO findByUserName(String username) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UsersDO.class)
                .add(Restrictions.eq("username", username ));
        return (UsersDO) criteria.uniqueResult();
    }

    @Override
    public UserInfoDO getUserInfoByUserName(String username) {
        UsersDO usersDO = (UsersDO) sessionFactory.getCurrentSession().createCriteria(UsersDO.class).add(Restrictions.eq("username", username)).uniqueResult();
        return usersDO.getUserInfoDO();
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
