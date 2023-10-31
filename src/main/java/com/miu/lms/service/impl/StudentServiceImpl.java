package com.miu.lms.service.impl;

import com.miu.lms.dto.student.NewStudentRequest;
import com.miu.lms.dto.student.StudentDto;
import com.miu.lms.entity.Course;
import com.miu.lms.entity.Student;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.exceptions.CoursePrerequisitesNotMeet;
import com.miu.lms.exceptions.StudentNotFound;
import com.miu.lms.mapper.StudentMapper;
import com.miu.lms.repo.CourseRepo;
import com.miu.lms.repo.StudentRepo;
import com.miu.lms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepository;
    private final CourseRepo courseRepo;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepository, CourseRepo courseRepo) {
        this.studentRepository = studentRepository;
        this.courseRepo = courseRepo;
    }

    @Override
    public StudentDto registerStudent(NewStudentRequest studentDTO) {
        Student student = new Student(studentDTO.firstName(), studentDTO.lastName(), studentDTO.phone(), new Date());
        studentRepository.save(student);
        return StudentMapper.studentToDTO(student);

    }

    @Override
    public StudentDto getStudentById(Long studentId) throws StudentNotFound {
        return studentRepository.findById(studentId)
                .map(StudentMapper::studentToDTO)
                .orElseThrow(()->new StudentNotFound(String.format("ERROR: Student with id %d not found.", studentId)));

    }

    @Override
    public List<StudentDto> getAllStudents() {
        return StudentMapper.studentListToDTOs(studentRepository.findAll(Sort.by("firstName")));
    }

    @Override
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) throws StudentNotFound {
        Student existing = studentRepository.findById(studentId)
                .orElseThrow(()->new StudentNotFound(String.format("ERROR: Student with id %d not found.", studentId)));

        existing.setFirstName(studentDto.firstName());
        existing.setLastName(studentDto.lastName());
        existing.setPhone(studentDto.phone());
        studentRepository.save(existing);

        return StudentMapper.studentToDTO(existing) ;
    }

    @Override
    public void deleteStudent(Long studentId) throws StudentNotFound {
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(()->new StudentNotFound(String.format("ERROR: Student with id %d not found.", studentId)));
        studentRepository.delete(existingStudent);
    }

    @Override
    public StudentDto enrollInCourse(Long studentId, Long courseId) throws CourseNotFound, StudentNotFound, CoursePrerequisitesNotMeet {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFound("Student not found with ID: " + studentId));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new CourseNotFound("Course not found with ID: " + courseId));

        for (Course prerequisite : course.getPrerequisites()) {
            if (!student.getCourses().contains(prerequisite)) {
                throw new CoursePrerequisitesNotMeet("Course prerequisites for Course ID: " + prerequisite.getId() + " not met");
            }
        }

        student.enrollInCourse(course);
        studentRepository.save(student);

        return StudentMapper.studentToDTO(student);
    }

    @Override
    public StudentDto withdrawCourse(Long courseId, Long studentId) throws StudentNotFound, CourseNotFound {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFound("Student not found with ID: " + studentId));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new CourseNotFound("Course not found with ID: " + courseId));

        student.withdrawFromCourse(course);
        studentRepository.save(student);

        return StudentMapper.studentToDTO(student);
    }
}
