package com.miu.lms.controller;

import com.miu.lms.constants.ApiController;
import com.miu.lms.dto.course.CourseRequest;
import com.miu.lms.entity.Course;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(ApiController.COURSE_ENDPOINT)
public class CourseController {
    private final CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @PostMapping("/register")
    public ResponseEntity<Course> registerCourse(@RequestBody CourseRequest courseRequest) {
        Course registeredCourse = courseService.registerCourse(courseRequest);
        return new ResponseEntity<>(registeredCourse, HttpStatus.CREATED);
    }
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseRequest> getCourse(@PathVariable Long courseId) throws CourseNotFound {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }
    @GetMapping
    public ResponseEntity<List<CourseRequest>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseRequest> updateCourse(@PathVariable Long courseId, @RequestBody CourseRequest courseRequest) throws CourseNotFound {
        return new ResponseEntity<>(courseService.updateCourse(courseId, courseRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) throws CourseNotFound {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}