package com.example.registration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.registration.model.Student;

@Service
public class StudentService {

    // Temporary storage using List
    private final List<Student> studentList = new ArrayList<>();

    // returns empty if the ID is already registered
    public Optional<Student> registerStudent(Student student) {
        if (getStudentById(student.getStudentId()).isPresent()) {
            return Optional.empty();
        }
        studentList.add(student);
        return Optional.of(student);
    }

    public List<Student> getAllStudents() {
        return studentList;
    }

    public Optional<Student> getStudentById(int id) {
        return studentList.stream()
                .filter(s -> s.getStudentId() == id)
                .findFirst();
    }

    public boolean deleteStudent(int id) {
        return studentList.removeIf(s -> s.getStudentId() == id);
    }
    
    public Optional<Student> updateStudent(int id, Student updated) {
        Optional<Student> existing = getStudentById(id);
        existing.ifPresent(s -> {
            s.setStudentName(updated.getStudentName());
            s.setCourseName(updated.getCourseName());
            s.setEmail(updated.getEmail());
            s.setAge(updated.getAge());
        });
        return existing;
    }
}