package com.hostel.mess.service;

import com.hostel.mess.dto.FeedbackDto;
import com.hostel.mess.model.Feedback;
import com.hostel.mess.model.Student;
import com.hostel.mess.model.Menu;
import com.hostel.mess.repository.FeedbackRepository;
import com.hostel.mess.repository.StudentRepository;
import com.hostel.mess.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Feedback operations
 * Demonstrates validation and business rules
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;
    private final StudentRepository studentRepository;
    private final MenuRepository menuRepository;
    
    public FeedbackDto submitFeedback(FeedbackDto feedbackDto) {
        log.info("Submitting feedback from student ID: {} for menu ID: {}", 
                feedbackDto.getStudentId(), feedbackDto.getMenuId());
        
        // Validate student
        Student student = studentRepository.findById(feedbackDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        if (!student.canSubmitFeedback()) {
            throw new IllegalArgumentException("Student is not active and cannot submit feedback");
        }
        
        // Validate menu
        Menu menu = menuRepository.findById(feedbackDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
        
        // Check for duplicate feedback
        List<Feedback> existingFeedback = feedbackRepository.findByStudentAndMenu(student, menu);
        if (!existingFeedback.isEmpty()) {
            throw new IllegalArgumentException("Student has already provided feedback for this menu");
        }
        
        Feedback feedback = convertToEntity(feedbackDto, student, menu);
        feedback.validateRating(); // Business validation
        
        Feedback savedFeedback = feedbackRepository.save(feedback);
        
        log.info("Feedback submitted successfully with ID: {}", savedFeedback.getId());
        return convertToDto(savedFeedback);
    }
    
    @Transactional(readOnly = true)
    public List<FeedbackDto> getFeedbackForMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
        
        return feedbackRepository.findByMenuOrderByCreatedAtDesc(menu)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<FeedbackDto> getFeedbackByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        return feedbackRepository.findByStudent(student)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Double getAverageRatingForMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
        
        return feedbackRepository.getAverageRatingForMenu(menu);
    }
    
    @Transactional(readOnly = true)
    public List<FeedbackDto> getNegativeFeedback() {
        return feedbackRepository.findNegativeFeedback()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<FeedbackDto> getPositiveFeedback() {
        return feedbackRepository.findPositiveFeedback()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Helper methods
    private FeedbackDto convertToDto(Feedback feedback) {
        return FeedbackDto.builder()
                .id(feedback.getId())
                .studentId(feedback.getStudent().getId())
                .menuId(feedback.getMenu().getId())
                .rating(feedback.getRating())
                .comments(feedback.getComments())
                .feedbackType(feedback.getFeedbackType())
                .studentName(feedback.getStudent().getName())
                .menuDescription(String.format("%s - %s", 
                        feedback.getMenu().getMenuDate(), 
                        feedback.getMenu().getMealType()))
                .submittedAt(feedback.getCreatedAt())
                .build();
    }
    
    private Feedback convertToEntity(FeedbackDto dto, Student student, Menu menu) {
        return Feedback.builder()
                .student(student)
                .menu(menu)
                .rating(dto.getRating())
                .comments(dto.getComments())
                .feedbackType(dto.getFeedbackType())
                .build();
    }
}
