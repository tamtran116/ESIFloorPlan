package com.tamtran.myreceipt.data.dao;


import com.tamtran.myreceipt.data.domain.UserInfoDO;
import com.tamtran.myreceipt.data.domain.UsersDO;

public interface UserDao {

    UsersDO findByUserName(String username);

    UserInfoDO addUserInfo(UserInfoDO userInfoDO);

    void addUser(UsersDO usersDO);
}
