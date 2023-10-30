package com.miu.lms.service;

import com.miu.lms.dto.course.CourseRequest;
import com.miu.lms.entity.Course;
import com.miu.lms.exceptions.CourseNotFound;

import java.util.List;

public interface CourseService {

    Course registerCourse(CourseRequest courseRequest);

    CourseRequest updateCourse(Long courseId, CourseRequest courseRequest) throws CourseNotFound;

    void deleteCourse(Long courseId) throws CourseNotFound;

    CourseRequest getCourseById(Long courseId) throws CourseNotFound;

    List<CourseRequest> getAllCourses();
}