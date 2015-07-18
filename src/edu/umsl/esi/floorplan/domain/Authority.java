package edu.umsl.esi.floorplan.domain;

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
    private User user;
    private String authority;

    public Authority() {
    }

    public Authority(User user, String authority) {
        this.user = user;
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
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "authority", nullable = false, length = 45)
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
