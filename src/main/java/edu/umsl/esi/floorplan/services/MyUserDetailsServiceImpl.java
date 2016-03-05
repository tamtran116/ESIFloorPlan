package edu.umsl.esi.floorplan.services;

import edu.umsl.esi.floorplan.dao.UserDAO;
import edu.umsl.esi.floorplan.domain.Authority;
import edu.umsl.esi.floorplan.domain.UsersDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UsersDO usersDO = userDAO.findByUserName(userName);
        List<GrantedAuthority> authorities = buildUserAuthority(usersDO.getAuthority());
        return buildUserForAuthentication(usersDO, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<Authority> authorities) {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // build user's authority
        for (Authority authority : authorities) {
            setAuths.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
        return result;
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(UsersDO usersDO, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(usersDO.getUsername(), usersDO.getPassword(), usersDO.isEnabled(), true, true, true, authorities);
    }


}
