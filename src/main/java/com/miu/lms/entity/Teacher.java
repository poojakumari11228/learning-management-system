package com.miu.lms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Teacher {
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

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Course> courses;

    private String phone;

    private Date creationDate;

    public Teacher(String firstName, String lastName,String phone, Date creationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = creationDate;
        this.phone = phone;
    }

    public Teacher() {
        courses = new ArrayList<>();

    }

    public void teachCourse(Course newCourse){
        newCourse.setTeacher(this);
        courses.add(newCourse);
    }

}
