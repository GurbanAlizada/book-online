package com.example.api;


import com.example.dtos.LoginRequest;
import com.example.dtos.TokenResponseDto;
import com.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok( authService.login(request));
    }



    @GetMapping("/admin")
    public String foo1(){
        return "admin";
    }

    @GetMapping("/user")
    public String foo2(){
        return "user";
    }

    @GetMapping("/only/user")
    public String foo3(){
        return "only user";
    }

    @GetMapping("/helloWorld")
    public String foo4(){
        return "Hello World";
    }


}
