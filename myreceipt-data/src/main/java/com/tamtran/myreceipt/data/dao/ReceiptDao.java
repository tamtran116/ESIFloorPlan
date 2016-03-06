package com.tamtran.myreceipt.data.dao;

import com.tamtran.myreceipt.common.constant.ReceiptConstants;
import com.tamtran.myreceipt.data.domain.ReceiptDO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReceiptDao {

    List<ReceiptDO> getReceiptByUserName(String userName);

    ReceiptDO getReceiptByExtId(String extReceiptId);

    ReceiptDO addReceipt(ReceiptDO receiptDO);

    void updateReceipt(ReceiptDO receiptDO);

    void deleteReceipt(ReceiptDO receiptDO);

    void deleteReceipts(List<String> receiptIds);
}
