package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import user_details.signup_details;

@RestController
@RequestMapping("login")


public class login {

	@PostMapping(consumes={MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> login_function(@RequestBody signup_details userDetails) {
		
		signup_details u = new signup_details();
		String str="select * from login";
		
		
		String id_details=userDetails.getEmail();
		String password=userDetails.getPassword();
		
		
		try (
   			 Connection conn = DriverManager.getConnection(
              		  "jdbc:mysql://localhost:3306/openevent?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "myuser", "xxxx");
                
                  Statement stmt = conn.createStatement();
                ){
			  ResultSet rset=stmt.executeQuery(str);
			  
			  MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    		 byte[] hash = digest.digest(
	    		   password.getBytes(StandardCharsets.UTF_8));
	    		 String sha256hex = new String(Hex.encode(hash));
			 
			 while(rset.next())
	    	  {   
	    		  if((rset.getString("email")).equalsIgnoreCase(id_details) && (rset.getString("Password")).equals(sha256hex))
	    		  { 
	    			 return new ResponseEntity<>(null, HttpStatus.ACCEPTED); //code 202
	    		  }
	    	  }
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);//404
	}
}
