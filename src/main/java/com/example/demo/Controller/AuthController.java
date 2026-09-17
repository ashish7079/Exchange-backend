package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.RegisterDto;
import com.example.demo.Model.Auth;
import com.example.demo.repo.AuthRepo;
import com.example.demo.util.Jwtutil;

@RequestMapping("/auth")
@RestController
public class AuthController {

	@Autowired
	private AuthRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	 
	@Autowired
	private Jwtutil util;
	
	@PostMapping("/register")
	public ResponseEntity<?> signUp(@RequestBody RegisterDto register){
		
		if(repo.existsByEmail(register.getEmail())) {
			return ResponseEntity.badRequest().body("Email Already Exist");
		}
		Auth auth = new Auth();
		auth.setName(register.getName());
		auth.setEmail(register.getEmail());
		auth.setPassword(encoder.encode(register.getPassword()));
		auth.setCollege(register.getCollege());
		auth.setBio(register.getBio());
		
		repo.save(auth);
		
		return ResponseEntity.ok("Registration Success");
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> signIn(@RequestBody LoginDto login){
		
		String email = login.getEmail();
		String password = login.getPassword();
		
		Auth user = repo.findByEmail(email).orElse(null);
		
		if(user == null){
		    return ResponseEntity.status(401).body("User not found");
		}
		
		if(!encoder.matches(password, user.getPassword())) {
			return ResponseEntity.status(401).body("Invalid Password");
			
		}
		String token = util.generateToken(user.getEmail());
		
		return ResponseEntity.ok(token);
		
	}
	
		@GetMapping("/check")
		public String checks() {
		return "jwt verified successfully";
		}
}
