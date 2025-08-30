package com.hostel.mess.controller;

import com.hostel.mess.dto.StudentDto;
import com.hostel.mess.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for Student operations
 * Demonstrates REST API design and HTTP response handling
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class StudentController {
    
    private final StudentService studentService;
    
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        log.info("Creating student with ID: {}", studentDto.getStudentId());
        
        try {
            StudentDto createdStudent = studentService.createStudent(studentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (IllegalArgumentException e) {
            log.error("Error creating student: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        log.info("Fetching student with ID: {}", id);
        
        return studentService.getStudentById(id)
                .map(student -> ResponseEntity.ok().body(student))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/by-student-id/{studentId}")
    public ResponseEntity<StudentDto> getStudentByStudentId(@PathVariable String studentId) {
        log.info("Fetching student with student ID: {}", studentId);
        
        return studentService.getStudentByStudentId(studentId)
                .map(student -> ResponseEntity.ok().body(student))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllActiveStudents() {
        log.info("Fetching all active students");
        
        List<StudentDto> students = studentService.getAllActiveStudents();
        return ResponseEntity.ok(students);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(
            @PathVariable Long id, 
            @Valid @RequestBody StudentDto studentDto) {
        log.info("Updating student with ID: {}", id);
        
        try {
            StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalArgumentException e) {
            log.error("Error updating student: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateStudent(@PathVariable Long id) {
        log.info("Deactivating student with ID: {}", id);
        
        try {
            studentService.deactivateStudent(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error deactivating student: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
