package com.everis.d4i.tutorial.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @Getter @Setter @Builder@AllArgsConstructor @NoArgsConstructor
public class LoginCredential {
	
	private String username;
	private String password;
}
