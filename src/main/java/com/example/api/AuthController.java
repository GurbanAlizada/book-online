package com.example.api;


import com.example.dtos.LoginRequest;
import com.example.dtos.TokenResponseDto;
import com.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/auth")
public class AuthController {


    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    // @PreAuthorize("")
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


    @GetMapping("foo5")
    public String foo5(){
        return ( (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toString();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deneme")
    public String foo6(){
        return "PreAuthorize kullanimi";
    }


}
