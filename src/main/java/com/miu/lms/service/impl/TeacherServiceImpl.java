package com.miu.lms.service.impl;

import com.miu.lms.dto.teacher.NewTeacherRequest;
import com.miu.lms.dto.teacher.TeacherDto;
import com.miu.lms.entity.Teacher;
import com.miu.lms.exceptions.TeacherNotFound;
import com.miu.lms.repo.TeacherRepo;
import com.miu.lms.service.TeacherService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepo teacherRepo;


    public TeacherServiceImpl(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    @Override
    public TeacherDto registerTeacher(NewTeacherRequest teacherDTO) {
        Teacher teacher = teacherRepo.save(new  Teacher(teacherDTO.firstName(),teacherDTO.lastName(),teacherDTO.phone(),new Date()));
        return new TeacherDto(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getPhone());
    }
    @Override
    public TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDTO) throws TeacherNotFound {

        Teacher existingTeacher = teacherRepo.findById(teacherId)
                .orElseThrow(()->new TeacherNotFound(String.format("ERROR: Course with id %d not found.", teacherId)));

        existingTeacher.setFirstName(teacherDTO.firstName());
        existingTeacher.setLastName(teacherDTO.lastName());
        existingTeacher.setPhone(teacherDTO.phone());
        teacherRepo.save(existingTeacher);

        return new TeacherDto(existingTeacher.getId(), existingTeacher.getFirstName(), existingTeacher.getLastName(), existingTeacher.getPhone()) ;
    }
    @Override
    public void deleteTeacher(Long teacherId) throws TeacherNotFound {
         Teacher existingTeacher = teacherRepo.findById(teacherId)
                 .orElseThrow(()->new TeacherNotFound(String.format("ERROR: Teacher with id %d not found.", teacherId)));
          teacherRepo.delete(existingTeacher);
     }
    @Override
    public TeacherDto getTeacherById(Long teacherId) throws TeacherNotFound {
        return teacherRepo.findById(teacherId)
                .map(t-> new TeacherDto(t.getId(), t.getFirstName(), t.getLastName(), t.getPhone()))
                .orElseThrow(()->new TeacherNotFound(String.format("ERROR: Teacher with id %d not found.", teacherId)));
    }
    @Override
    public List<TeacherDto> getAllTeachers() {
        return teacherRepo.findAll(Sort.by("firstName"))
                .stream()
                .map(t->new TeacherDto(t.getId(), t.getFirstName(), t.getFirstName(), t.getPhone()))
                .toList();
    }



}
