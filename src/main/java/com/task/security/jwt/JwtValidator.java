package com.task.security.jwt;


import com.task.exception.NotReadException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:security/jwt.properties")
public class JwtValidator {

    @Value("${jwtSecret}")
    private String jwtSecret;

    private final JwtUtils jwtUtils;

    @Autowired
    public JwtValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @SneakyThrows
    public String validateAndGetSubject(String token) {
        try {
            if (jwtUtils.validateJwtToken(token).equals("ok")) {
                Claims body = Jwts.parser()
                        .setSigningKey(jwtSecret)
                        .parseClaimsJws(token)
                        .getBody();
                return body.getSubject();
            } else {
                throw new NotReadException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new NotReadException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
        }

    }
}
