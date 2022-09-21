package com.example.employeehierarchy.jwt;

import com.example.employeehierarchy.models.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil {
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
	public static final String ADMIN_ROLE = "ADMIN";

	@Value("${app.jwt.secret}")
	private String SECRET_KEY;
	
	public String generateAccessToken(User user) {
		
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuer("EmployeeHierarchy")
				.claim("roles", ADMIN_ROLE)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			log.error("JWT expired", ex);
		} catch (IllegalArgumentException ex) {
			log.error("Token is null, empty or only whitespace", ex);
		} catch (MalformedJwtException ex) {
			log.error("JWT is invalid", ex);
		} catch (UnsupportedJwtException ex) {
			log.error("JWT is not supported", ex);
		} catch (SignatureException ex) {
			log.error("Signature validation failed");
		}
		
		return false;
	}
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}
}
