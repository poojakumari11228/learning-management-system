package com.miu.lms.service;

import com.miu.lms.dto.student.NewStudentRequest;
import com.miu.lms.dto.student.StudentDto;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.exceptions.CoursePrerequisitesNotMeet;
import com.miu.lms.exceptions.StudentNotFound;
import com.miu.lms.exceptions.UserAlreadyExists;

import java.util.List;

public interface StudentService {
        StudentDto registerStudent(NewStudentRequest studentDTO) throws UserAlreadyExists;
        StudentDto getStudentById(Long studentId) throws StudentNotFound;

        List<StudentDto> getAllStudents();

        StudentDto updateStudent(Long studentId, StudentDto studentRequest) throws StudentNotFound;

        void deleteStudent(Long studentId) throws StudentNotFound;

        StudentDto enrollInCourse(Long studentId, Long courseId) throws CourseNotFound, StudentNotFound, CoursePrerequisitesNotMeet;

        StudentDto withdrawCourse(Long courseId, Long studentId) throws StudentNotFound, CourseNotFound;
}
