package com.miu.lms.controller;

import com.miu.lms.constants.ApiController;
import com.miu.lms.dto.student.NewStudentRequest;
import com.miu.lms.dto.student.StudentDto;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.exceptions.CoursePrerequisitesNotMeet;
import com.miu.lms.exceptions.StudentNotFound;
import com.miu.lms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(ApiController.STUDENT_ENDPOINT)
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/register")
    public ResponseEntity<StudentDto> registerStudent(@RequestBody NewStudentRequest studentDTO) {
        return new ResponseEntity<>(studentService.registerStudent(studentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long studentId) throws StudentNotFound {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }
    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long studentId, @RequestBody StudentDto studentRequest) throws StudentNotFound {
        return new ResponseEntity<>(studentService.updateStudent(studentId, studentRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) throws StudentNotFound {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{studentId}/course/enroll/{courseId}")
    public ResponseEntity<StudentDto> enrollStudentInCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) throws CourseNotFound, StudentNotFound, CoursePrerequisitesNotMeet {
        return ResponseEntity.ok(studentService.enrollInCourse(studentId, courseId));
    }

    @PostMapping("/{studentId}/course/withdraw/{courseId}")
    public ResponseEntity<StudentDto> withdrawStudentInCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) throws CourseNotFound, StudentNotFound {
        return ResponseEntity.ok(studentService.withdrawCourse(courseId, studentId));
    }

}