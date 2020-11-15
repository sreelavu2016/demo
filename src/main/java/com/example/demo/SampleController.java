package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	
	public String sayGreetings() {
		return "Hello Spring boot docker CI/CD PipeLine";
	}
	

}
