package com.shineyder.ecommerce_erp_back_end.dto;

import com.shineyder.ecommerce_erp_back_end.model.Users;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResponseDTO {
    private final String id;
    private String name;
    private String email;

    public static UserResponseDTO transform(Users user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}
