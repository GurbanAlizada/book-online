package com.example.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TokenResponseDto {

    private Map<String ,String> tokens;

    private UserDto userDto;


}
