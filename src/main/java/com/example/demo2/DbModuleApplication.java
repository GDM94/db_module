package com.example.demo2;

import com.example.communication.CommunicationModuleApp;
import com.example.communication.model.*;
import com.example.communication.services.AnagraficaService;
import com.example.demo2.repositories.AuthorityRepository;
import com.example.demo2.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.communication.model"})
public class DbModuleApplication {

	@Autowired
	private ApplicationContext webApplicationContext;

	@Autowired
	PasswordEncoder passwordEncoder;


	private static final Logger logger = LoggerFactory.getLogger(DbModuleApplication.class);


	@Bean
	public CommandLineRunner loadData(UserRepository userRepository, AuthorityRepository authorityRepository) {
		return (args) -> {

			User user=userRepository.findByUsername("admin");

			if(user == null){

				/**
				 * Inizializzo i dati del mio test
				 */


				Authority authorityAdmin=new Authority();
				authorityAdmin.setName(AuthorityName.ROLE_ADMIN);
				authorityAdmin=authorityRepository.save(authorityAdmin);

				Authority authorityUser=new Authority();
				authorityUser.setName(AuthorityName.ROLE_USER);
				authorityUser=authorityRepository.save(authorityUser);


				List<Authority> authorities = Arrays.asList(new Authority[] {authorityAdmin,authorityUser});


				user = new User();
				user.setAuthorities(authorities);
				user.setEnabled(true);
				user.setUsername("admin");
				user.setPassword(passwordEncoder.encode("admin"));

				user = userRepository.save(user);

			}
		};
	}


	public static void main(String[] args) {
		logger.info("Hello from Logback");

		SpringApplication.run(DbModuleApplication.class, args);
	}

}
