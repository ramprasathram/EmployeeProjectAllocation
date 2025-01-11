package com.empproject.allocation.utils.exception;

import com.empproject.allocation.utils.Constants;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
@RestController
public class EmployeeProjectAllocationServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        log.error("EmployeeProjectAllocationServiceExceptionHandler:Exception {}", exception);
        ErrorMessage errorMessage = mapErrorResponse(ErrorCode.TECHNICAL_ERROR.getErrorCode(),
                ErrorCode.TECHNICAL_ERROR.getErrorDescription());
        errorMessage.setStatus(Constants.FAILURE);
        return ResponseEntity.status(ErrorCode.TECHNICAL_ERROR.getHttpStatus()).body(errorMessage);
    }


    @ExceptionHandler(EmployeeProjectServiceException.class)
    public ResponseEntity<ErrorMessage> handleException(EmployeeProjectServiceException exception) {
        log.error("EmployeeProjectAllocationServiceExceptionHandler:EmployeeProjectServiceException {}", exception);
        ErrorMessage errorMessage = mapErrorResponse(exception.getErrorCode(), exception.getErrorMessage());
        errorMessage.setStatus(Constants.FAILURE);
        return ResponseEntity.status(exception.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ErrorMessage> handleRestClientException(CannotCreateTransactionException ex) {
        log.error("EmployeeProjectAllocationServiceExceptionHandler:handleRestClientException {}", ex);
        ErrorMessage errorMessage = mapErrorResponse(ErrorCode.CONNECTIVITY_ERROR_DB.getErrorCode(),
                ErrorCode.CONNECTIVITY_ERROR_DB.getErrorDescription());
        errorMessage.setStatus(Constants.FAILURE);
        return ResponseEntity.status(ErrorCode.CONNECTIVITY_ERROR_DB.getHttpStatus()).body(errorMessage);

    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("EmployeeProjectAllocationServiceExceptionHandler:handleHttpMessageNotReadable(ENUM Validation Exception) : {}", ex);
        StringBuilder errorMessage = new StringBuilder();

        Throwable throwable = ex.getCause();
        JsonMappingException jsonMappingException = ((JsonMappingException) throwable);
        errorMessage.append(jsonMappingException.getOriginalMessage());

        ErrorMessage error = mapErrorResponse(ErrorCode.CONTRACT_VALIDATION_ERROR.getErrorCode(),
                errorMessage.toString());
        error.setStatus(Constants.FAILURE);
        return ResponseEntity.status(ErrorCode.CONTRACT_VALIDATION_ERROR.getHttpStatus()).body(error);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            org.springframework.web.bind.MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        log.error("EmployeeProjectAllocationServiceExceptionHandler:handleMethodArgumentNotValid(Validation Exception) : {}", ex);
        ErrorMessage errorMessage = new ErrorMessage();
        List<com.empproject.allocation.utils.exception.Error> errors = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            com.empproject.allocation.utils.exception.Error error = new com.empproject.allocation.utils.exception.Error();
            error.setCode(ErrorCode.CONTRACT_VALIDATION_ERROR.getErrorCode());
            error.setMessage(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            errors.add(error);
        }
        errorMessage.setErrors(errors);
        errorMessage.setStatus(Constants.FAILURE);
        return ResponseEntity.status(ErrorCode.CONTRACT_VALIDATION_ERROR.getHttpStatus()).body(errorMessage);
    }

    public ErrorMessage mapErrorResponse(String errorCode, String errorDetails) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<com.empproject.allocation.utils.exception.Error> errors = new ArrayList<>();
        com.empproject.allocation.utils.exception.Error error = new com.empproject.allocation.utils.exception.Error();
        error.setMessage(errorDetails);
        error.setCode(errorCode);
        errors.add(error);
        errorMessage.setErrors(errors);
        return errorMessage;
    }
}