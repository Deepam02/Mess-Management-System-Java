package com.hostel.mess.repository;

import com.hostel.mess.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student entity
 * Demonstrates Data Access Layer and Repository pattern
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByStudentId(String studentId);
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByIsActiveTrue();
    
    List<Student> findByRoomNumber(String roomNumber);
    
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:name%")
    List<Student> findByNameContaining(@Param("name") String name);
    
    boolean existsByStudentId(String studentId);
    
    boolean existsByEmail(String email);
}
