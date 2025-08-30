package com.hostel.mess.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO for MenuItem display and management
 * Demonstrates data validation and transfer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDto {
    
    private Long id;
    
    @NotBlank(message = "Item name is required")
    @Size(min = 2, max = 100, message = "Item name must be between 2 and 100 characters")
    private String itemName;
    
    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;
    
    @Builder.Default
    private Boolean isVegetarian = true;
    
    @Builder.Default
    private Boolean isAvailable = true;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Price must have at most 8 digits and 2 decimal places")
    private BigDecimal price;
}
