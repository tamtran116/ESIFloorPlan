package com.tamtran.myreceipt.data.domain;

import javax.persistence.*;

/**
 * Created by Tam Tran on 7/18/2015.
 */
@Entity
@Table(name = "AUTHORITIES",
        uniqueConstraints = @UniqueConstraint(
                columnNames = { "authority", "username" }))
public class Authority {
    private Integer userRoleId;
    private UsersDO usersDO;
    private String authority;

    public Authority() {
    }

    public Authority(UsersDO usersDO, String authority) {
        this.usersDO = usersDO;
        this.authority = authority;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id",
            unique = true, nullable = false)
    public Integer getUserRoleId() {
        return this.userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    public UsersDO getUsersDO() {
        return this.usersDO;
    }

    public void setUsersDO(UsersDO usersDO) {
        this.usersDO = usersDO;
    }

    @Column(name = "authority", nullable = false, length = 45)
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
