package edu.umsl.esi.floorplan.dao;

import edu.umsl.esi.floorplan.domain.User;
import edu.umsl.esi.floorplan.domain.UserInfo;

/**
 * Created by Tam Tran on 7/18/2015.
 */
public interface UserDAO {
    User findByUserName(String username);

    UserInfo addUserInfo(UserInfo userInfo);

    void addUser(User user);
}
