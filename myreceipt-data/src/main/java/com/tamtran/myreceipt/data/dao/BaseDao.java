package com.tamtran.myreceipt.data.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseDao {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //suppress warning hack
    @SuppressWarnings("unchecked")
    public <T> List<T> listAndCast(Criteria q) {
        return q.list();
    }
}
