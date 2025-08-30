package com.hostel.mess.dto;

import com.hostel.mess.model.FeedbackType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO for Feedback submission and display
 * Demonstrates validation and encapsulation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    
    private Long id;
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    @NotNull(message = "Menu ID is required")
    private Long menuId;
    
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
    
    @Size(max = 1000, message = "Comments should not exceed 1000 characters")
    private String comments;
    
    @Builder.Default
    private FeedbackType feedbackType = FeedbackType.GENERAL;
    
    // Read-only fields for response
    private String studentName;
    private String menuDescription;
    private LocalDateTime submittedAt;
}
