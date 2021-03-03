package com.task.security.jwt;

import com.task.user.entity.UserEntity;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource("classpath:security/jwt.properties")
public class JwtUtils {

    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(UserEntity userEntity) {

        return Jwts.builder()
                .setSubject((userEntity.getId().toString()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String validateJwtToken(String authToken) {
        String message;

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return "ok";
        } catch (SignatureException e) {
            message = "Invalid JWT signature: { " + e.getMessage() + " }";
        } catch (MalformedJwtException e) {
            message = "Invalid JWT token: { " + e.getMessage() + " }";
        } catch (ExpiredJwtException e) {
            message = "JWT token is expired: { " + e.getMessage() + " }";
        } catch (UnsupportedJwtException e) {
            message = "JWT token is unsupported: { " + e.getMessage() + " }";
        } catch (IllegalArgumentException e) {
            message = "JWT claims string is empty: { " + e.getMessage() + " }";
        }

        return message;
    }
}
