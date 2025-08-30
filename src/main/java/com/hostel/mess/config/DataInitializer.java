package com.hostel.mess.config;

import com.hostel.mess.model.*;
import com.hostel.mess.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Data initialization class for sample data
 * Demonstrates data seeding and system setup
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final StudentRepository studentRepository;
    private final MenuRepository menuRepository;
    private final FeedbackRepository feedbackRepository;
    private final ComplaintRepository complaintRepository;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing sample data...");
        
        if (studentRepository.count() == 0) {
            initializeStudents();
            initializeMenus();
            initializeFeedback();
            initializeComplaints();
            
            log.info("Sample data initialization completed!");
            log.info("🎉 System is ready to use!");
        } else {
            log.info("Sample data already exists, skipping initialization");
        }
    }
    
    private void initializeStudents() {
        List<Student> students = Arrays.asList(
            Student.builder()
                    .studentId("ST001")
                    .name("Alice Johnson")
                    .email("alice.johnson@hostel.com")
                    .roomNumber("A-101")
                    .phoneNumber("9876543210")
                    .build(),
            
            Student.builder()
                    .studentId("ST002")
                    .name("Bob Smith")
                    .email("bob.smith@hostel.com")
                    .roomNumber("A-102")
                    .phoneNumber("9876543211")
                    .build(),
            
            Student.builder()
                    .studentId("ST003")
                    .name("Charlie Brown")
                    .email("charlie.brown@hostel.com")
                    .roomNumber("B-201")
                    .phoneNumber("9876543212")
                    .build()
        );
        
        studentRepository.saveAll(students);
        log.info("Sample students created: {}", students.size());
    }
    
    private void initializeMenus() {
        // Today's menus
        Menu breakfastMenu = Menu.builder()
                .menuDate(LocalDate.now())
                .mealType(MealType.BREAKFAST)
                .specialNotes("Fresh bread and organic fruits available")
                .build();
        
        Menu lunchMenu = Menu.builder()
                .menuDate(LocalDate.now())
                .mealType(MealType.LUNCH)
                .specialNotes("Special vegetarian thali today")
                .build();
        
        Menu dinnerMenu = Menu.builder()
                .menuDate(LocalDate.now())
                .mealType(MealType.DINNER)
                .specialNotes("Continental cuisine evening")
                .build();
        
        menuRepository.saveAll(Arrays.asList(breakfastMenu, lunchMenu, dinnerMenu));
        
        // Tomorrow's menus
        Menu tomorrowBreakfast = Menu.builder()
                .menuDate(LocalDate.now().plusDays(1))
                .mealType(MealType.BREAKFAST)
                .specialNotes("South Indian breakfast special")
                .build();
        
        Menu tomorrowLunch = Menu.builder()
                .menuDate(LocalDate.now().plusDays(1))
                .mealType(MealType.LUNCH)
                .specialNotes("North Indian curry varieties")
                .build();
        
        menuRepository.saveAll(Arrays.asList(tomorrowBreakfast, tomorrowLunch));
        
        log.info("Sample menus created for today and tomorrow");
    }
    
    private void initializeFeedback() {
        List<Student> students = studentRepository.findAll();
        List<Menu> menus = menuRepository.findAll();
        
        if (!students.isEmpty() && !menus.isEmpty()) {
            List<Feedback> feedbacks = Arrays.asList(
                Feedback.builder()
                        .student(students.get(0))
                        .menu(menus.get(0))
                        .rating(4)
                        .comments("Great breakfast! Fresh bread was amazing.")
                        .feedbackType(FeedbackType.TASTE)
                        .build(),
                
                Feedback.builder()
                        .student(students.get(1))
                        .menu(menus.get(1))
                        .rating(5)
                        .comments("Excellent lunch today. Perfect vegetarian thali!")
                        .feedbackType(FeedbackType.GENERAL)
                        .build(),
                
                Feedback.builder()
                        .student(students.get(2))
                        .menu(menus.get(2))
                        .rating(3)
                        .comments("Dinner was okay, but could be warmer.")
                        .feedbackType(FeedbackType.SERVICE)
                        .build()
            );
            
            feedbackRepository.saveAll(feedbacks);
            log.info("Sample feedback created: {}", feedbacks.size());
        }
    }
    
    private void initializeComplaints() {
        List<Student> students = studentRepository.findAll();
        
        if (!students.isEmpty()) {
            List<Complaint> complaints = Arrays.asList(
                Complaint.builder()
                        .student(students.get(0))
                        .title("Food served cold")
                        .description("The dinner served yesterday evening was cold. Request to ensure food is served hot.")
                        .category(ComplaintCategory.FOOD_QUALITY)
                        .priority(Priority.MEDIUM)
                        .build(),
                
                Complaint.builder()
                        .student(students.get(1))
                        .title("Cleanliness issue in dining hall")
                        .description("Tables were not properly cleaned during lunch time. This affects hygiene standards.")
                        .category(ComplaintCategory.HYGIENE)
                        .priority(Priority.HIGH)
                        .build()
            );
            
            complaintRepository.saveAll(complaints);
            log.info("Sample complaints created: {}", complaints.size());
        }
    }
}
