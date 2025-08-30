package com.hostel.mess.controller;

import com.hostel.mess.dto.ComplaintDto;
import com.hostel.mess.model.ComplaintStatus;
import com.hostel.mess.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for Complaint operations
 * Demonstrates complaint tracking and resolution workflow
 */
@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class ComplaintController {
    
    private final ComplaintService complaintService;
    
    @PostMapping
    public ResponseEntity<ComplaintDto> submitComplaint(@Valid @RequestBody ComplaintDto complaintDto) {
        log.info("Submitting complaint from student ID: {}", complaintDto.getStudentId());
        
        try {
            ComplaintDto submittedComplaint = complaintService.submitComplaint(complaintDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(submittedComplaint);
        } catch (IllegalArgumentException e) {
            log.error("Error submitting complaint: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDto> getComplaintById(@PathVariable Long id) {
        log.info("Fetching complaint with ID: {}", id);
        
        try {
            ComplaintDto complaint = complaintService.getComplaintById(id);
            return ResponseEntity.ok(complaint);
        } catch (IllegalArgumentException e) {
            log.error("Error fetching complaint: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ComplaintDto>> getComplaintsByStudent(@PathVariable Long studentId) {
        log.info("Fetching complaints by student ID: {}", studentId);
        
        try {
            List<ComplaintDto> complaints = complaintService.getComplaintsByStudent(studentId);
            return ResponseEntity.ok(complaints);
        } catch (IllegalArgumentException e) {
            log.error("Error fetching complaints: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/open")
    public ResponseEntity<List<ComplaintDto>> getOpenComplaints() {
        log.info("Fetching open complaints");
        
        List<ComplaintDto> complaints = complaintService.getOpenComplaints();
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping
    public ResponseEntity<List<ComplaintDto>> getAllComplaints() {
        log.info("Fetching all complaints");
        
        List<ComplaintDto> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ComplaintDto> updateComplaintStatus(
            @PathVariable Long id,
            @RequestParam ComplaintStatus status,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) String resolvedBy) {
        log.info("Updating complaint ID: {} to status: {}", id, status);
        
        try {
            ComplaintDto updatedComplaint = complaintService.updateComplaintStatus(id, status, notes, resolvedBy);
            return ResponseEntity.ok(updatedComplaint);
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Error updating complaint status: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/stats/pending-count")
    public ResponseEntity<Long> getPendingComplaintsCount() {
        log.info("Fetching pending complaints count");
        
        Long count = complaintService.getPendingComplaintsCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/urgent-count")
    public ResponseEntity<Long> getUrgentComplaintsCount() {
        log.info("Fetching urgent complaints count");
        
        Long count = complaintService.getUrgentComplaintsCount();
        return ResponseEntity.ok(count);
    }
}
