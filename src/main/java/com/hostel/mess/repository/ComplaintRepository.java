package com.hostel.mess.repository;

import com.hostel.mess.model.Complaint;
import com.hostel.mess.model.ComplaintStatus;
import com.hostel.mess.model.ComplaintCategory;
import com.hostel.mess.model.Priority;
import com.hostel.mess.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Complaint entity
 * Demonstrates complex queries and filtering
 */
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    
    List<Complaint> findByStudent(Student student);
    
    List<Complaint> findByStatus(ComplaintStatus status);
    
    List<Complaint> findByCategory(ComplaintCategory category);
    
    List<Complaint> findByPriority(Priority priority);
    
    @Query("SELECT c FROM Complaint c WHERE c.status IN ('SUBMITTED', 'IN_PROGRESS') ORDER BY c.priority DESC, c.createdAt ASC")
    List<Complaint> findOpenComplaintsOrderByPriorityAndDate();
    
    @Query("SELECT c FROM Complaint c WHERE c.student = :student ORDER BY c.createdAt DESC")
    List<Complaint> findByStudentOrderByCreatedAtDesc(@Param("student") Student student);
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 'SUBMITTED'")
    Long countPendingComplaints();
    
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.priority = 'URGENT' AND c.status IN ('SUBMITTED', 'IN_PROGRESS')")
    Long countUrgentOpenComplaints();
    
    List<Complaint> findByStatusInOrderByPriorityDescCreatedAtAsc(List<ComplaintStatus> statuses);
}
