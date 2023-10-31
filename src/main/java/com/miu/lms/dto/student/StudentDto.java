package com.miu.lms.dto.student;

import com.miu.lms.dto.course.CourseDto;

import java.util.Date;
import java.util.List;

public record StudentDto(Long id, String firstName, String lastName, String phone, Date creationDate, List<CourseDto> enrolledCourses) {
}