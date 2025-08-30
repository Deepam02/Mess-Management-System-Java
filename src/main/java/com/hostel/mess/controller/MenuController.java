package com.hostel.mess.controller;

import com.hostel.mess.dto.MenuDto;
import com.hostel.mess.model.MealType;
import com.hostel.mess.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for Menu operations
 * Demonstrates RESTful API design for menu management
 */
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class MenuController {
    
    private final MenuService menuService;
    
    @PostMapping
    public ResponseEntity<MenuDto> createMenu(@Valid @RequestBody MenuDto menuDto) {
        log.info("Creating menu for date: {} and meal type: {}", 
                menuDto.getMenuDate(), menuDto.getMealType());
        
        try {
            MenuDto createdMenu = menuService.createMenu(menuDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
        } catch (IllegalArgumentException e) {
            log.error("Error creating menu: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/today")
    public ResponseEntity<List<MenuDto>> getTodaysMenus() {
        log.info("Fetching today's menus");
        
        List<MenuDto> menus = menuService.getTodaysMenus();
        return ResponseEntity.ok(menus);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<MenuDto>> getMenusForDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Fetching menus for date: {}", date);
        
        List<MenuDto> menus = menuService.getMenusForDate(date);
        return ResponseEntity.ok(menus);
    }
    
    @GetMapping("/date/{date}/meal-type/{mealType}")
    public ResponseEntity<MenuDto> getMenuByDateAndMealType(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable MealType mealType) {
        log.info("Fetching menu for date: {} and meal type: {}", date, mealType);
        
        return menuService.getMenuByDateAndMealType(date, mealType)
                .map(menu -> ResponseEntity.ok().body(menu))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<MenuDto>> getUpcomingMenus() {
        log.info("Fetching upcoming menus");
        
        List<MenuDto> menus = menuService.getUpcomingMenus();
        return ResponseEntity.ok(menus);
    }
    
    @GetMapping("/week/{startDate}")
    public ResponseEntity<List<MenuDto>> getMenusForWeek(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        log.info("Fetching menus for week starting: {}", startDate);
        
        List<MenuDto> menus = menuService.getMenusForWeek(startDate);
        return ResponseEntity.ok(menus);
    }
    
    @GetMapping("/meal-types")
    public ResponseEntity<MealType[]> getMealTypes() {
        return ResponseEntity.ok(MealType.values());
    }
}
