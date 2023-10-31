package com.miu.lms.service;


import com.miu.lms.constants.RoleType;

public interface UserService {

    Long createUser(String email, String password, RoleType student);
}