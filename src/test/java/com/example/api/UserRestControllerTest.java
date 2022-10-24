package com.example.api;

import com.example.dtos.UserDto;
import com.example.dtos.UserRequest;
import com.example.model.Role;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = "integration")
class UserRestControllerTest {

    @MockBean
    private UserService userService;


    @Autowired
    MockMvc mockMvc ;


    private ObjectMapper objectMapper = new ObjectMapper();



    @Test
    @WithMockUser(value = "spring")
    public void testSave_whenGivenValidUserRequest_itShouldReturnUserDto() throws Exception {

        UserRequest request = UserRequest.builder()
                .role(Role.USER)
                .email("test-email")
                .password("test-password")
                .username("test-username")
                .build();

        UserDto response = UserDto.builder()
                .id(1L)
                .role(request.getRole())
                .username(request.getUsername())
                .email(request.getEmail())
                .build();

        when(userService.createUser(request)).thenReturn(response);

        mockMvc
                .perform(post("/api/v1.0/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(request)))
                .andDo(print())
                .andExpect(jsonPath("$.username").value(request.getUsername()))
                .andExpect(status().isOk());

    }




    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }




}