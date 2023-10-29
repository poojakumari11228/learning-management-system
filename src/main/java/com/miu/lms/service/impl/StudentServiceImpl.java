package com.miu.lms.service.impl;

import com.miu.lms.dto.student.StudentRequest;
import com.miu.lms.entity.Student;
import com.miu.lms.repo.StudentRepo;
import com.miu.lms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student registerStudent(StudentRequest studentDTO) {
        Student student = new Student(studentDTO.firstName(), studentDTO.lastName(), studentDTO.phone());
        return studentRepository.save(student);
    }
}
