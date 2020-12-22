package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import user_details.signup_details;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("signup")
public class signup_user {

	
	@PostMapping(consumes={MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> signup(@RequestBody signup_details userDetails) {
		
		signup_details u= new signup_details();
		u.setFirstName(userDetails.getFirstName());
		u.setLastName(userDetails.getLastName());
		u.setAddress(userDetails.getAddress());
		u.setCity(userDetails.getCity());
		u.setEmail(userDetails.getEmail());
		u.setPhone(userDetails.getPhone());
		u.setPassword(userDetails.getPassword());
		u.setId(userDetails.getId());
		u.entry();
		
		System.out.println(userDetails.getAddress());
		return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
	}
	
	
}
