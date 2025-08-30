package com.hostel.mess.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;

/**
 * DTO for Student registration and updates
 * Demonstrates data validation and encapsulation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    
    private Long id;
    
    @NotBlank(message = "Student ID is required")
    @Size(min = 3, max = 20, message = "Student ID must be between 3 and 20 characters")
    private String studentId;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @Size(max = 10, message = "Room number should not exceed 10 characters")
    private String roomNumber;
    
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid")
    private String phoneNumber;
    
    private Boolean isActive;
}
