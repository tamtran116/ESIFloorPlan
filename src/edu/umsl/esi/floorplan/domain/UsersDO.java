package edu.umsl.esi.floorplan.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tam Tran on 7/18/2015.
 */
@Entity
@Table(name = "USERS")
public class UsersDO {

    private String username;
    private String password;
    private boolean enabled;
    private Set<Authority> userRole = new HashSet<Authority>(0);
    private Long userInfoId;
    private UserInfoDO userInfoDO;

    public UsersDO() {
    }

    public UsersDO(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public UsersDO(String username, String password,
                   boolean enabled, Set<Authority> userRole) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRole = userRole;
    }

    @Id
    @Column(name = "USERNAME", unique = true,
            nullable = false, length = 45)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD",
            nullable = false, length = 60)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "ENABLED", nullable = false)
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersDO")
    public Set<Authority> getAuthority() {
        return this.userRole;
    }

    public void setAuthority(Set<Authority> userRole) {
        this.userRole = userRole;
    }

    @Column(name = "USER_INFO_ID", nullable = false)
    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_INFO_ID", insertable = false, updatable = false)
    public UserInfoDO getUserInfoDO() {
        return userInfoDO;
    }

    public void setUserInfoDO(UserInfoDO userInfoDO) {
        this.userInfoDO = userInfoDO;
    }
}
