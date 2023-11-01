package com.miu.lms.service;


import com.miu.lms.constants.RoleType;
import com.miu.lms.exceptions.UserAlreadyExists;

public interface UserService {

    Long createUser(String email, String password, RoleType student) throws UserAlreadyExists;
}