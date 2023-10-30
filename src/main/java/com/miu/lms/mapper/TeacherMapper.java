package com.miu.lms.mapper;

import com.miu.lms.dto.teacher.TeacherDto;
import com.miu.lms.entity.Teacher;

public class TeacherMapper {

    public static TeacherDto teacherToDTO(Teacher teacher) {
        return new TeacherDto(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getPhone(), CourseMapper.courseListToDTOs(teacher.getCourses()), teacher.getCreationDate());
    }

}
