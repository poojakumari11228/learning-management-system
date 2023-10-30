package com.miu.lms.dto.teacher;

import com.miu.lms.dto.course.CourseDto;
import com.miu.lms.entity.Course;
import lombok.Data;

import java.util.Date;
import java.util.List;

public record TeacherDto(Long id, String firstName, String lastName, String phone, List<CourseDto> courses,
                         Date creationDate) {

}

