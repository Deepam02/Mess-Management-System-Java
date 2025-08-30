package com.hostel.mess.controller;

import com.hostel.mess.dto.FeedbackDto;
import com.hostel.mess.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for Feedback operations
 * Demonstrates feedback collection and rating system
 */
@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class FeedbackController {
    
    private final FeedbackService feedbackService;
    
    @PostMapping
    public ResponseEntity<FeedbackDto> submitFeedback(@Valid @RequestBody FeedbackDto feedbackDto) {
        log.info("Submitting feedback from student ID: {} for menu ID: {}", 
                feedbackDto.getStudentId(), feedbackDto.getMenuId());
        
        try {
            FeedbackDto submittedFeedback = feedbackService.submitFeedback(feedbackDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(submittedFeedback);
        } catch (IllegalArgumentException e) {
            log.error("Error submitting feedback: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<FeedbackDto>> getFeedbackForMenu(@PathVariable Long menuId) {
        log.info("Fetching feedback for menu ID: {}", menuId);
        
        try {
            List<FeedbackDto> feedbacks = feedbackService.getFeedbackForMenu(menuId);
            return ResponseEntity.ok(feedbacks);
        } catch (IllegalArgumentException e) {
            log.error("Error fetching feedback: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByStudent(@PathVariable Long studentId) {
        log.info("Fetching feedback by student ID: {}", studentId);
        
        try {
            List<FeedbackDto> feedbacks = feedbackService.getFeedbackByStudent(studentId);
            return ResponseEntity.ok(feedbacks);
        } catch (IllegalArgumentException e) {
            log.error("Error fetching feedback: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/menu/{menuId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForMenu(@PathVariable Long menuId) {
        log.info("Fetching average rating for menu ID: {}", menuId);
        
        try {
            Double averageRating = feedbackService.getAverageRatingForMenu(menuId);
            return ResponseEntity.ok(averageRating != null ? averageRating : 0.0);
        } catch (IllegalArgumentException e) {
            log.error("Error fetching average rating: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/negative")
    public ResponseEntity<List<FeedbackDto>> getNegativeFeedback() {
        log.info("Fetching negative feedback");
        
        List<FeedbackDto> feedbacks = feedbackService.getNegativeFeedback();
        return ResponseEntity.ok(feedbacks);
    }
    
    @GetMapping("/positive")
    public ResponseEntity<List<FeedbackDto>> getPositiveFeedback() {
        log.info("Fetching positive feedback");
        
        List<FeedbackDto> feedbacks = feedbackService.getPositiveFeedback();
        return ResponseEntity.ok(feedbacks);
    }
}
