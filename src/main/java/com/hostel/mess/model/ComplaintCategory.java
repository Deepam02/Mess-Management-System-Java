package com.hostel.mess.model;

/**
 * Enum representing complaint categories
 * Demonstrates categorization and organization
 */
public enum ComplaintCategory {
    FOOD_QUALITY("Food Quality"),
    HYGIENE("Hygiene & Cleanliness"),
    SERVICE("Service Quality"),
    INFRASTRUCTURE("Infrastructure"),
    STAFF_BEHAVIOR("Staff Behavior"),
    TIMING("Meal Timing"),
    GENERAL("General");
    
    private final String displayName;
    
    ComplaintCategory(String displayName) {
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
