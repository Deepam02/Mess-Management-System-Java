package com.hostel.mess.model;

/**
 * Enum representing different types of feedback
 * Demonstrates enumeration and categorization
 */
public enum FeedbackType {
    GENERAL("General Feedback"),
    TASTE("Taste & Quality"),
    HYGIENE("Hygiene & Cleanliness"),
    SERVICE("Service Quality"),
    SUGGESTION("Suggestion"),
    COMPLAINT("Complaint");
    
    private final String displayName;
    
    FeedbackType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
