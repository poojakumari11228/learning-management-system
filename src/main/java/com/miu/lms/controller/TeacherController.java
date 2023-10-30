package com.miu.lms.controller;
import com.miu.lms.constants.ApiController;
import com.miu.lms.dto.teacher.NewTeacherRequest;
import com.miu.lms.dto.teacher.TeacherDto;
import com.miu.lms.exceptions.TeacherNotFound;
import com.miu.lms.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(ApiController.TEACHER_ENDPOINT)
public class TeacherController {

    private final TeacherService teacherService;


    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }
    @PostMapping("/register")
    public ResponseEntity<TeacherDto> registerTeacher(@RequestBody NewTeacherRequest teacherDTO) {
        return new ResponseEntity<>(teacherService.registerTeacher(teacherDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable Long teacherId) throws TeacherNotFound {
        return ResponseEntity.ok(teacherService.getTeacherById(teacherId));
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.OK);
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable Long teacherId, @RequestBody TeacherDto teacherDTO) throws TeacherNotFound {
        return new ResponseEntity<>(teacherService.updateTeacher(teacherId, teacherDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long teacherId) throws TeacherNotFound {
        teacherService.deleteTeacher(teacherId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
