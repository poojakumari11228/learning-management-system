package com.miu.lms.advice;

import com.miu.lms.exceptions.CourseNotFound;
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
    public Map<String, String> handlePatientNotFoundException(CourseNotFound publisherNotFoundException) {
        Map<String , String> errorMessageMap = new HashMap<>();
        errorMessageMap.put("errorMessage", publisherNotFoundException.getMessage());
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

}
