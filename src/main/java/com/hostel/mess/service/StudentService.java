package com.hostel.mess.service;

import com.hostel.mess.dto.StudentDto;
import com.hostel.mess.model.Student;
import com.hostel.mess.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Student operations
 * Demonstrates business logic layer and transaction management
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentService {
    
    private final StudentRepository studentRepository;
    
    public StudentDto createStudent(StudentDto studentDto) {
        log.info("Creating new student with ID: {}", studentDto.getStudentId());
        
        // Business validation
        if (studentRepository.existsByStudentId(studentDto.getStudentId())) {
            throw new IllegalArgumentException("Student ID already exists: " + studentDto.getStudentId());
        }
        
        if (studentRepository.existsByEmail(studentDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + studentDto.getEmail());
        }
        
        Student student = convertToEntity(studentDto);
        Student savedStudent = studentRepository.save(student);
        
        log.info("Student created successfully with ID: {}", savedStudent.getId());
        return convertToDto(savedStudent);
    }
    
    @Transactional(readOnly = true)
    public Optional<StudentDto> getStudentById(Long id) {
        return studentRepository.findById(id).map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Optional<StudentDto> getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId).map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public List<StudentDto> getAllActiveStudents() {
        return studentRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        log.info("Updating student with ID: {}", id);
        
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));
        
        // Update fields
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setRoomNumber(studentDto.getRoomNumber());
        student.setPhoneNumber(studentDto.getPhoneNumber());
        
        Student updatedStudent = studentRepository.save(student);
        
        log.info("Student updated successfully with ID: {}", updatedStudent.getId());
        return convertToDto(updatedStudent);
    }
    
    public void deactivateStudent(Long id) {
        log.info("Deactivating student with ID: {}", id);
        
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));
        
        student.deactivate();
        studentRepository.save(student);
        
        log.info("Student deactivated successfully with ID: {}", id);
    }
    
    // Helper methods demonstrating encapsulation
    private StudentDto convertToDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .studentId(student.getStudentId())
                .name(student.getName())
                .email(student.getEmail())
                .roomNumber(student.getRoomNumber())
                .phoneNumber(student.getPhoneNumber())
                .isActive(student.getIsActive())
                .build();
    }
    
    private Student convertToEntity(StudentDto dto) {
        return Student.builder()
                .studentId(dto.getStudentId())
                .name(dto.getName())
                .email(dto.getEmail())
                .roomNumber(dto.getRoomNumber())
                .phoneNumber(dto.getPhoneNumber())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();
    }
}
