package com.miu.lms.advice;

import com.miu.lms.exceptions.CourseNotFound;
import com.miu.lms.exceptions.CoursePrerequisitesNotMeet;
import com.miu.lms.exceptions.StudentNotFound;
import com.miu.lms.exceptions.TeacherNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CourseNotFound.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleCourseNotFoundException(CourseNotFound courseNotFoundException) {
        Map<String , String> errorMessageMap = new HashMap<>();
        errorMessageMap.put("errorMessage", courseNotFoundException.getMessage());
        return errorMessageMap;
    }

    @ExceptionHandler(TeacherNotFound.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleTeacherNotFoundException(TeacherNotFound teacherNotFoundException) {
        Map<String , String> errorMessageMap = new HashMap<>();
        errorMessageMap.put("errorMessage", teacherNotFoundException.getMessage());
        return errorMessageMap;
    }
    @ExceptionHandler(StudentNotFound.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleStudentNotFoundException(StudentNotFound studentNotFoundException) {
        Map<String , String> errorMessageMap = new HashMap<>();
        errorMessageMap.put("errorMessage", studentNotFoundException.getMessage());
        return errorMessageMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleDataValidationException(MethodArgumentNotValidException exception) {
        Map<String , String> errorMessageMap = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error-> errorMessageMap.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));
        return errorMessageMap;
    }

    @ExceptionHandler(CoursePrerequisitesNotMeet.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleCoursePrerequisitesNotMeet(CoursePrerequisitesNotMeet coursePrerequisitesNotMeet) {
        Map<String , String> errorMessageMap = new HashMap<>();
        errorMessageMap.put("errorMessage", coursePrerequisitesNotMeet.getMessage());
        return errorMessageMap;
    }

}
