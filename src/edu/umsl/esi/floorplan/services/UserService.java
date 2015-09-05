package edu.umsl.esi.floorplan.services;

import edu.umsl.esi.floorplan.domain.UserInfo;
import edu.umsl.esi.floorplan.domain.UserRegisterRequest;

/**
 * Created by Tam Tran on 9/4/2015.
 */
public interface UserService {
    void addUser(UserRegisterRequest userRegisterRequest);
}
