package com.beardtrust.webapp.userservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Controller advisor.
 */
@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	/**
	 * Handle duplicate entry exception response entity.
	 *
	 * @param e       DuplicateEntryException the exception encountered
	 * @param request WebRequest the web request
	 * @return ResponseEntity<Object> the response entity
	 */
	@ExceptionHandler(DuplicateEntryException.class)
	public ResponseEntity<Object> handleDuplicateEntryException(DuplicateEntryException e, WebRequest request) {
		log.warn(String.format("Encountered duplicate entry exception in %s", request.toString()));
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", e.getMessage());

		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.warn(String.format("Encountered method argument not valid exception in %s", request.toString()));

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());

		for (FieldError error : e.getFieldErrors()) {
			String name = error.getField();
			String message = error.getDefaultMessage();
			body.put(name, message);
		}

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}
