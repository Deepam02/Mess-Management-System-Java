package com.hostel.mess.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * MenuItem entity representing individual food items in a menu
 * Demonstrates composition and encapsulation
 */
@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class MenuItem extends BaseEntity {
    
    @Column(name = "item_name", nullable = false)
    private String itemName;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "is_vegetarian")
    @Builder.Default
    private Boolean isVegetarian = true;
    
    @Column(name = "is_available")
    @Builder.Default
    private Boolean isAvailable = true;
    
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
    
    // Business methods
    public boolean canBeOrdered() {
        return isAvailable && menu != null && menu.getIsActive();
    }
    
    public void markUnavailable() {
        this.isAvailable = false;
    }
    
    public void markAvailable() {
        this.isAvailable = true;
    }
}
