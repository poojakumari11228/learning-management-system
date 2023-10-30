package com.miu.lms.service;

import com.miu.lms.dto.course.CourseDto;
import com.miu.lms.dto.course.NewCourseRequest;
import com.miu.lms.entity.Course;
import com.miu.lms.exceptions.CourseNotFound;

import java.util.List;

public interface CourseService {

    CourseDto registerCourse(NewCourseRequest courseRequest);

    CourseDto updateCourse(Long courseId, CourseDto courseRequest) throws CourseNotFound;

    void deleteCourse(Long courseId) throws CourseNotFound;

    CourseDto getCourseById(Long courseId) throws CourseNotFound;

    List<CourseDto> getAllCourses();
}