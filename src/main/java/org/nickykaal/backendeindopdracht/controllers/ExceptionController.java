package org.nickykaal.backendeindopdracht.controllers;

import jakarta.validation.ConstraintViolationException;
import org.nickykaal.backendeindopdracht.dtos.ValidationExceptionDto;
import org.nickykaal.backendeindopdracht.exceptions.ResourceNotFoundException;
import org.nickykaal.backendeindopdracht.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity <String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <Map<String,String>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
            .getFieldErrors()
            .forEach((e)-> errors.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity <Map<String,String>> constraintException(ConstraintViolationException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations()
            .forEach((e)-> errors.put(e.getPropertyPath().toString(), e.getMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity <String> illegalArgumentException(IllegalArgumentException ex){

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity <ValidationExceptionDto> validationException(ValidationException ex){

        ValidationExceptionDto responseDto = new ValidationExceptionDto( ex.getErrors());

        return ResponseEntity.badRequest().body( responseDto);
    }
}
