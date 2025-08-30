package com.hostel.mess.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

/**
 * Feedback entity representing student feedback on meals
 * Demonstrates association and encapsulation
 */
@Entity
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Feedback extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;
    
    @Column(name = "rating", nullable = false)
    private Integer rating; // 1-5 scale
    
    @Column(name = "comments", length = 1000)
    private String comments;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_type")
    @Builder.Default
    private FeedbackType feedbackType = FeedbackType.GENERAL;
    
    // Business methods
    public boolean isValidRating() {
        return rating >= 1 && rating <= 5;
    }
    
    public boolean isPositive() {
        return rating >= 4;
    }
    
    public boolean isNegative() {
        return rating <= 2;
    }
    
    public void validateRating() {
        if (!isValidRating()) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }
}
