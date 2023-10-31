package com.miu.lms.dto.student;

import java.util.Date;

public record StudentDto(Long id, String firstName, String lastName, String phone, Date creationDate) {
}