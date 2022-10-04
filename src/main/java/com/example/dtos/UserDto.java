package com.example.dtos;


import com.example.model.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private long id;

    private String username;

    private Role role;

    private String email;



}
