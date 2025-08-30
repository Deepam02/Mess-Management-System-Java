package com.hostel.mess.dto;

import com.hostel.mess.model.MealType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for Menu display and management
 * Demonstrates composition and data transfer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    
    private Long id;
    
    @NotNull(message = "Menu date is required")
    @FutureOrPresent(message = "Menu date cannot be in the past")
    private LocalDate menuDate;
    
    @NotNull(message = "Meal type is required")
    private MealType mealType;
    
    private List<MenuItemDto> menuItems;
    
    private Boolean isActive;
    
    @Size(max = 500, message = "Special notes should not exceed 500 characters")
    private String specialNotes;
    
    private Double averageRating;
    
    private Long totalFeedbacks;
}
