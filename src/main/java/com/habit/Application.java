package com.habit;

import com.habit.domain.Role;
import com.habit.domain.RoleType;
import com.habit.domain.User;
import com.habit.repository.RoleRepository;
import com.habit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
@Component
@AllArgsConstructor
class DemoCommandLineRunner implements CommandLineRunner {

	RoleRepository roleRepository;

	PasswordEncoder passwordEncoder;

	UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception { // this method will run before app and check if db is empty and add roles
		// if so.

		if (!roleRepository.findByType(RoleType.ROLE_CUSTOMER).isPresent()) {
			Role roleCustomer = new Role();
			roleCustomer.setType(RoleType.ROLE_CUSTOMER);
			roleRepository.save(roleCustomer);
		}

		if (!roleRepository.findByType(RoleType.ROLE_ADMIN).isPresent()) {
			Role roleAdmin = new Role();
			roleAdmin.setType(RoleType.ROLE_ADMIN);
			roleRepository.save(roleAdmin);
		}

		if (!userRepository.findByUserName("superadmin").isPresent()) {
			User admin = new User();
			Role role = roleRepository.findByType(RoleType.ROLE_ADMIN).get();
			admin.setRoles(new HashSet<>(Collections.singletonList(role)));
			admin.setUserName("superadmin");
			admin.setBuiltIn(true);
			admin.setPassword(passwordEncoder.encode("Ankara06"));
			userRepository.save(admin);
		}

		if (!userRepository.findByUserName("supercustomer").isPresent()) {
			User customer = new User();
			Role role = roleRepository.findByType(RoleType.ROLE_CUSTOMER).get();
			customer.setRoles(new HashSet<>(Collections.singletonList(role)));
			customer.setUserName("supercustomer");
			customer.setBuiltIn(true);
			customer.setPassword(passwordEncoder.encode("Ankara06"));
			userRepository.save(customer);

		}
	}
}
