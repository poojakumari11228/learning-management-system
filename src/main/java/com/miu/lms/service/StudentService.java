package com.miu.lms.service;

import com.miu.lms.dto.student.StudentRequest;
import com.miu.lms.entity.Student;

public interface StudentService {
        Student registerStudent(StudentRequest studentDTO);
}
