package edu.umsl.esi.floorplan.model;

import edu.umsl.esi.floorplan.domain.Authority;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tam Tran on 7/18/2015.
 */
public class User {
    private String username;
    private String password;
    private boolean enabled;
    private Set<Authority> userRole = new HashSet<Authority>(0);

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<Authority> userRole) {
        this.userRole = userRole;
    }
}
