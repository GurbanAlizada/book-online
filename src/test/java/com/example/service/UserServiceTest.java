package com.example.service;

import com.example.dtos.UserDto;
import com.example.dtos.UserRequest;
import com.example.exception.ErrorCode;
import com.example.exception.GenericException;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = "integration")
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository , passwordEncoder);
    }


    @Test
    public void itShouldCreateUser(){

        UserRequest userRequest = UserRequest.builder()
                .username("test-username")
                .password("test-password")
                .email("test-email")
                .role(Role.USER)
                .build();

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .build();


//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        when(userRepository.save(user)).thenReturn(user);


        UserDto userDto  = UserDto.builder()
                .id(0)
                .username(user.getUsername())
                .role(user.getRole())
                .email(user.getEmail())
                .build();

        UserDto actual = userService.createUser(userRequest);


        assertEquals(userDto , actual);


    }


    @Test
    public void itShouldReturnUserByUsername_whenUserExists(){

        String username = "test-username";

        User expected = User.builder()
                .username(username)
                .password("test-password")
                .build();

        when(userRepository.getByUsername(username)).thenReturn(Optional.of(expected));

        User actual = userService.findByUserName(username);

        assertEquals(expected , actual);
        verify(userRepository , times(1)).getByUsername(username);

    }




    @Test
    public void itShouldThrowException_whenUsernameDoesNotExists(){


        GenericException expectedError =  new GenericException(
                HttpStatus.NOT_FOUND ,
                ErrorCode.USER_NOT_FOUNDED ,
                "User not found by given id");

        when(userRepository.getByUsername(Mockito.anyString())).thenReturn(Optional.empty());

        GenericException actual = assertThrows(GenericException.class, () -> userService.findByUserName(Mockito.anyString()));

        verify(userRepository , times(1)).getByUsername(Mockito.anyString());
        assertEquals(expectedError.getErrorMessage() , actual.getErrorMessage());

    }




    @Test
    public void itShouldReturnUserDtoByUsername_whenUserExists(){

        String username = "test-username";

        User user  = User.builder()
                .username(username)
                .password("test-password")
                .build();

        UserDto expected = UserDto.builder()
                .username(user.getUsername())
                .build();



        when(userService.findByUserName(username)).thenReturn(user);
        //when(userRepository.getByUsername(username)).thenReturn(Optional.of(user));


        UserDto actual = userService.getUserDto(username);

        assertEquals(expected , actual);
      //  verify(userRepository , times(1)).getByUsername(username);

    }



}