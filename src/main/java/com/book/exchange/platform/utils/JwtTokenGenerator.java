package com.book.exchange.platform.utils;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenGenerator {

	// Generate a secure random key for HMAC-SHA256 algorithm
    private static final Key SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    public static String generateJwtToken(String subject, long expirationTimeMillis) {
        // Set the expiration time
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeMillis);

        // Build the JWT token
        String jwtToken = Jwts.builder()
                .setSubject(subject)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();

        return jwtToken;
    }

    public static String getSubjectFromJwtToken(String jwtToken) {
        try {
            // Parse the JWT token and extract claims
            Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(jwtToken).getBody();

            // Get the subject claim from the claims
            String subject = claims.getSubject();

            return subject;
        } catch (JwtException e) {
            // Log the error message
            System.err.println("Error decoding JWT token: " + e.getMessage());
            // Optionally, re-throw the exception or return null to indicate failure
            return null;
        }
    }

    public static void main(String[] args) {
        // Example usage: generate a JWT token with a subject and expiration time
        String subject = "abc@gmail.com";
        String jwtToken = generateJwtToken(subject, 3600000); // Token expires in 1 hour (3600 seconds)
        System.out.println("Generated JWT token: " + jwtToken);

        // Example usage: decode a JWT token and get the subject
        String decodedSubject = getSubjectFromJwtToken(jwtToken);
        if (decodedSubject != null) {
            System.out.println("Subject from JWT token: " + decodedSubject);
        } else {
            System.out.println("Failed to decode JWT token.");
        }
    }
}
