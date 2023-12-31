package com.miu.lms.mapper;

import com.miu.lms.dto.course.CourseDto;
import com.miu.lms.entity.Course;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CourseMapper {

    public static List<CourseDto> courseListToDTOs(List<Course> courses) {
        return courses==null? new ArrayList<>() : courses.stream()
                .map(CourseMapper::courseToDTO)
                .collect(Collectors.toList());
    }

    public static CourseDto courseToDTO(Course course){
        return new CourseDto(course.getId(), course.getName(), course.getCode(), course.getDescription(), course.getCreationDate()
                , course.getPrerequisites().size()>0 ? course.getPrerequisites().stream().map(CourseMapper::courseToDTO )
                .collect(Collectors.toSet()) : new HashSet<>());
    }

}
