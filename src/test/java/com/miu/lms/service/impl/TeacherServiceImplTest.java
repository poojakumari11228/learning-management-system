package com.miu.lms.service.impl;

import com.miu.lms.dto.teacher.TeacherDto;
import com.miu.lms.entity.Course;
import com.miu.lms.entity.Teacher;
import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.exceptions.TeacherNotFound;
import com.miu.lms.repo.CourseRepo;
import com.miu.lms.repo.RoleRepository;
import com.miu.lms.repo.TeacherRepo;
import com.miu.lms.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TeacherServiceImplTest {

    @Mock
    private TeacherRepo teacherRepo;
    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateTeacher_Success() throws TeacherNotFound {
        Date date = new Date();
        Teacher existingTeacher = new Teacher(1L, "John", "Doe", "123456", date, 1L);
        TeacherDto updatedTeacherDto = new TeacherDto(1L,"Jane", "Smith", "789123", List.of(), date);
        Mockito.when(teacherRepo.findById(1L)).thenReturn(Optional.of(existingTeacher));
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(existingTeacher));
        TeacherDto updatedTeacher = teacherService.updateTeacher(1L, updatedTeacherDto);
        assertEquals("Jane", updatedTeacher.firstName());
        assertEquals("Smith", updatedTeacher.lastName());
        assertEquals("789123", updatedTeacher.phone());
     }
    @Test
    public void testUpdateTeacher_TeacherNotFound() {
        when(teacherRepo.findById(1L)).thenReturn(Optional.empty());

        TeacherDto updatedTeacherDto = new TeacherDto(1L,"Jane", "Smith", "789123", List.of(), new Date());

        assertThrows(TeacherNotFound.class, () -> {
            teacherService.updateTeacher(1L, updatedTeacherDto);
        });
    }


    @Test
    public void testDeleteTeacherSuccess() throws TeacherNotFound {
        Long teacherId = 1L;

        Teacher existingTeacher = new Teacher(teacherId, "John", "Doe", "123456", new Date(), 1L);


        when(teacherRepo.findById(teacherId)).thenReturn(Optional.of(existingTeacher));

        assertDoesNotThrow(() -> teacherService.deleteTeacher(teacherId));

    }
    @Test
    public void testDeleteTeacher_TeacherNotFound() {

        when(teacherRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TeacherNotFound.class, () -> {
            teacherService.deleteTeacher(1L);
        });
    }
    @Test
    public void testGetTeacherById() throws TeacherNotFound {
        Teacher existingTeacher = new Teacher(1L, "John", "Doe", "123456", new Date(), 1L);
        Mockito.when(teacherRepo.findById(1L)).thenReturn(Optional.of(existingTeacher));

        TeacherDto result = teacherService.getTeacherById(1L);

        assertEquals(existingTeacher.getId(), result.id());
        assertEquals(existingTeacher.getFirstName(), result.firstName());
        assertEquals(existingTeacher.getLastName(), result.lastName());
        assertEquals(existingTeacher.getPhone(), result.phone());
    }

    @Test
    public void testGetTeacherById_TeacherNotFound() {
        when(teacherRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TeacherNotFound.class, () -> {
            teacherService.getTeacherById(1L);
        });
    }
        @Test
    public void testGetAllTeachers() {
        List<Teacher> teachers = List.of(
                new Teacher(1L, "John", "Doe", "123456", new Date(), 1L),
                new Teacher(2L, "Jane", "Smith", "789012", new Date(), 2L)
        );

        Mockito.when(teacherRepo.findAll(Sort.by("firstName"))).thenReturn(teachers);
        List<TeacherDto> result = teacherService.getAllTeachers();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).firstName());
        assertEquals("Doe", result.get(0).lastName());
        assertEquals("123456", result.get(0).phone());
        assertEquals("Jane", result.get(1).firstName());
        assertEquals("Smith", result.get(1).lastName());
        assertEquals("789012", result.get(1).phone());
    }

    @Test
    public void testGetAllTeachers_NoTeachersFound() {
        when(teacherRepo.findAll(Sort.by("firstName"))).thenReturn(Collections.emptyList());

        List<TeacherDto> result = teacherService.getAllTeachers();

        assertTrue(result.isEmpty());
    }

        @Test
    public void testTeachNewCourse_CourseNotFound() {
        Teacher teacher = new Teacher(1L, "John", "Doe", "123456", new Date(), 1L);

        Mockito.when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));

        Mockito.when(courseRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFound.class, () -> teacherService.teachNewCourse(1L, 1L));
    }

    @Test
    public void testWithdrawCourse_Success() throws CourseNotFound, TeacherNotFound {
        Teacher teacher = new Teacher(1L, "John", "Doe", "123456", new Date(), 1L);
        Course course = new Course( 1L,"Math", "MATH101", "Mathematics Course", new Date(), null);
        teacher.teachCourse(course);

        Mockito.when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));

        Mockito.when(courseRepo.findById(1L)).thenReturn(Optional.of(course));

        TeacherDto result = teacherService.withdrawCourse(1L, 1L);

        assertEquals(0, result.courses().size());
    }

    @Test
    public void testWithdrawCourse_CourseNotFound() {
        Teacher teacher = new Teacher(1L, "John", "Doe", "123456", new Date(), 1L);

        Mockito.when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));

        Mockito.when(courseRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFound.class, () -> teacherService.withdrawCourse(1L, 1L));
    }


}