package com.miu.lms.controller;

import com.miu.lms.dto.student.StudentRequest;
import com.miu.lms.entity.Student;
import com.miu.lms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody StudentRequest studentDTO) {
        Student registeredStudent = studentService.registerStudent(studentDTO);
        return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
    }
}