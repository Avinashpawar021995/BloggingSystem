package com.org.Blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.org.Blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobelExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)

	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(
			ResourceNotFoundException resourceNotFoundException) {
		String massege = resourceNotFoundException.getMessage();
		ApiResponse apiResponse = new ApiResponse(massege, false);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> responseErr = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String massage = error.getDefaultMessage();
			responseErr.put(fieldName, massage);
		});
		return new ResponseEntity<Map<String, String>>(responseErr, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ApiException.class)

	public ResponseEntity<ApiResponse> handleApiException(ApiException resourceNotFoundException) {
		String massege = resourceNotFoundException.getMessage();
		ApiResponse apiResponse = new ApiResponse(massege, true);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

}
