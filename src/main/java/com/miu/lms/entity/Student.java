package com.miu.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(nullable=false)
    @NotBlank(message = "first name is required")
    private String firstName;
    @Column(nullable=false)
    @NotBlank(message = "last name is required")
    private String lastName;
    @Column(nullable=false)
    @NotBlank(message = "contact is required")
    private String phone;
    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
    private Date creationDate;

    public Student(String firstName, String lastName, String phone, Date creationDate, Long userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.creationDate = creationDate;
        this.userId = userId;
    }

    public void enrollInCourse(Course course){
        if (!courses.contains(course)) {
            courses.add(course);
            course.getStudents().add(this);
        }
    }

    public void withdrawFromCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.getStudents().remove(this);
        }
    }


}
