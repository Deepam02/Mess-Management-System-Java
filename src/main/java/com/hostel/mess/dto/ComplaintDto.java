package com.hostel.mess.dto;

import com.hostel.mess.model.ComplaintCategory;
import com.hostel.mess.model.ComplaintStatus;
import com.hostel.mess.model.Priority;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO for Complaint submission and tracking
 * Demonstrates complex validation and state management
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDto {
    
    private Long id;
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
    private String description;
    
    @Builder.Default
    private ComplaintCategory category = ComplaintCategory.GENERAL;
    
    @Builder.Default
    private ComplaintStatus status = ComplaintStatus.SUBMITTED;
    
    @Builder.Default
    private Priority priority = Priority.MEDIUM;
    
    @Size(max = 1000, message = "Resolution notes should not exceed 1000 characters")
    private String resolutionNotes;
    
    private String resolvedBy;
    
    // Read-only fields for response
    private String studentName;
    private LocalDateTime submittedAt;
    private LocalDateTime lastUpdated;
}
