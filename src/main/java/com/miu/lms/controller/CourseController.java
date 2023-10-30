package com.miu.lms.controller;

import com.miu.lms.constants.ApiController;
import com.miu.lms.dto.course.CourseDto;
import com.miu.lms.dto.course.NewCourseRequest;
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
    public ResponseEntity<CourseDto> registerCourse(@RequestBody NewCourseRequest courseRequest) {
        CourseDto registeredCourse = courseService.registerCourse(courseRequest);
        return new ResponseEntity<>(registeredCourse, HttpStatus.CREATED);
    }
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long courseId) throws CourseNotFound {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }
    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long courseId, @RequestBody CourseDto courseRequest) throws CourseNotFound {
        return new ResponseEntity<>(courseService.updateCourse(courseId, courseRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) throws CourseNotFound {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}