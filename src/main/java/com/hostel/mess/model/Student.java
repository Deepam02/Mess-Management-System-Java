package com.hostel.mess.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

/**
 * Student entity representing hostel residents
 * Demonstrates encapsulation and data modeling
 */
@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseEntity {
    
    @Column(name = "student_id", unique = true, nullable = false)
    private String studentId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "room_number")
    private String roomNumber;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    // Business methods demonstrating OOP principles
    public void deactivate() {
        this.isActive = false;
    }
    
    public void activate() {
        this.isActive = true;
    }
    
    public boolean canSubmitFeedback() {
        return this.isActive;
    }
}
