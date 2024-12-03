package com.shineyder.ecommerce_erp_back_end.service;

import com.shineyder.ecommerce_erp_back_end.model.UserPrincipal;
import com.shineyder.ecommerce_erp_back_end.model.Users;
import com.shineyder.ecommerce_erp_back_end.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ShineyderUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }
}
