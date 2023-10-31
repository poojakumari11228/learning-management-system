package com.miu.lms.service.impl;

import com.miu.lms.constants.RoleType;
import com.miu.lms.entity.Role;
import com.miu.lms.entity.User;
import com.miu.lms.repo.RoleRepository;
import com.miu.lms.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements com.miu.lms.service.UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Long createUser(String email, String password, RoleType student) {
        User newUser = new User(email, passwordEncoder.encode(password),
                true,true, true, true);
        Optional<Role> role = roleRepository.findByRole(student.getRoleName());
        if(role.isPresent())
            newUser.setRoles(List.of(role.get()));
        else
            newUser.setRoles(List.of(new Role(student.getRoleName())));

       var user = userRepository.save(newUser);
        return user.getId();
    }
}
