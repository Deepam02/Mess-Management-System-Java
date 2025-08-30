package com.hostel.mess.model;

/**
 * Enum representing priority levels
 * Demonstrates prioritization and urgency classification
 */
public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");
    
    private final String displayName;
    
    Priority(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isHighPriority() {
        return this == HIGH || this == URGENT;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
