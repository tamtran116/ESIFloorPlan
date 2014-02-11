package edu.umsl.esi.floorplan.services;

import java.util.List;

import edu.umsl.esi.floorplan.domain.Cube;
import edu.umsl.esi.floorplan.domain.Request;

public interface RequestService {
	public void addRequest(Request re);
	public List<Request> listRequest();
	public void updateRequest(Request re);
	public void removeRequest(int re_id);
}
