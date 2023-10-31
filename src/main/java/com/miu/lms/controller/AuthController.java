package com.miu.lms.controller;

import com.miu.lms.constants.ApiController;
import com.miu.lms.dto.auth.AuthRequest;
import com.miu.lms.dto.auth.AuthResponse;
import com.miu.lms.entity.User;
import com.miu.lms.utils.JWTManagementUtilityService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiController.AUTHENTICATE_ENDPOINT)
public class AuthController {

    private final JWTManagementUtilityService jwtManagementUtilityService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthController(JWTManagementUtilityService jwtManagementUtilityService,
                          AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.jwtManagementUtilityService = jwtManagementUtilityService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public AuthResponse authenticateUser(@Valid @RequestBody AuthRequest userAuthRequest) throws Exception {
       Authentication auth;
        try {
           auth =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuthRequest.getUsername(),
                            userAuthRequest.getPassword())
            );
        } catch (Exception ex) {
            System.out.println("UserAuthException is: " + ex.getMessage());
            System.out.println("Invalid Username and/or Password!");
            throw ex;
        }
        User userDetails = (User) userDetailsService.loadUserByUsername(auth.getName());


        return new AuthResponse(jwtManagementUtilityService.generateToken(userDetails));
    }
}
