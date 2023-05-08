package com.everis.d4i.tutorial.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	// Declaramos el token que usaremos para el cifrado y la decodificación.
		// Lo suyo es que este token sea generado de forma aleatoria al empezar la
		// aplicación
		private final static String ACCESS_TOKEN_SECRET = "sksksskskskskiwoapqowlakskiwoaskskskskiwoapqowlapqowla";
		// Definimos el tiempo de validez del token en segundos. Mientras estemos en
		// fase de desarrollo
		// podemos definir tiempos largos para que no nos den problema la caducidad del
		// token.
		// Cuando nuestra aplicación esté en explotación deberemos ajustar este tiempo.
		// Estos dos parámetro también se pueden definir el el fichero
		// aplicattion.properties
		private final static Long ACCESS_TOKEN_VALIDATY_SECONDS = (long) 2592000; // 30 días

		// Método que genera un token. Este método debe recibir toda la información que
		// queremos
		// que quede almacenada en el token, en nuestro caso vamos a guardar el
		// username, el email y el role

		
		public static String generateToken(String username,  String role) {

			// Establecemos la fecha de expiración del token en milisegundos
			Date expirationDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATY_SECONDS * 1000);

			// Creamos un mapa para guardar toda la información que queramos guardar en el
			// token
			Map<String, Object> extra = new HashMap<>();
			extra.put("role", role);

			// Construimos el token con el nombre del usuario, la fecha de expiración, la
			// información
			// que queremos guardar y el token que vamos a user para encriptarlo.
			String token = Jwts.builder().setSubject(username).setExpiration(expirationDate).addClaims(extra)
					.signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())).compact();

			// Al token generado le podemos añadir alguna información para que luego
			// comprueba que
			// es un token generado por nosotros mismos.

			//return "Bearer " + token;
			return token;

		}
		
		
		// Obtiene al información del usuario a partir de un token o nulo si el token no
		// es correcto
		// Deberemos verificar que el token que nos pasan es un token válido

		public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
			try {
				// Claims == PayLoad
				Claims claims = Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build()
						.parseClaimsJws(token).getBody();

				String username = claims.getSubject();
				String role = (String) claims.get("role");
				List<SimpleGrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(role));

				return new UsernamePasswordAuthenticationToken(username, null, authorities);
			} catch (JwtException e) {
				return null;
			}

		}


}
