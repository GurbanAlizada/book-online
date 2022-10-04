package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericException extends RuntimeException{

    private HttpStatus httpStatus;
    private ErrorCode errorCode;
    private String errorMessage;

    public GenericException(HttpStatus httpStatus , ErrorCode errorCode ){
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }


}
