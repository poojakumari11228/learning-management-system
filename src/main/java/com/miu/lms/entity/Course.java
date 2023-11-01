package com.miu.lms.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;
    private String description;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "course_prerequisite",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    private Set<Course> prerequisites = new HashSet<>();

    private Date creationDate;

    public Course(String name, String code, String desc, Date date) {
        this.name = name ;
        this.code = code ;
        this.creationDate= date;
        this.description = desc;
    }

    public Course(String name, String code, String desc, Date date, Course preReq) {
        this.name = name ;
        this.code = code ;
        this.creationDate= date;
        this.description = desc;
        this.prerequisites.add(preReq);
    }

    public Course(Long id, String name, String code, String desc, Date date, Course preReq) {
        this.id = id;
        this.name = name ;
        this.code = code ;
        this.creationDate= date;
        this.description = desc;
        this.prerequisites.add(preReq);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id) && name.equals(course.name) && code.equals(course.code) && description.equals(course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, description);
    }
}
