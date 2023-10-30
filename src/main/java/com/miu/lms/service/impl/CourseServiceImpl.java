package com.miu.lms.service.impl;

import com.miu.lms.dto.course.CourseDto;
import com.miu.lms.dto.course.NewCourseRequest;
import com.miu.lms.entity.Course;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.mapper.CourseMapper;
import com.miu.lms.repo.CourseRepo;
import com.miu.lms.service.CourseService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;

    public CourseServiceImpl(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }


    @Override
    public CourseDto registerCourse(NewCourseRequest courseRequest) {
        Course course = new Course(courseRequest.name(), courseRequest.code(), courseRequest.desc(),new Date());
        courseRepo.save(course);
        return CourseMapper.courseToDTO(course);
    }

    @Override
    public CourseDto updateCourse(Long courseId, CourseDto courseRequest) throws CourseNotFound {
        Course existingCourse = courseRepo.findById(courseId)
                .orElseThrow(()->new CourseNotFound(String.format("ERROR: Course with id %d not found.", courseId)));

        existingCourse.setName(courseRequest.name());
        existingCourse.setCode(courseRequest.code());
        existingCourse.setDescription(courseRequest.desc());
        courseRepo.save(existingCourse);
        return CourseMapper.courseToDTO(existingCourse) ;
    }


    @Override
    public void deleteCourse(Long courseId) throws CourseNotFound {
        Course existingCourse = courseRepo.findById(courseId).orElseThrow(()->new CourseNotFound(String.format("ERROR: Course with id %d not found.", courseId)));
        courseRepo.delete(existingCourse);
    }

    @Override
    public CourseDto getCourseById(Long courseId) throws CourseNotFound {
        return courseRepo.findById(courseId)
                .map(c->CourseMapper.courseToDTO(c))
                .orElseThrow(()->new CourseNotFound(String.format("ERROR: Course with id %d not found.", courseId)));

    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepo.findAll(Sort.by("name"))
                .stream()
                .map(c->CourseMapper.courseToDTO(c))
                .toList();
    }
















}