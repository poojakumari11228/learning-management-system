package com.miu.lms.config;

import com.miu.lms.constants.ApiController;
import com.miu.lms.filter.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebApiSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JWTAuthFilter jwtAuthFilter;

    public WebApiSecurityConfig(UserDetailsService userDetailsService,
                                JWTAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests
                        (
                        auth -> auth.requestMatchers(ApiController.AUTHENTICATE_ENDPOINT+"/**").permitAll()
                                .requestMatchers(ApiController.COURSE_ENDPOINT+"/**").hasAnyAuthority("ADMIN")
                                .requestMatchers(ApiController.STUDENT_ENDPOINT+"/**").hasAnyAuthority("ADMIN", "STUDENT")
                                .requestMatchers(ApiController.STUDENT_ENDPOINT+"/register").permitAll()
                                .requestMatchers(ApiController.TEACHER_ENDPOINT+"/**").hasAnyAuthority("ADMIN", "TEACHER")
                                .requestMatchers(ApiController.TEACHER_ENDPOINT+"/register").permitAll()

                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
