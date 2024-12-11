package com.shineyder.ecommerce_erp_back_end.error;

import com.shineyder.ecommerce_erp_back_end.dto.ErrorResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@SuppressWarnings("unused")
public class ValidationExceptionsHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDTO> handle(@NotNull MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();

        var errorResponseDTO = ErrorResponseDTO.builder()
            .errors(errors)
            .build();

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        return ResponseEntity.status(status).body(errorResponseDTO);
    }
}