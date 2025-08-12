package com.emakers.api_biblioteca.exceptions;

import com.emakers.api_biblioteca.DTOs.ErrorResponseDTO;
import com.emakers.api_biblioteca.DTOs.FieldErrorItem;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponseDTO> buildError(
        HttpStatus status,
        String message,
        HttpServletRequest request,
        List<FieldErrorItem> fieldErrors
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(status.value(),status.getReasonPhrase(),message,request.getRequestURI(),OffsetDateTime.now());
        error.setFieldErrors(fieldErrors);
        return ResponseEntity.status(status).body(error);
    }

    private ResponseEntity<ErrorResponseDTO> buildError(
        HttpStatus status,
        String message,
        HttpServletRequest request
    ) {
        return buildError(status, message, request, null);
    }


    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailJaCadastrado(
        EmailJaCadastradoException ex, HttpServletRequest request) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErrorResponseDTO> handleCredenciaisInvalidas(
        CredenciaisInvalidasException ex, HttpServletRequest request) {
        return buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(LivroNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleLivroNotFound(
        LivroNotFoundException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(PessoaNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePessoaNotFound(
        PessoaNotFoundException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(EmprestimoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmprestimoNotFound(
        EmprestimoNotFoundException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateResource(
        DuplicateResourceException ex, HttpServletRequest request) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(
        UnauthorizedActionException ex, HttpServletRequest request) {
        return buildError(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(
        ValidationException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<FieldErrorItem> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> new FieldErrorItem(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());
        return buildError(HttpStatus.BAD_REQUEST, "Dados de entrada inválidos.", request, fieldErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(
        ConstraintViolationException ex, HttpServletRequest request) {
        List<FieldErrorItem> fieldErrors = ex.getConstraintViolations().stream()
            .map(v -> new FieldErrorItem(v.getPropertyPath().toString(), v.getMessage()))
            .collect(Collectors.toList());
        return buildError(HttpStatus.BAD_REQUEST, "Parâmetros inválidos.", request, fieldErrors);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
        Exception ex, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,
            "Erro interno do servidor: " + ex.getMessage(), request);
    }
}
