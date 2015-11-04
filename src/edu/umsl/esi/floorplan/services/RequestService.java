package edu.umsl.esi.floorplan.services;

import java.util.List;

import edu.umsl.esi.floorplan.domain.RequestDO;

public interface RequestService {
	public void addRequest(RequestDO re);
	public List<RequestDO> listRequest();
	public void updateRequest(RequestDO re);
	public void removeRequest(int re_id);
}
