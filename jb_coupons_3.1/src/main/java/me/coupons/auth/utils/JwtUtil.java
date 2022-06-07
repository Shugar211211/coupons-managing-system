package me.coupons.auth.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.coupons.auth.models.Principal;
import me.coupons.entity.ClientType;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	@Value("${jwt.expirationperiod}")
	private int JWT_LIFETIME;
	
	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public Date extractRole(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}
	
	
	public String renewToken(String token) {
		Claims claims = this.extractAllClaims(token);
		return this.createToken(claims);
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(Principal principal) {
		Map<String, Object>claims = new HashMap<>();
		claims.put("id", (Integer)principal.getId());
		claims.put("role", (ClientType)principal.getRole());
		claims.put("name", (String)principal.getName());
		return createToken(claims);
	}
	
	private String createToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims)
//				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				// set token expiration time for 30 minutes
				.setExpiration(new Date(System.currentTimeMillis()+JWT_LIFETIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	public Boolean validateToken(String token, Principal principal) {
		final Claims claims = extractAllClaims(token);
		if(((Integer)claims.get("id")).intValue() == principal.getId() 
				&& claims.get("role").equals(principal.getRole())
				&& claims.get("name").equals(principal.getName())
				&& !isTokenExpired(token))
			return true;
		else 
			return false;
	}
}
