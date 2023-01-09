package com.resucreator.webservices.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.resucreator.webservices.user.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String jsonWebToken = token.substring(7);

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("secret")
                        .parseClaimsJws(jsonWebToken)
                        .getBody();

                String userName = claims.getSubject();

                if (userRepository.findByUserName(userName) == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "No user exists.");

                    return;
                }
            } catch (ExpiredJwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "This token has been expired.");

                return;
            } catch (Exception e) {
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "The Authorization header should contain a Bearer token.");
        }
    }
}
