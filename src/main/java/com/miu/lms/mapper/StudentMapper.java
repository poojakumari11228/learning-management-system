package com.miu.lms.mapper;

import com.miu.lms.dto.student.StudentDto;
import com.miu.lms.entity.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {


    public static List<StudentDto> studentListToDTOs(List<Student> students) {
        return students==null? new ArrayList<>() : students.stream()
                .map(c->studentToDTO(c))
                .collect(Collectors.toList());
    }

    public static StudentDto studentToDTO(Student student){
        return new StudentDto(student.getId(), student.getFirstName(), student.getLastName(), student.getPhone(), student.getCreationDate()) ;
    }
}
