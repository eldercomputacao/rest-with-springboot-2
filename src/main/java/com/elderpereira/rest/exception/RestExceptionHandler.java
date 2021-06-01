package com.elderpereira.rest.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestExceptionDetails(BadRequestException ex){
		return new ResponseEntity<>(
				BadRequestExceptionDetails
					.builder()
					.title("Bad Request Exception, Check the Documentation")
					.timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .details(ex.getMessage())
                    .developerMessage(ex.getClass().getName())
					.build(), 
				HttpStatus.BAD_REQUEST
		);
	}
	
}
