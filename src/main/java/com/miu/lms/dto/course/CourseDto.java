package com.miu.lms.dto.course;

import java.util.Date;

public record CourseDto(Long id, String name, String code, String desc, Date creationDate) {
}