package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SampleMessage message = new SampleMessage();
		message.setCourseName("DevOps Training");
		message.setMessage("Sample Project");
		message.setUserName("Sreenu.soft2012@gmail.com");
		System.out.println(message);
		
	}

}
