package com.example.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.exception.GenericException;
import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class TokenGenerator {


    @Value("${jwt-variables.KEY}")
    private String KEY;
    // kitaplik

    @Value("${jwt-variables.ISSUER}")
    private String ISSUER;
    // folksdev

    @Value("${jwt-variables.EXPIRES_ACCESS_TOKEN_MINUTE}")
    private Integer EXPIRES_ACCESS_TOKEN_MINUTE;
    // 15


    public Map<String,String> generateToken(Authentication auth) {

        String username = ((UserDetails) auth.getPrincipal()).getUsername();


        String access_token =  JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + (3 * 60 * 1000)))
                .withIssuer(ISSUER)
                .sign(Algorithm.HMAC256(KEY.getBytes()));


        String refresh_token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+ 30 * 60 * 1000))
                .withIssuer(ISSUER)
                .sign(Algorithm.HMAC256(KEY.getBytes()));


        Map<String , String> tokens = new HashMap<>();
        tokens.put("access_token" , access_token);
        tokens.put("refresh_token" , refresh_token);
//        response.setContentType(APPLICATION_JSON_VALUE);
//        new ObjectMapper().writeValue(response.getOutputStream() , tokens);


        return tokens;

//        return JWT.create()
//                .withSubject(username)
//                .withExpiresAt(new Date(System.currentTimeMillis() + (EXPIRES_ACCESS_TOKEN_MINUTE * 60 * 1000)))
//                .withIssuer(ISSUER)
//                .sign(Algorithm.HMAC256(KEY.getBytes()));
    }



    public DecodedJWT verifyJWT(String token) {

        Algorithm algorithm = Algorithm.HMAC256(KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm)
               // .acceptExpiresAt(20)
                .build();
        try {
            return verifier.verify(token);
        } catch (Exception e) {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }





}


