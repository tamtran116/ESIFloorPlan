package com.tamtran.myreceipt.business.services;


import com.tamtran.myreceipt.data.domain.RequestDO;

import java.util.List;

public interface RequestService {

	public void addRequest(RequestDO re);

	public List<RequestDO> listRequest();

	public void updateRequest(RequestDO re);

	public void removeRequest(int re_id);

}
