package com.example.repository;

import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles(value = "integration")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;



    @Test
    public void itShouldFindUser_whenUsernameExists(){

        String username = "test-username";

        User expected  = User.builder()
                .username(username)
                .password("test-password")
                .build();

        userRepository.save(expected);
        User actual = userRepository.getByUsername(username).get();

        assertEquals(expected , actual);
        assertEquals(expected.getUsername() , actual.getUsername());


    }


}