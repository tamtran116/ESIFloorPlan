package edu.umsl.esi.floorplan.dao.impl;

import edu.umsl.esi.floorplan.domain.ReceiptDO;
import edu.umsl.esi.floorplan.util.ReceiptConstants;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Tam Tran on 10/29/2015.
 */
@Transactional
@Repository
public class ReceiptDaoImpl {
    @Autowired
    private SessionFactory sessionFactory;

    public List<ReceiptDO> getReceiptByUserName(String userName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReceiptDO.class)
                .add(Restrictions.eq("userName", userName))
                .add(Restrictions.eq("activeSwitch", "Y"));
        return criteria.list();
    }

    public ReceiptDO getReceiptByExtId(String extReceiptId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReceiptDO.class)
                .add(Restrictions.eq("extendReceiptId", extReceiptId));
        return (ReceiptDO) criteria.uniqueResult();
    }

    public ReceiptDO addReceipt(ReceiptDO receiptDO) {
        System.out.println("Saving Receipt");
        sessionFactory.getCurrentSession().save(receiptDO);
        System.out.println(ReflectionToStringBuilder.toString(receiptDO, ToStringStyle.MULTI_LINE_STYLE));
        return receiptDO;
    }

    public void updateReceipt(ReceiptDO receiptDO) {
        sessionFactory.getCurrentSession().update(receiptDO);
        System.out.println("Success update receipt: " + receiptDO.getExtendReceiptId());
    }

    public void deleteReceipt(ReceiptDO receiptDO) {
        sessionFactory.getCurrentSession().delete(receiptDO);
    }

    public void deleteReceipts(List<String> receiptIds) {
        for (String receiptId : receiptIds) {
            ReceiptDO receiptDO = getReceiptByExtId(receiptId);
            receiptDO.setActiveSwitch(ReceiptConstants.ACTIVE_SW.N.toString());
            sessionFactory.getCurrentSession().update(receiptDO);
        }
    }
}
