package com.miu.lms.service;

import com.miu.lms.dto.course.CourseDto;
import com.miu.lms.dto.teacher.NewTeacherRequest;
import com.miu.lms.dto.teacher.TeacherDto;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.exceptions.TeacherNotFound;
import com.miu.lms.exceptions.UserAlreadyExists;

import java.util.List;

public interface TeacherService {
    TeacherDto registerTeacher(NewTeacherRequest teacherDTO) throws UserAlreadyExists;

    TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDTO) throws TeacherNotFound;

    void deleteTeacher(Long teacherId) throws TeacherNotFound;

    TeacherDto getTeacherById(Long teacherId) throws TeacherNotFound;

    List<TeacherDto> getAllTeachers();

    TeacherDto teachNewCourse(Long teacherId, Long courseId) throws CourseNotFound, TeacherNotFound;

    List<CourseDto> getCoursesTaughtByTeacher(Long teacherId) throws TeacherNotFound;

    TeacherDto withdrawCourse(Long teacherId, Long courseId) throws CourseNotFound, TeacherNotFound;
}
