package edu.umsl.esi.floorplan.dao;

import edu.umsl.esi.floorplan.domain.User;

/**
 * Created by Tam Tran on 7/18/2015.
 */
public interface UserDAO {
    User findByUserName(String username);
}
