package edu.umsl.esi.floorplan.dao;

import edu.umsl.esi.floorplan.domain.UsersDO;
import edu.umsl.esi.floorplan.domain.UserInfoDO;

/**
 * Created by Tam Tran on 7/18/2015.
 */
public interface UserDAO {
    UsersDO findByUserName(String username);

    UserInfoDO addUserInfo(UserInfoDO userInfoDO);

    void addUser(UsersDO usersDO);
}
