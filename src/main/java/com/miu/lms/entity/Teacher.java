package com.miu.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long userId;
    @Column(nullable=false)
    @NotBlank(message = "first name is required")
    private String firstName;
    @Column(nullable=false)
    @NotBlank(message = "last name is required")
    private String lastName;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    private Date creationDate;


}
