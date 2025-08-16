package com.example.xamantei;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.xamantei.entity.AppRole;
import com.example.xamantei.repository.AppRoleRepository;
import com.example.xamantei.repository.AppUserRepository;
import com.example.xamantei.service.AppUserService;

@SpringBootApplication
public class XamanteApplication {

	public static void main(String[] args) {
		SpringApplication.run(XamanteApplication.class, args);
	}

	// @Bean
	// CommandLineRunner commandLineRunner(AppRoleRepository appRoleRepository){

	// 	return args -> {
			
	// 		AppRole appRole = new AppRole();
	// 		AppRole appRole1 = new AppRole();
	// 		appRole.setRoleName("ADMIN");
	// 		appRole1.setRoleName("USER");

	// 		System.out.println(appRole );
	// 		System.out.println(appRole1 );

	// 		appRoleRepository.save(appRole);
	// 		appRoleRepository.save(appRole1);
	// 	};


	// }


}
