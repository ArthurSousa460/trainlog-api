package com.trainlog.exceptions;

import com.trainlog.dto.ResponseErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> handleResourceNotFoundException(
            ResourceNotFoundException exception
    ){
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status)
                .body(
                    new ResponseErrorDTO(
                        status.value(),
                        status.getReasonPhrase(),
                        exception.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(BusinessException.class)
    public  ResponseEntity<ResponseErrorDTO> handleBusinessException(
            BusinessException exception
    ){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(new ResponseErrorDTO(
                        status.value(),
                        status.getReasonPhrase(),
                        exception.getMessage(),
                        Instant.now()
                ));
    }
}
