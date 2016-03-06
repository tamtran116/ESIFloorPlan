package com.tamtran.myreceipt.data.dao.impl;

import com.tamtran.myreceipt.common.constant.ReceiptConstants;
import com.tamtran.myreceipt.data.dao.BaseDao;
import com.tamtran.myreceipt.data.dao.ReceiptDao;
import com.tamtran.myreceipt.data.domain.ReceiptDO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ReceiptDaoImpl extends BaseDao implements ReceiptDao{

    public List<ReceiptDO> getReceiptByUserName(String userName) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ReceiptDO.class)
                .add(Restrictions.eq("userName", userName))
                .add(Restrictions.eq("activeSwitch", "Y"));
        return criteria.list();
    }

    public ReceiptDO getReceiptByExtId(String extReceiptId) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ReceiptDO.class)
                .add(Restrictions.eq("extendReceiptId", extReceiptId));
        return (ReceiptDO) criteria.uniqueResult();
    }

    public ReceiptDO addReceipt(ReceiptDO receiptDO) {
        getSessionFactory().getCurrentSession().save(receiptDO);
        return receiptDO;
    }

    public void updateReceipt(ReceiptDO receiptDO) {
        getSessionFactory().getCurrentSession().update(receiptDO);
    }

    public void deleteReceipt(ReceiptDO receiptDO) {
        getSessionFactory().getCurrentSession().delete(receiptDO);
    }

    public void deleteReceipts(List<String> receiptIds) {
        for (String receiptId : receiptIds) {
            ReceiptDO receiptDO = getReceiptByExtId(receiptId);
            receiptDO.setActiveSwitch(ReceiptConstants.ACTIVE_SW.N.toString());
            getSessionFactory().getCurrentSession().update(receiptDO);
        }
    }
}
