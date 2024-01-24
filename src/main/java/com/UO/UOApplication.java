package com.UO;

import com.UO.DOA.UserRepository;
import com.UO.Modele.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UOApplication implements  CommandLineRunner {

	final UserRepository ur;

	public UOApplication(UserRepository ur) {
		this.ur = ur;
	}

	public static void main(String[] args) {
		SpringApplication.run(UOApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		if(this.ur.findAll().isEmpty()){
			User u=new User();
			u.setUsername("admin");
			u.setPassword("adminUO2024");
			u.setRole("admin");
			ur.save(u);
		}

	}
}
