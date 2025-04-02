package com.tekworks.gateway.util;


import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	private String secretKey = null;

	private SecretKey generateKey() {
		byte[] decode = Decoders.BASE64.decode(getSecretKey());
		return Keys.hmacShaKeyFor(decode);
	}

	public String getSecretKey() {
		return secretKey = "RqxPOuVfHoBA8Uq40MhJvfY6qEHOOWWvg6N9W9vt23s=";
	}

	public String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractClaims(token);
		return claimResolver.apply(claims);
	}
	private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
}