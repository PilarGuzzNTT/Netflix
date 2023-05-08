package com.everis.d4i.tutorial.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.everis.d4i.tutorial.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// Creamos un objeto para almacenar las credenciales del usuario que está en el
		// token
		LoginCredential loginCredential = new LoginCredential();
		try {
			// Mapeamos el json a la clase loginCredential
			loginCredential = new ObjectMapper().readValue(request.getReader(), LoginCredential.class);
		} catch (Exception e) {
		}
		UsernamePasswordAuthenticationToken usernamePat = new UsernamePasswordAuthenticationToken(
				loginCredential.getUsername(), loginCredential.getPassword(), Collections.emptyList());
		return getAuthenticationManager().authenticate(usernamePat);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		String token = JwtUtils.generateToken(user.getUsername(), user.getRole());
		response.addHeader("Authorization", "Bearer " + token);
		response.getWriter().flush();
		super.successfulAuthentication(request, response, chain, authResult);
	}

}
