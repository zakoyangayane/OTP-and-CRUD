package com.task.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.base.response.BaseResponse;
import com.task.exception.NotReadException;
import com.task.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtValidator jwtValidator;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String message = "Cannot set user authentication";
        String jwt = parseJwt(request);
        if (jwt != null) {
            try {
                message = jwtUtils.validateJwtToken(jwt);
                if (!message.equals("ok") || !userRepository.existsById(Long.parseLong(jwtValidator.validateAndGetSubject(jwt)))) {
                    throw new NotReadException(message, HttpStatus.UNAUTHORIZED);
                }
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                BaseResponse<Object> baseResponse = new BaseResponse<>(false, message, null);
                OutputStream out = response.getOutputStream();
                com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(out, baseResponse);
                out.flush();
            }
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
