package edu.umsl.esi.floorplan.services;

import edu.umsl.esi.floorplan.dao.UserDAO;
import edu.umsl.esi.floorplan.domain.UserInfoDO;
import edu.umsl.esi.floorplan.model.UserRegisterRequest;
import edu.umsl.esi.floorplan.domain.UsersDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Tam Tran on 9/4/2015.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;

    @Override
    public void addUser(UserRegisterRequest userRegisterRequest) {
        UserInfoDO userInfoDO = new UserInfoDO();
        UsersDO usersDO = new UsersDO();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

        userInfoDO.setFirstName(userRegisterRequest.getFirstName());
        userInfoDO.setLastName(userRegisterRequest.getLastName());
        userInfoDO.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        userInfoDO.setEmail(userRegisterRequest.getEmail());

        usersDO.setUsername(userRegisterRequest.getUserName());
        usersDO.setPassword(hashedPassword);
        userDao.addUserInfo(userInfoDO);

        usersDO.setUserInfoId(userInfoDO.getUserInfoId());
        usersDO.setEnabled(true);
        userDao.addUser(usersDO);
    }
}
