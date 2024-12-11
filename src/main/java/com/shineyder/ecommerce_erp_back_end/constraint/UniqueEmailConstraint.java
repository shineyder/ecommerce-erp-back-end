package com.shineyder.ecommerce_erp_back_end.constraint;

import com.shineyder.ecommerce_erp_back_end.validation.UniqueEmailValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidation.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("unused")
public @interface UniqueEmailConstraint {
    String message() default "Email já em uso";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
