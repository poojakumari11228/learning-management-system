package com.miu.lms.dto.course;

import java.util.Date;
import java.util.Set;

public record CourseDto(Long id, String name, String code, String desc, Date creationDate, Set<CourseDto> prerequisites) {
}