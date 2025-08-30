package com.hostel.mess.model;

/**
 * Enum representing different meal types
 * Demonstrates enumeration and type safety
 */
public enum MealType {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    SNACKS("Snacks"),
    DINNER("Dinner");
    
    private final String displayName;
    
    MealType(String displayName) {
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
