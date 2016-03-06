package com.tamtran.myreceipt.data.dao;

import com.tamtran.myreceipt.data.domain.RequestDO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestDao {

	void addRequest(RequestDO requestDO) ;
	
	void updateRequest(RequestDO requestDO) ;
	
	void removeRequest(int request_id) ;
	
    List<RequestDO> listRequest() ;
	
	RequestDO getRequestById(int request_id) ;
}
