package com.shineyder.ecommerce_erp_back_end.validation;

import com.shineyder.ecommerce_erp_back_end.constraint.UniqueEmailConstraint;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("SpellCheckingInspection")
public class UserRegisterValidation {
    @Size(max = 256,message = "Email deve possuir no máximo 256 caracteres")
    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")
    @UniqueEmailConstraint
    private String email;

    @Size(min = 4, max = 16, message = "Senha deve ter entre 4 e 16 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=.]).{3,16}$", message = "Senha deve possuir pelo menos um caracter minusculo, um maiusculo, um número e um especial (@#$%^&+=.)")
    @NotBlank(message = "Senha é obrigatório")
    private String password;

    @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private Boolean isEmployee;
}
