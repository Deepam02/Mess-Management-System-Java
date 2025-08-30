package com.hostel.mess.service;

import com.hostel.mess.dto.ComplaintDto;
import com.hostel.mess.model.Complaint;
import com.hostel.mess.model.Student;
import com.hostel.mess.model.ComplaintStatus;
import com.hostel.mess.repository.ComplaintRepository;
import com.hostel.mess.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Complaint operations
 * Demonstrates state management and business workflows
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ComplaintService {
    
    private final ComplaintRepository complaintRepository;
    private final StudentRepository studentRepository;
    
    public ComplaintDto submitComplaint(ComplaintDto complaintDto) {
        log.info("Submitting complaint from student ID: {}", complaintDto.getStudentId());
        
        // Validate student
        Student student = studentRepository.findById(complaintDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        if (!student.canSubmitFeedback()) {
            throw new IllegalArgumentException("Student is not active and cannot submit complaints");
        }
        
        Complaint complaint = convertToEntity(complaintDto, student);
        Complaint savedComplaint = complaintRepository.save(complaint);
        
        log.info("Complaint submitted successfully with ID: {}", savedComplaint.getId());
        return convertToDto(savedComplaint);
    }
    
    @Transactional(readOnly = true)
    public ComplaintDto getComplaintById(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        
        return convertToDto(complaint);
    }
    
    @Transactional(readOnly = true)
    public List<ComplaintDto> getComplaintsByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        return complaintRepository.findByStudentOrderByCreatedAtDesc(student)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ComplaintDto> getOpenComplaints() {
        List<ComplaintStatus> openStatuses = Arrays.asList(
                ComplaintStatus.SUBMITTED, ComplaintStatus.IN_PROGRESS);
        
        return complaintRepository.findByStatusInOrderByPriorityDescCreatedAtAsc(openStatuses)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ComplaintDto> getAllComplaints() {
        return complaintRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public ComplaintDto updateComplaintStatus(Long id, ComplaintStatus status, String notes, String resolvedBy) {
        log.info("Updating complaint ID: {} to status: {}", id, status);
        
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        
        // Apply state transitions based on business rules
        switch (status) {
            case IN_PROGRESS:
                complaint.markInProgress();
                break;
            case RESOLVED:
                if (!complaint.canBeResolved()) {
                    throw new IllegalStateException("Complaint cannot be resolved in current state");
                }
                complaint.resolve(notes, resolvedBy);
                break;
            case CLOSED:
                complaint.close();
                break;
            default:
                throw new IllegalArgumentException("Invalid status transition");
        }
        
        Complaint updatedComplaint = complaintRepository.save(complaint);
        
        log.info("Complaint status updated successfully for ID: {}", updatedComplaint.getId());
        return convertToDto(updatedComplaint);
    }
    
    @Transactional(readOnly = true)
    public Long getPendingComplaintsCount() {
        return complaintRepository.countPendingComplaints();
    }
    
    @Transactional(readOnly = true)
    public Long getUrgentComplaintsCount() {
        return complaintRepository.countUrgentOpenComplaints();
    }
    
    // Helper methods
    private ComplaintDto convertToDto(Complaint complaint) {
        return ComplaintDto.builder()
                .id(complaint.getId())
                .studentId(complaint.getStudent().getId())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .category(complaint.getCategory())
                .status(complaint.getStatus())
                .priority(complaint.getPriority())
                .resolutionNotes(complaint.getResolutionNotes())
                .resolvedBy(complaint.getResolvedBy())
                .studentName(complaint.getStudent().getName())
                .submittedAt(complaint.getCreatedAt())
                .lastUpdated(complaint.getUpdatedAt())
                .build();
    }
    
    private Complaint convertToEntity(ComplaintDto dto, Student student) {
        return Complaint.builder()
                .student(student)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .priority(dto.getPriority())
                .build();
    }
}
