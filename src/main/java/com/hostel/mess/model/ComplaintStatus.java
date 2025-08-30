package com.hostel.mess.model;

/**
 * Enum representing complaint status lifecycle
 * Demonstrates state management and workflow
 */
public enum ComplaintStatus {
    SUBMITTED("Submitted"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved"),
    CLOSED("Closed"),
    REJECTED("Rejected");
    
    private final String displayName;
    
    ComplaintStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isOpen() {
        return this == SUBMITTED || this == IN_PROGRESS;
    }
    
    public boolean isClosed() {
        return this == CLOSED || this == REJECTED;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
