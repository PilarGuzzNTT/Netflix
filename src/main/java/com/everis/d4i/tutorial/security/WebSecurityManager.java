package com.everis.d4i.tutorial.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.everis.d4i.tutorial.services.impl.UserServiceImpl;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityManager {

	@Autowired
	private UserServiceImpl myUserDetailService;

	@Autowired
	AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public JwtAuthorizationFilter authenticationJwtTokenFilter() {
		return new JwtAuthorizationFilter();
	}

	// Indicamos que la configuración se hará a travéx del servicio.
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailService);
	}

	// Método que usaremos más abajo
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserServiceImpl();
	}

	// Método que nos suministrará la codificación
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Método que autentifica
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	// Aquí es donde podemos especificar qué es lo que hace y lo que no
	// según el rol del usuario
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.authorizeHttpRequests((requests) -> {
					requests.mvcMatchers(HttpMethod.GET, "/actors/**").hasAnyAuthority("USER_ROLE", "ADMIN_ROLE")
							.mvcMatchers(HttpMethod.GET, "/tv-show/{id}/awards").hasAnyAuthority("USER_ROLE", "ADMIN_ROLE")
							.mvcMatchers(HttpMethod.POST, "/actors/**").hasAuthority("ADMIN_ROLE")
							.mvcMatchers(HttpMethod.PATCH, "/actors/**").hasAuthority("ADMIN_ROLE")
							.mvcMatchers(HttpMethod.PUT, "/actors/**").hasAuthority("ADMIN_ROLE")
							.mvcMatchers(HttpMethod.DELETE, "/actors/**").hasAuthority("ADMIN_ROLE")
							.antMatchers("/categories/**").hasAuthority("ADMIN_ROLE")
							.antMatchers(RestConstants.RESOURCE_CHAPTER + "/**").hasAuthority("ADMIN_ROLE")
							.antMatchers(RestConstants.RESOURCE_SEASON + "/**").hasAuthority("ADMIN_ROLE")
							.antMatchers("/tv-shows/**").hasAuthority("ADMIN_ROLE")

							.antMatchers("/login").permitAll()
							.antMatchers("/registrer").permitAll()

							.antMatchers("/swagger-ui/**").permitAll()
							.antMatchers("/v3/api-docs/**").permitAll()
							.anyRequest().denyAll();
				});
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
