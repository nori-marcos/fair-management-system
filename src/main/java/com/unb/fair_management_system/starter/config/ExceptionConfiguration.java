package com.unb.fair_management_system.starter.config;

import static java.util.Objects.isNull;

import jakarta.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionConfiguration {
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Void> handle(final AccessDeniedException exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handle(final ConstraintViolationException exception) {
    final var error =
        exception.getConstraintViolations().stream()
            .map(
                violation ->
                    "%s: %s".formatted(violation.getPropertyPath(), violation.getMessage()))
            .toList()
            .toString();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handle(final HttpMessageNotReadableException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handle(final MethodArgumentNotValidException exception) {
    final var fieldErrors =
        exception.getFieldErrors().stream()
            .map(
                error ->
                    "%s: %s"
                        .formatted(
                            error.getField(),
                            "typeMismatch".equals(error.getCode())
                                ? "must be valid"
                                : error.getDefaultMessage()));

    final var globalErrors =
        exception.getGlobalErrors().stream()
            .map(error -> "%s: %s".formatted(error.getObjectName(), error.getDefaultMessage()));

    final var errors = Stream.concat(fieldErrors, globalErrors).sorted().toList();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handle(final MethodArgumentTypeMismatchException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("[%s: must be valid]".formatted(exception.getPropertyName()));
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Void> handle(final NoSuchElementException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<String> handle(final ResponseStatusException exception) {
    if (isNull(exception.getCause())) {
      return ResponseEntity.status(exception.getStatusCode()).body(exception.getReason());
    }

    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(HttpStatus.valueOf(exception.getStatusCode().value())).build();
  }

  @ExceptionHandler(NoResourceFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleNoResourceFoundException(
      final NoResourceFoundException exception, final Model model) {
    log.warn("Resource not found for request: {}", exception.getResourcePath());
    model.addAttribute("statusCode", HttpStatus.NOT_FOUND.value());
    model.addAttribute("errorMessage", "A página que você está procurando não foi encontrada.");
    model.addAttribute("isForbiddenError", false);
    model.addAttribute("contentFragment", "custom-error");
    return "layout";
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handle(final Exception exception) {
    log.error(exception.getMessage(), exception);
    final String errorMessage =
        exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
  }
}
