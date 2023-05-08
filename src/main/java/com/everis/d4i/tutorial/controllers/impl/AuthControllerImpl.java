package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.entities.User;
import com.everis.d4i.tutorial.security.JwtUtils;
import com.everis.d4i.tutorial.security.LoginCredential;
import com.everis.d4i.tutorial.services.impl.UserServiceImpl;

@RestController
public class AuthControllerImpl {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServiceImpl userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginCredential loginRequest) {
		// Si el usuario y el password que le paso son los adecuados me devuele un
		// autentication
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		String jwt = JwtUtils.generateToken(loginRequest.getUsername(), user.getRole());
		User userDetails = (User) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(jwt);
	}
	
	
	@PostMapping("/registrer")
	public ResponseEntity<?> registrerUser(@RequestBody User user, HttpServletRequest request) {
		
		if (userService.findByUsername(user.getUsername()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Identificador o email ya existe");
		}
		User u = new User();
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setRole("USER_ROLE");
		u.setEnabled(true);
		

		try {
			userService.add(u);
			return ResponseEntity.status(HttpStatus.CREATED).body(u);
		} catch (Exception e) {
			System.out.println(e.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

}
