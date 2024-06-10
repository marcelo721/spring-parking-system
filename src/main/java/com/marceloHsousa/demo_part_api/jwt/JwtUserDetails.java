package com.marceloHsousa.demo_part_api.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;



public class JwtUserDetails extends User {

    private com.marceloHsousa.demo_part_api.entities.User user;

    public JwtUserDetails(com.marceloHsousa.demo_part_api.entities.User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Long getId(){
        return this.user.getId();
    }

    public String getRole(){
        return this.user.getRole().name();
    }
}
