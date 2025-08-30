package com.hostel.mess.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

/**
 * Complaint entity representing student complaints
 * Demonstrates state management and business logic
 */
@Entity
@Table(name = "complaints")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Complaint extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description", length = 2000, nullable = false)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    @Builder.Default
    private ComplaintCategory category = ComplaintCategory.GENERAL;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private ComplaintStatus status = ComplaintStatus.SUBMITTED;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    @Builder.Default
    private Priority priority = Priority.MEDIUM;
    
    @Column(name = "resolution_notes", length = 1000)
    private String resolutionNotes;
    
    @Column(name = "resolved_by")
    private String resolvedBy;
    
    // Business methods demonstrating state management
    public void markInProgress() {
        this.status = ComplaintStatus.IN_PROGRESS;
    }
    
    public void resolve(String notes, String resolvedBy) {
        this.status = ComplaintStatus.RESOLVED;
        this.resolutionNotes = notes;
        this.resolvedBy = resolvedBy;
    }
    
    public void close() {
        if (this.status == ComplaintStatus.RESOLVED) {
            this.status = ComplaintStatus.CLOSED;
        } else {
            throw new IllegalStateException("Cannot close complaint that is not resolved");
        }
    }
    
    public boolean canBeResolved() {
        return this.status == ComplaintStatus.SUBMITTED || this.status == ComplaintStatus.IN_PROGRESS;
    }
    
    public boolean isOpen() {
        return this.status == ComplaintStatus.SUBMITTED || this.status == ComplaintStatus.IN_PROGRESS;
    }
}
