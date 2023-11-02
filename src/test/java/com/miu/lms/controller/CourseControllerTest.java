package com.miu.lms.controller;

import com.miu.lms.dto.course.CourseDto;
import com.miu.lms.dto.course.NewCourseRequest;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseControllerTest {

    @InjectMocks
    CourseController courseController;

    @Mock
    CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCourse() throws CourseNotFound {
        CourseDto courseDto = new CourseDto(1L, "Math", "MATH101", "Math Course", null, null);
        when(courseService.getCourseById(1L)).thenReturn(courseDto);

        ResponseEntity<CourseDto> responseEntity = courseController.getCourse(1L);

        assertEquals(courseDto, responseEntity.getBody());
    }

    @Test
    void getAllCourses() {
        List<CourseDto> courses = List.of(
                new CourseDto(1L, "Math", "MATH101", "Math Course", null, null),
                new CourseDto(2L, "Physics", "PHYS101", "Physics Course", null, null)
        );
        when(courseService.getAllCourses()).thenReturn(courses);

        ResponseEntity<List<CourseDto>> responseEntity = courseController.getAllCourses();

        assertEquals(courses, responseEntity.getBody());
    }

    @Test
    void updateCourse() throws CourseNotFound {
        long courseId = 1L;
        CourseDto courseDto = new CourseDto(1L, "Math", "MATH101", "Math Course", null, null);
        when(courseService.updateCourse(courseId, courseDto)).thenReturn(courseDto);

        ResponseEntity<CourseDto> responseEntity = courseController.updateCourse(courseId, courseDto);

        assertEquals(courseDto, responseEntity.getBody());
    }

    @Test
    void deleteCourse() throws CourseNotFound {
        long courseId = 1L;

        ResponseEntity<Void> responseEntity = courseController.deleteCourse(courseId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(courseService).deleteCourse(courseId);
    }

    @Test
    void registerCourse() throws CourseNotFound {
        NewCourseRequest courseRequest = new NewCourseRequest("Math", "MATH101", "Math Course", null);
        CourseDto registeredCourse = new CourseDto(1L, "Math", "MATH101", "Math Course", null, null);
        when(courseService.registerCourse(courseRequest)).thenReturn(registeredCourse);

        ResponseEntity<CourseDto> responseEntity = courseController.registerCourse(courseRequest);

        assertEquals(registeredCourse, responseEntity.getBody());
    }



}