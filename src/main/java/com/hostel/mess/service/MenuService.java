package com.hostel.mess.service;

import com.hostel.mess.dto.MenuDto;
import com.hostel.mess.dto.MenuItemDto;
import com.hostel.mess.model.Menu;
import com.hostel.mess.model.MenuItem;
import com.hostel.mess.model.MealType;
import com.hostel.mess.repository.MenuRepository;
import com.hostel.mess.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Menu operations
 * Demonstrates composition and business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MenuService {
    
    private final MenuRepository menuRepository;
    private final FeedbackRepository feedbackRepository;
    
    public MenuDto createMenu(MenuDto menuDto) {
        log.info("Creating menu for date: {} and meal type: {}", 
                menuDto.getMenuDate(), menuDto.getMealType());
        
        // Business validation
        Optional<Menu> existingMenu = menuRepository.findByMenuDateAndMealTypeAndIsActiveTrue(
                menuDto.getMenuDate(), menuDto.getMealType());
        
        if (existingMenu.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("Menu already exists for %s - %s", 
                            menuDto.getMenuDate(), menuDto.getMealType()));
        }
        
        Menu menu = convertToEntity(menuDto);
        Menu savedMenu = menuRepository.save(menu);
        
        log.info("Menu created successfully with ID: {}", savedMenu.getId());
        return convertToDto(savedMenu);
    }
    
    @Transactional(readOnly = true)
    public List<MenuDto> getTodaysMenus() {
        log.info("Fetching today's menus");
        return menuRepository.findTodaysMenus()
                .stream()
                .map(this::convertToDtoWithStats)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<MenuDto> getMenusForDate(LocalDate date) {
        log.info("Fetching menus for date: {}", date);
        return menuRepository.findByMenuDate(date)
                .stream()
                .map(this::convertToDtoWithStats)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<MenuDto> getMenuByDateAndMealType(LocalDate date, MealType mealType) {
        return menuRepository.findByMenuDateAndMealTypeAndIsActiveTrue(date, mealType)
                .map(this::convertToDtoWithStats);
    }
    
    @Transactional(readOnly = true)
    public List<MenuDto> getUpcomingMenus() {
        log.info("Fetching upcoming menus");
        return menuRepository.findUpcomingMenus()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<MenuDto> getMenusForWeek(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        log.info("Fetching menus for week: {} to {}", startDate, endDate);
        
        return menuRepository.findMenusForDateRange(startDate, endDate)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Helper methods
    private MenuDto convertToDto(Menu menu) {
        return MenuDto.builder()
                .id(menu.getId())
                .menuDate(menu.getMenuDate())
                .mealType(menu.getMealType())
                .menuItems(convertMenuItemsToDto(menu.getMenuItems()))
                .isActive(menu.getIsActive())
                .specialNotes(menu.getSpecialNotes())
                .build();
    }
    
    private MenuDto convertToDtoWithStats(Menu menu) {
        MenuDto dto = convertToDto(menu);
        
        // Add statistics
        Double averageRating = feedbackRepository.getAverageRatingForMenu(menu);
        Long totalFeedbacks = feedbackRepository.countFeedbackForMenu(menu);
        
        dto.setAverageRating(averageRating);
        dto.setTotalFeedbacks(totalFeedbacks);
        
        return dto;
    }
    
    private List<MenuItemDto> convertMenuItemsToDto(List<MenuItem> menuItems) {
        if (menuItems == null) return null;
        
        return menuItems.stream()
                .map(this::convertMenuItemToDto)
                .collect(Collectors.toList());
    }
    
    private MenuItemDto convertMenuItemToDto(MenuItem menuItem) {
        return MenuItemDto.builder()
                .id(menuItem.getId())
                .itemName(menuItem.getItemName())
                .description(menuItem.getDescription())
                .isVegetarian(menuItem.getIsVegetarian())
                .isAvailable(menuItem.getIsAvailable())
                .price(menuItem.getPrice())
                .build();
    }
    
    private Menu convertToEntity(MenuDto dto) {
        return Menu.builder()
                .menuDate(dto.getMenuDate())
                .mealType(dto.getMealType())
                .specialNotes(dto.getSpecialNotes())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();
    }
}
