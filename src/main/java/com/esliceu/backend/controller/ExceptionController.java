package com.esliceu.backend.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    Gson gson = new Gson();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> defaultException(Exception e){
        return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
