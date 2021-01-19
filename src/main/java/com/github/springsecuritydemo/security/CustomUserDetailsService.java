package com.github.springsecuritydemo.security;

import com.github.springsecuritydemo.entity.User;
import com.github.springsecuritydemo.repository.UserRepository;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private GrantedAuthority DEFAULT_ROLE = new SimpleGrantedAuthority("USER");

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // 1. 查询用户
        User userFromDatabase = userRepository.findOneByLogin(login);
        if (userFromDatabase == null) {
            //log.warn("User: {} not found", login);
            throw new UsernameNotFoundException("User " + login + " was not found in db");
        }
        // 2. 设置角色
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String dbRole = userFromDatabase.getRole();
        if(StringUtils.isNullOrEmpty(dbRole)){
             grantedAuthorities.add(DEFAULT_ROLE);
        }else{
            String [] roles = dbRole.split(",");
            for (String r : roles){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(r);
                grantedAuthorities.add(grantedAuthority);
            }
        }

        return new org.springframework.security.core.userdetails.User(login,
                userFromDatabase.getPassword(), grantedAuthorities);
    }


}
