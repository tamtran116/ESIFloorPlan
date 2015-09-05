package edu.umsl.esi.floorplan.services;

import edu.umsl.esi.floorplan.dao.UserDAO;
import edu.umsl.esi.floorplan.domain.UserInfo;
import edu.umsl.esi.floorplan.domain.UserRegisterRequest;
import edu.umsl.esi.floorplan.domain.User;
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
        UserInfo userInfo = new UserInfo();
        User user = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

        userInfo.setFirstName(userRegisterRequest.getFirstName());
        userInfo.setLastName(userRegisterRequest.getLastName());
        userInfo.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        userInfo.setEmail(userRegisterRequest.getEmail());

        user.setUsername(userRegisterRequest.getUserName());
        user.setPassword(hashedPassword);
        userDao.addUserInfo(userInfo);

        user.setUserInfoId(userInfo.getUserInfoId());
        user.setEnabled(true);
        userDao.addUser(user);
    }
}
