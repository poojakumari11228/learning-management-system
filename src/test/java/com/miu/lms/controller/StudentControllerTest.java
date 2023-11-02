package com.miu.lms.controller;

import com.miu.lms.dto.student.StudentDto;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.exceptions.CoursePrerequisitesNotMeet;
import com.miu.lms.exceptions.StudentNotFound;
import com.miu.lms.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentControllerTest {

    @InjectMocks
    StudentController studentController;

    @Mock
    StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getStudent() throws StudentNotFound {
        StudentDto studentDto = new StudentDto(1L, "John", "Doe", "123456", new Date(), List.of());
        when(studentService.getStudentById(1L)).thenReturn(studentDto);

        ResponseEntity<StudentDto> responseEntity = studentController.getStudent(1L);

        assertEquals(studentDto, responseEntity.getBody());
    }

    @Test
    void getAllStudents() {
        List<StudentDto> students = List.of(
                new StudentDto(1L, "John", "Doe", "123456", new Date(), List.of()),
                new StudentDto(2L, "Jane", "Smith", "789123", new Date(), List.of())
        );
        when(studentService.getAllStudents()).thenReturn(students);

        ResponseEntity<List<StudentDto> > responseEntity = studentController.getAllStudents();

        assertEquals(students, responseEntity.getBody());
    }

    @Test
    void updateStudent() throws StudentNotFound {
        long studentId = 1L;
        StudentDto studentDto = new StudentDto(1L, "John", "Doe", "123456", new Date(), List.of());
        when(studentService.updateStudent(studentId, studentDto)).thenReturn(studentDto);

        ResponseEntity<StudentDto> responseEntity = studentController.updateStudent(studentId, studentDto);

        assertEquals(studentDto, responseEntity.getBody());
    }

    @Test
    void deleteStudent() throws StudentNotFound {
        long studentId = 1L;

        ResponseEntity<Void> responseEntity = studentController.deleteStudent(studentId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(studentService).deleteStudent(studentId);
    }

    @Test
    void enrollStudentInCourse() throws CourseNotFound, StudentNotFound, CoursePrerequisitesNotMeet {
        long studentId = 1L;
        long courseId = 2L;
        StudentDto studentDto = new StudentDto(1L, "John", "Doe", "123456", new Date(), List.of());
        when(studentService.enrollInCourse(studentId, courseId)).thenReturn(studentDto);

        ResponseEntity<StudentDto> responseEntity = studentController.enrollStudentInCourse(studentId, courseId);

        assertEquals(studentDto, responseEntity.getBody());
    }

    @Test
    void withdrawStudentInCourse() throws CourseNotFound, StudentNotFound {
        long studentId = 1L;
        long courseId = 2L;
        StudentDto studentDto = new StudentDto(1L, "John", "Doe", "123456", new Date(), List.of());
        when(studentService.withdrawCourse(courseId, studentId)).thenReturn(studentDto);

        ResponseEntity<StudentDto> responseEntity = studentController.withdrawStudentInCourse(studentId, courseId);

        assertEquals(studentDto, responseEntity.getBody());
    }



}