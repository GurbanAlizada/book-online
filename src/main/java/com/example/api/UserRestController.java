package com.example.api;


import com.example.dtos.UserDto;
import com.example.dtos.UserRequest;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;


    @PostMapping("/save")
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }









}


/*

{
    "username" : "qurban" ,
    "password" : "qax12345" ,
    "role" : "ADMIN" ,
    "email" : "qax@gmail.com"
}


 */