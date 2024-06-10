package com.marceloHsousa.demo_part_api.jwt;

import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.entities.enums.Role;
import com.marceloHsousa.demo_part_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class jwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);

        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String username){

        Role role = userService.findRoleByUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}
