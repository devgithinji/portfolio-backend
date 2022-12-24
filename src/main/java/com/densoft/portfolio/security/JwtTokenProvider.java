package com.densoft.portfolio.security;

import com.densoft.portfolio.exceptions.ApIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-miliseconds}")
    private int jwtExpirationInMs;

    //generate token
    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    //get username from token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    //validate jwt token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException signatureException) {
            throw new ApIException("Invalid jwt signature");
        } catch (MalformedJwtException malformedJwtException) {
            throw new ApIException("Invalid jwt token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new ApIException("Expired jwt token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new ApIException("Unsupported jwt token");
        } catch (IllegalArgumentException exception) {
            throw new ApIException("Jwt claims string is empty");
        }
    }
}
