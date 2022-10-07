package com.example.service;


import com.example.dtos.LoginRequest;
import com.example.dtos.TokenResponseDto;
import com.example.dtos.UserDto;
import com.example.exception.ErrorCode;
import com.example.exception.GenericException;
import com.example.util.TokenGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenGenerator tokenGenerator;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, TokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenGenerator = tokenGenerator;
    }

    public TokenResponseDto login(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            return TokenResponseDto
                    .builder()
                    .token(tokenGenerator.generateToken(auth))
                    .userDto(userService.getUserDto(loginRequest.getUsername()))
                    .build();
        } catch (final BadCredentialsException badCredentialsException) {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errorCode(ErrorCode.USER_NOT_FOUNDED)
                    .errorMessage("Invalid Username or Password !!!")
                    .build();
        }

    }




    public UserDto getAuthenticatedUser() {
        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userService.getUserDto(username);
    }




}
