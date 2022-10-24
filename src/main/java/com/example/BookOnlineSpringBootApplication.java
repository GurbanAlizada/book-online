package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BookOnlineSpringBootApplication   {

    public static void main(String[] args) {
        SpringApplication.run(BookOnlineSpringBootApplication.class, args);
    }



    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();

    }


}


// Annotation Based code generation build time ı 2 katına çıkarıyor