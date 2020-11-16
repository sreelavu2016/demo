package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SampleController {
	
	@GetMapping("/")
	public String sayGreetings() {
		return "Hello Spring boot docker CI/CD PipeLine";
	}
	

}
