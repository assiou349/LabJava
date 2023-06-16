package com.labjava.skillguest.api.utils.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class, MyBadRequestException.class })
    protected final ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        logger.info("Bad Request: " + ex.getLocalizedMessage());
        logger.debug("Bad Request: ", ex);

        final ApiError apiError = message(HttpStatus.BAD_REQUEST, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 403

    @ExceptionHandler({ AccessDeniedException.class })
    protected ResponseEntity<Object> handleEverything(final AccessDeniedException ex, final WebRequest request) {
        logger.error("403 Status Code", ex);

        final ApiError apiError = message(HttpStatus.FORBIDDEN, ex);

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    // 404

    @ExceptionHandler({ EntityNotFoundException.class, MyResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        logger.warn("Not Found: " + ex.getMessage());

        final ApiError apiError = message(HttpStatus.NOT_FOUND, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409

    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, MyConflictException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        logger.warn("Conflict: " + ex.getMessage());

        final ApiError apiError = message(HttpStatus.CONFLICT, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 415

    @ExceptionHandler({ InvalidMimeTypeException.class, InvalidMediaTypeException.class })
    protected ResponseEntity<Object> handleInvalidMimeTypeException(final IllegalArgumentException ex, final WebRequest request) {
        logger.warn("Unsupported Media Type: " + ex.getMessage());

        final ApiError apiError = message(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }

    // 500

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handle500s(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);

        final ApiError apiError = message(HttpStatus.INTERNAL_SERVER_ERROR, ex);

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }



    private ApiError message(final HttpStatus httpStatus, final Exception ex) {
        final String message = ex.getMessage() == null ? ex.getClass()
                .getSimpleName() : ex.getMessage();
        final String devMessage = ex.getClass()
                .getSimpleName();
        // devMessage = ExceptionUtils.getStackTrace(ex);

        return new ApiError(httpStatus.value(), message, devMessage);
    }

}
