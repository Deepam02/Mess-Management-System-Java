package com.hostel.mess.repository;

import com.hostel.mess.model.Menu;
import com.hostel.mess.model.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Menu entity
 * Demonstrates query methods and custom queries
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    
    List<Menu> findByMenuDate(LocalDate menuDate);
    
    List<Menu> findByMenuDateAndMealType(LocalDate menuDate, MealType mealType);
    
    Optional<Menu> findByMenuDateAndMealTypeAndIsActiveTrue(LocalDate menuDate, MealType mealType);
    
    List<Menu> findByIsActiveTrueOrderByMenuDateDescMealTypeAsc();
    
    @Query("SELECT m FROM Menu m WHERE m.menuDate BETWEEN :startDate AND :endDate AND m.isActive = true")
    List<Menu> findMenusForDateRange(@Param("startDate") LocalDate startDate, 
                                   @Param("endDate") LocalDate endDate);
    
    @Query("SELECT m FROM Menu m WHERE m.menuDate = CURRENT_DATE AND m.isActive = true")
    List<Menu> findTodaysMenus();
    
    @Query("SELECT m FROM Menu m WHERE m.menuDate >= CURRENT_DATE AND m.isActive = true ORDER BY m.menuDate ASC")
    List<Menu> findUpcomingMenus();
}
