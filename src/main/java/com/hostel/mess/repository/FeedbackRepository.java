package com.hostel.mess.repository;

import com.hostel.mess.model.Feedback;
import com.hostel.mess.model.Student;
import com.hostel.mess.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Feedback entity
 * Demonstrates aggregation queries and statistical operations
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    List<Feedback> findByStudent(Student student);
    
    List<Feedback> findByMenu(Menu menu);
    
    List<Feedback> findByStudentAndMenu(Student student, Menu menu);
    
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.menu = :menu")
    Double getAverageRatingForMenu(@Param("menu") Menu menu);
    
    @Query("SELECT f FROM Feedback f WHERE f.menu = :menu ORDER BY f.createdAt DESC")
    List<Feedback> findByMenuOrderByCreatedAtDesc(@Param("menu") Menu menu);
    
    @Query("SELECT f FROM Feedback f WHERE f.rating <= 2 ORDER BY f.createdAt DESC")
    List<Feedback> findNegativeFeedback();
    
    @Query("SELECT f FROM Feedback f WHERE f.rating >= 4 ORDER BY f.createdAt DESC")
    List<Feedback> findPositiveFeedback();
    
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.menu = :menu")
    Long countFeedbackForMenu(@Param("menu") Menu menu);
}
