package com.hostel.mess.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * Menu entity representing daily/weekly mess menu
 * Demonstrates composition and encapsulation
 */
@Entity
@Table(name = "menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity {
    
    @Column(name = "menu_date", nullable = false)
    private LocalDate menuDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", nullable = false)
    private MealType mealType;
    
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menuItems;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @Column(name = "special_notes")
    private String specialNotes;
    
    // Business methods
    public boolean isCurrentMenu() {
        return menuDate.equals(LocalDate.now()) && isActive;
    }
    
    public void addMenuItem(MenuItem item) {
        this.menuItems.add(item);
        item.setMenu(this);
    }
    
    public void removeMenuItem(MenuItem item) {
        this.menuItems.remove(item);
        item.setMenu(null);
    }
}
