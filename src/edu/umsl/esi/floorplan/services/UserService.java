package edu.umsl.esi.floorplan.services;

import edu.umsl.esi.floorplan.model.UserRegisterRequest;

/**
 * Created by Tam Tran on 9/4/2015.
 */
public interface UserService {
    void addUser(UserRegisterRequest userRegisterRequest);
}
