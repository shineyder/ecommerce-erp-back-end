package com.shineyder.ecommerce_erp_back_end.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.shineyder.ecommerce_erp_back_end.dto.UserResponseDTO;
import com.shineyder.ecommerce_erp_back_end.model.JwtBlacklist;
import com.shineyder.ecommerce_erp_back_end.model.Users;
import com.shineyder.ecommerce_erp_back_end.repository.UsersRepository;
import com.shineyder.ecommerce_erp_back_end.service.UsersService;
import com.shineyder.ecommerce_erp_back_end.validation.UserRegisterValidation;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@SuppressWarnings("unused")
public class UsersController {
    private final UsersService service;
    private final UsersRepository repository;

    public UsersController(UsersService service, UsersRepository repository){
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterValidation usersRegisterForm){
        Users user = service.register(usersRegisterForm);

        return new ResponseEntity<>(
            UserResponseDTO.transform(user),
            HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Users users){
        Map<String, String> response = service.verify(users);

        if(response.containsKey("token")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody @NotNull JsonNode token){
        return service.refresh(token.get("token").toString().replace("\"",""));
    }

    @GetMapping("/me")
    public UserResponseDTO getAuthenticatedUser(@NotNull Authentication auth){
        Users user = repository.findByEmail(auth.getName());
        return UserResponseDTO.transform(user);
    }

    @PostMapping("/logout")
    public JwtBlacklist logout(@RequestBody @NotNull JsonNode token){
        return service.logout(token.get("token").toString().replace("\"",""));
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getUsers(){
        List<Users> users = repository.findAll();

        return users.stream()
            .map(UserResponseDTO::transform)
            .collect(Collectors.toList());
    }
}
