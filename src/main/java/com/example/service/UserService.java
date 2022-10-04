package com.example.service;

import com.example.dtos.UserDto;
import com.example.dtos.UserRequest;
import com.example.exception.ErrorCode;
import com.example.exception.GenericException;
import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDto createUser(UserRequest request){

        User user  = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .role(request.getRole())
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        UserDto result = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .build();

        return result;
    }



    public User findByUserName(String username){
        var fromDb = userRepository.getByUsername(username).orElseThrow(()-> new GenericException(HttpStatus.NOT_FOUND , ErrorCode.USER_NOT_FOUNDED , "User not found by given id"));
        return fromDb;
    }


    public UserDto getUserDto(String username) {

        var user = findByUserName(username);
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }


}
