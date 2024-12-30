package com.shineyder.ecommerce_erp_back_end.service;

import com.shineyder.ecommerce_erp_back_end.model.JwtBlacklist;
import com.shineyder.ecommerce_erp_back_end.model.Users;
import com.shineyder.ecommerce_erp_back_end.repository.UsersRepository;
import com.shineyder.ecommerce_erp_back_end.validation.UserRegisterValidation;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@SuppressWarnings("SpellCheckingInspection")
public class UsersService {
    private final UsersRepository repository;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public UsersService(UsersRepository repository, JWTService jwtService, AuthenticationManager authManager){
        this.repository = repository;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public Users register(@NotNull UserRegisterValidation usersRegisterForm){
        Boolean isEmployee = usersRegisterForm.getIsEmployee();
        if(isEmployee == null) {
            isEmployee = false;
        }
        Users user = new Users(
                null,
                usersRegisterForm.getName(),
                usersRegisterForm.getEmail(),
                usersRegisterForm.getPassword(),
                isEmployee
        );
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Map<String, String> verify(@NotNull Users users) {
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getEmail(),users.getPassword())
            );

            if(authentication.isAuthenticated()) {
                return Collections.singletonMap("token", jwtService.generateToken(users.getEmail()));
            }

            return Collections.singletonMap("errors", "Ocorreu um erro inesperado!");
        } catch (AuthenticationException authException) {
            return Collections.singletonMap("errors", authException.getMessage());
        }
    }

    public Map<String, String> refresh(String token){
        return jwtService.refreshToken(token);
    }

    public JwtBlacklist logout(String token){
        return jwtService.logout(token);
    }
}
