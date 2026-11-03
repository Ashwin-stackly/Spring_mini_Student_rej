package com.example.registration.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.registration.model.Student;
import com.example.registration.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Register student
    @PostMapping
    public ResponseEntity<?> registerStudent(@Valid @RequestBody Student student) {
        return studentService.registerStudent(student)
                .<ResponseEntity<?>>map(s -> new ResponseEntity<>(s, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>("Student ID already registered", HttpStatus.CONFLICT));
    }

    // View all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Find by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND));
    }
    
   // Update registration
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id,
                                           @Valid @RequestBody Student updated) {
        return studentService.updateStudent(id, updated)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND));
    }

    // Delete registration
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        if (studentService.deleteStudent(id)) {
            return ResponseEntity.ok("Registration deleted");
        }
        return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
    }
}