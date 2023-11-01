package com.miu.lms.service.impl;

import com.miu.lms.constants.RoleType;
import com.miu.lms.dto.course.CourseDto;
import com.miu.lms.dto.teacher.NewTeacherRequest;
import com.miu.lms.dto.teacher.TeacherDto;
import com.miu.lms.entity.Course;
import com.miu.lms.entity.Teacher;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.exceptions.TeacherNotFound;
import com.miu.lms.exceptions.UserAlreadyExists;
import com.miu.lms.mapper.CourseMapper;
import com.miu.lms.mapper.TeacherMapper;
import com.miu.lms.repo.CourseRepo;
import com.miu.lms.repo.TeacherRepo;
import com.miu.lms.service.TeacherService;
import com.miu.lms.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepo teacherRepo;
    private final CourseRepo courseRepo;
    private final UserService userService;


    public TeacherServiceImpl(TeacherRepo teacherRepo, CourseRepo courseRepo, UserService userService) {
        this.teacherRepo = teacherRepo;
        this.courseRepo = courseRepo;
        this.userService = userService;
    }

    @Override
    public TeacherDto registerTeacher(NewTeacherRequest teacherDTO) throws UserAlreadyExists {

        Long userId = userService.createUser(teacherDTO.email(), teacherDTO.password(), RoleType.TEACHER);
        Teacher teacher = teacherRepo.save(new  Teacher(teacherDTO.firstName(),teacherDTO.lastName(),teacherDTO.phone(),new Date(), userId));
        System.out.println(teacher);
        return TeacherMapper.teacherToDTO(teacher);
    }
    @Override
    public TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDTO) throws TeacherNotFound {

        Teacher existingTeacher = teacherRepo.findById(teacherId)
                .orElseThrow(()->new TeacherNotFound(String.format("ERROR: Teacher with id %d not found.", teacherId)));

        existingTeacher.setFirstName(teacherDTO.firstName());
        existingTeacher.setLastName(teacherDTO.lastName());
        existingTeacher.setPhone(teacherDTO.phone());
        teacherRepo.save(existingTeacher);

        return TeacherMapper.teacherToDTO(existingTeacher);
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
                .map(TeacherMapper :: teacherToDTO)
                .orElseThrow(()->new TeacherNotFound(String.format("ERROR: Teacher with id %d not found.", teacherId)));
    }
    @Override
    public List<TeacherDto> getAllTeachers() {
        return teacherRepo.findAll(Sort.by("firstName"))
                .stream()
                .map(TeacherMapper::teacherToDTO)
                .toList();
    }

    @Override
    public TeacherDto teachNewCourse(Long teacherId, Long courseId) throws CourseNotFound, TeacherNotFound {

        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(()->new TeacherNotFound(String.format("ERROR: Teacher with id %d not found.", teacherId)));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(String.format("ERROR: Course with id %d not found.", courseId)));
        teacher.teachCourse(course);
        teacherRepo.save(teacher);

        return TeacherMapper.teacherToDTO(teacher);
    }

    @Override
    public List<CourseDto> getCoursesTaughtByTeacher(Long teacherId) throws TeacherNotFound {

        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFound("Teacher not found with ID: " + teacherId));

        return CourseMapper.courseListToDTOs(teacher.getCourses());

    }
    @Override
    public TeacherDto withdrawCourse(Long teacherId, Long courseId) throws TeacherNotFound, CourseNotFound {

        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(()->new TeacherNotFound(String.format("ERROR: Teacher with id %d not found.", teacherId)));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new CourseNotFound(String.format("ERROR: Course with id %d not found.", courseId)));
        teacher.withDrawCourse(course);
        teacherRepo.save(teacher);

        return TeacherMapper.teacherToDTO(teacher);
    }

}
