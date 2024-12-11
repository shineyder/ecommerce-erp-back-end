package com.shineyder.ecommerce_erp_back_end.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponseDTO {
    private List<String> errors;
}
