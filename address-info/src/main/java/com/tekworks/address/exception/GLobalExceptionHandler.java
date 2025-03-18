package com.tekworks.address.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GLobalExceptionHandler {
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
	        BindingResult bindingResult = ex.getBindingResult();
	        Map<String, String> errors = new HashMap<>();

	        bindingResult.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	    }
}
