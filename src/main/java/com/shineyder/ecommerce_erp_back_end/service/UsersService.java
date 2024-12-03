package com.shineyder.ecommerce_erp_back_end.service;

import com.shineyder.ecommerce_erp_back_end.model.Users;
import com.shineyder.ecommerce_erp_back_end.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository repository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public String verify(Users users) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUsername(),users.getPassword())
        );

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(users.getUsername());
        }

        return "Fail";
    }
}
