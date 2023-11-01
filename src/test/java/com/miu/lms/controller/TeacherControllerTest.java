package com.miu.lms.controller;

import com.miu.lms.dto.course.CourseDto;
import com.miu.lms.dto.teacher.NewTeacherRequest;
import com.miu.lms.dto.teacher.TeacherDto;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.exceptions.TeacherNotFound;
import com.miu.lms.exceptions.UserAlreadyExists;
import com.miu.lms.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeacherControllerTest {

    @InjectMocks
    TeacherController teacherController;

    @Mock
    TeacherService teacherService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTeacherById() throws TeacherNotFound {
        TeacherDto teacherDto = new TeacherDto(1L,"Jane", "Smith", "789123", List.of(), new Date());

        when(teacherService.getTeacherById(1L)).thenReturn(teacherDto);
        var responseEntity = teacherController.getTeacherById(1L);

        assertEquals(teacherDto, responseEntity.getBody());
    }
    @Test
    void getAllTeachers() {
        List<TeacherDto> teacherList = List.of(
                new TeacherDto(1L, "Jane", "Smith", "789123", List.of(), new Date()),
                new TeacherDto(2L, "John", "Doe", "456789", List.of(), new Date())
        );

        when(teacherService.getAllTeachers()).thenReturn(teacherList);
        var responseEntity = teacherController.getAllTeachers();
        assertEquals(teacherList, responseEntity.getBody());
    }

    @Test
    void updateTeacher() throws TeacherNotFound {
        Long teacherId = 1L;
        TeacherDto teacherDto = new TeacherDto(1L, "Jane", "Smith", "789123", List.of(), new Date());

        when(teacherService.updateTeacher(teacherId, teacherDto)).thenReturn(teacherDto);
        var responseEntity = teacherController.updateTeacher(teacherId, teacherDto);

        assertEquals(teacherDto, responseEntity.getBody());
    }

    @Test
    void deleteTeacher() throws TeacherNotFound {
        Long teacherId = 1L;
        ResponseEntity<Void> responseEntity = teacherController.deleteTeacher(teacherId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(teacherService).deleteTeacher(teacherId);
    }

    @Test
    void registerTeacher() throws UserAlreadyExists {
        NewTeacherRequest teacherDTO = new NewTeacherRequest("Jane", "Smith", "789123", "jane@example.com", "test1234");
        TeacherDto teacherDto = new TeacherDto(1L, "Jane", "Smith", "789123", List.of(), new Date());

        when(teacherService.registerTeacher(teacherDTO)).thenReturn(teacherDto);
        var responseEntity = teacherController.registerTeacher(teacherDTO);

        assertEquals(teacherDto, responseEntity.getBody());
    }

    @Test
    void teachCourse() throws TeacherNotFound, CourseNotFound {
        Long teacherId = 1L;
        Long courseId = 2L;
        TeacherDto teacherDto = new TeacherDto(1L, "Jane", "Smith", "789123", List.of(), new Date());
        when(teacherService.teachNewCourse(teacherId, courseId)).thenReturn(teacherDto);
        var responseEntity = teacherController.teachCourse(teacherId, courseId);

        assertEquals(teacherDto, responseEntity.getBody());
    }

    @Test
    void withdrawCourse() throws TeacherNotFound, CourseNotFound {
        Long teacherId = 1L;
        Long courseId = 2L;
        TeacherDto teacherDto = new TeacherDto(1L, "Jane", "Smith", "789123", List.of(), new Date());

        when(teacherService.withdrawCourse(teacherId, courseId)).thenReturn(teacherDto);
        var responseEntity = teacherController.withdrawCourse(teacherId, courseId);

        assertEquals(teacherDto, responseEntity.getBody());
    }

    @Test
    void getCoursesTaughtByTeacher() throws TeacherNotFound {
        Long teacherId = 1L;
        List<CourseDto> courses = List.of(new CourseDto(1L, "Math", "MATH101", "Math Course", new Date(), null));

        when(teacherService.getCoursesTaughtByTeacher(teacherId)).thenReturn(courses);
        var responseEntity = teacherController.getCoursesTaughtByTeacher(teacherId);

        assertEquals(courses, responseEntity.getBody());
    }

}