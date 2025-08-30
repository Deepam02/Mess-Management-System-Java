package com.hostel.mess.controller;

import com.hostel.mess.dto.MenuDto;
import com.hostel.mess.dto.FeedbackDto;
import com.hostel.mess.service.MenuService;
import com.hostel.mess.service.FeedbackService;
import com.hostel.mess.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Dashboard operations
 * Provides overview and summary information
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class DashboardController {
    
    private final MenuService menuService;
    private final FeedbackService feedbackService;
    private final ComplaintService complaintService;
    
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getDashboardOverview() {
        log.info("Fetching dashboard overview");
        
        Map<String, Object> overview = new HashMap<>();
        
        try {
            // Today's menus
            List<MenuDto> todaysMenus = menuService.getTodaysMenus();
            overview.put("todaysMenus", todaysMenus);
            
            // Recent feedback
            List<FeedbackDto> positiveFeedback = feedbackService.getPositiveFeedback();
            List<FeedbackDto> negativeFeedback = feedbackService.getNegativeFeedback();
            overview.put("positiveFeedbackCount", positiveFeedback.size());
            overview.put("negativeFeedbackCount", negativeFeedback.size());
            
            // Complaint statistics
            Long pendingComplaints = complaintService.getPendingComplaintsCount();
            Long urgentComplaints = complaintService.getUrgentComplaintsCount();
            overview.put("pendingComplaints", pendingComplaints);
            overview.put("urgentComplaints", urgentComplaints);
            
            // System status
            overview.put("systemStatus", "operational");
            overview.put("lastUpdated", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(overview);
        } catch (Exception e) {
            log.error("Error fetching dashboard overview: {}", e.getMessage());
            overview.put("systemStatus", "error");
            overview.put("message", "Unable to fetch complete dashboard data");
            return ResponseEntity.ok(overview);
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> getHealthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Hostel Mess Management System");
        health.put("version", "1.0.0");
        health.put("timestamp", java.time.LocalDateTime.now().toString());
        
        return ResponseEntity.ok(health);
    }
}
