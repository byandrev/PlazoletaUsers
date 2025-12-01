package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.exception.UserNotAdultException;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.ValidationError;
import com.pragma.powerup.infrastructure.input.rest.response.CustomResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<CustomResponse<Void>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        CustomResponse<Void> response = CustomResponse.<Void>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not found")
                .message(ignoredNoDataFoundException.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {

        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationError(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()
                ))
                .collect(Collectors.toList());

        CustomResponse<Void> response = CustomResponse.<Void>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message("Error de validación")
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomResponse<Void>> handleDataIntegrityViolationException() {
        CustomResponse<Void> response = CustomResponse.<Void>builder()
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message(ExceptionResponse.DUPLICATE_DATA.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UserNotAdultException.class)
    public ResponseEntity<CustomResponse<Void>> handleUserNotFoundException(UserNotAdultException ex) {
        CustomResponse<Void> response = CustomResponse.<Void>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public  ResponseEntity<CustomResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        CustomResponse<Void> response = CustomResponse.<Void>builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Access is denied")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}