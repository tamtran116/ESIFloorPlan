package com.tamtran.myreceipt.business.services.impl;

import com.tamtran.myreceipt.business.services.UserService;
import com.tamtran.myreceipt.common.model.UserRegisterRequest;
import com.tamtran.myreceipt.data.dao.UserDao;
import com.tamtran.myreceipt.data.domain.UserInfoDO;
import com.tamtran.myreceipt.data.domain.UsersDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Tam Tran on 9/4/2015.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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
