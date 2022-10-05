package com.example.security;

import com.example.service.UserDetailsServiceImpl;
import com.example.util.TokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final TokenGenerator tokenGenerator;
    private final UserDetailsServiceImpl userDetailsService;


    public JwtFilter(TokenGenerator tokenGenerator, UserDetailsServiceImpl userDetailsService) {
        this.tokenGenerator = tokenGenerator;
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      //  System.out.println(request.getServletPath().toString());

        String token = getToken(request);
        String username;
        try {
            if (!token.isBlank()) {
                username = tokenGenerator.verifyJWT(token).getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("error -> " + e.getMessage());
            sendError(response, e);
        }
    }



    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            return "";
        }
        return header.substring(7);
    }


    private void sendError(HttpServletResponse res, Exception e) throws IOException {
        res.setContentType("application/json");
        Map<String, String> errors = new HashMap<>();
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        errors.put("error", e.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        res.getWriter().write(mapper.writeValueAsString(errors));
    }




}
