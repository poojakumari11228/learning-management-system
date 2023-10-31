package com.miu.lms.mapper;

import com.miu.lms.dto.student.StudentDto;
import com.miu.lms.entity.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {


    public static List<StudentDto> studentListToDTOs(List<Student> students) {
        return students==null? new ArrayList<>() : students.stream()
                .map(StudentMapper::studentToDTO)
                .toList();
    }

    public static StudentDto studentToDTO(Student student){
        return new StudentDto(student.getId(), student.getFirstName(), student.getLastName(), student.getPhone(), student.getCreationDate()
                , student.getCourses().size()>0 ? student.getCourses().stream().map(CourseMapper::courseToDTO )
                .collect(Collectors.toList()) : new ArrayList<>()) ;
    }
}
