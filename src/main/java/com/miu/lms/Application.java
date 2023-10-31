package com.miu.lms;

import com.miu.lms.entity.Role;
import com.miu.lms.entity.User;
import com.miu.lms.repo.RoleRepository;
import com.miu.lms.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application implements Runnable {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;


	Application(UserRepository userRepository,
				RoleRepository roleRepository,
				PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run() {
	}

	@PostConstruct
	public void initAdminUser() {
		var adminUser = userRepository.findByEmail("admin@lms.com");
		if(adminUser.isEmpty()) {
			List<Role> listAdminRoles = new ArrayList<>();
			var adminRole = roleRepository.findByRole("ADMIN");
			if(!adminRole.isPresent()) {
				var newAdminRole =  new Role("ADMIN");
				listAdminRoles.add(newAdminRole);
			} else {
				listAdminRoles.add(adminRole.get());
			}
			User newAdminUser = new User("admin@lms.com",
					passwordEncoder.encode("test1234"),
					true,true, true, true);

			newAdminUser.setRoles(listAdminRoles);
			userRepository.save(newAdminUser);
		}
	}
}
