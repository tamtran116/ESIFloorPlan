package com.tamtran.myreceipt.common.model;

/**
 * Created by Tam Tran on 7/18/2015.
 */
public class Authority {

    private Integer userRoleId;
    private User user;
    private String role;

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
