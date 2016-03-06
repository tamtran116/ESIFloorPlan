package com.tamtran.myreceipt.business.services;

import com.tamtran.myreceipt.common.model.UserRegisterRequest;
import com.tamtran.myreceipt.data.domain.UserInfoDO;
import com.tamtran.myreceipt.data.domain.UsersDO;

import java.util.List;

/**
 * Created by Tam Tran on 9/4/2015.
 */
public interface UserService {

    void createUser(UserRegisterRequest userRegisterRequest);

    UserInfoDO getUser(String userName);

    List<UserInfoDO> listUser();

    void updateUser(UserRegisterRequest userRegisterRequest);

    void removeUser(UserRegisterRequest userRegisterRequest);
}
