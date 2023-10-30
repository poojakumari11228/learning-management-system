package com.miu.lms.service.impl;

import com.miu.lms.dto.course.CourseRequest;
import com.miu.lms.entity.Course;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.repo.CourseRepo;
import com.miu.lms.service.CourseService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Course registerCourse(CourseRequest courseRequest) {
        Course course = new Course(courseRequest.name(), courseRequest.code(), courseRequest.desc(),new Date());
        return courseRepo.save(course);
    }

    @Override
    public CourseRequest updateCourse(Long courseId, CourseRequest courseRequest) throws CourseNotFound {
        Course existingCourse = courseRepo.findById(courseId)
                .orElseThrow(()->new CourseNotFound(String.format("ERROR: Course with id %d not found.", courseId)));

        existingCourse.setName(courseRequest.name());
        existingCourse.setCode(courseRequest.code());
        existingCourse.setDescription(courseRequest.desc());
        courseRepo.save(existingCourse);

        return new CourseRequest(existingCourse.getName(), existingCourse.getCode(), existingCourse.getDescription()) ;
    }


    @Override
    public void deleteCourse(Long courseId) throws CourseNotFound {
        Course existingCourse = courseRepo.findById(courseId).orElseThrow(()->new CourseNotFound(String.format("ERROR: Course with id %d not found.", courseId)));
        courseRepo.delete(existingCourse);
    }

    @Override
    public CourseRequest getCourseById(Long courseId) throws CourseNotFound {
        return courseRepo.findById(courseId)
                .map(c->new CourseRequest(c.getName(), c.getCode(), c.getDescription()))
                .orElseThrow(()->new CourseNotFound(String.format("ERROR: Course with id %d not found.", courseId)));

    }

    @Override
    public List<CourseRequest> getAllCourses() {
        return courseRepo.findAll(Sort.by("name"))
                .stream()
                .map(c->new CourseRequest(c.getName(), c.getCode(), c.getDescription()))
                .toList();
    }
















}