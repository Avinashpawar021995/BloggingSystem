package com.org.Blog;

import java.util.List;

import org.aspectj.lang.reflect.CatchClauseSignature;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.org.Blog.confign.AppConstants;
import com.org.Blog.entity.Role;
import com.org.Blog.respositry.RoleRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// System.out.println(this.passwordEncoder.encode("Ram@123"));

		try {
			Role role = new Role();
			role.setRoleid(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			Role role2 = new Role();
			role2.setRoleid(AppConstants.NORMLA_USER);
			role2.setName("ROLE_NORMAL");
			List<Role> roles = List.of(role, role2);

			List<Role> result = this.roleRepository.saveAll(roles);
			result.forEach(r -> {
				// System.out.println(r.getName());
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
