package com.empproject.allocation.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmployeeProjectServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private String errorCode;

    private String errorMessage;

    private int httpStatus;

    public EmployeeProjectServiceException(ErrorCode errorCode, String errorMessage) {
        super(errorCode.getErrorCode() + errorCode.getErrorDescription() + errorMessage);
        this.errorCode = errorCode.getErrorCode();
        this.errorMessage = errorCode.getErrorDescription() + errorMessage;
        this.httpStatus = errorCode.getHttpStatus();
    }

    public EmployeeProjectServiceException(ErrorCode errorCode) {
        super(errorCode.getErrorCode() + errorCode.getErrorDescription());
        this.errorCode = errorCode.getErrorCode();
        this.errorMessage = errorCode.getErrorDescription();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public EmployeeProjectServiceException(Throwable e, ErrorCode errorCode) {
        super("Fallback " + errorCode.getErrorCode() + errorCode.getErrorDescription());
        log.error("Fallback ", e);
        this.errorCode = errorCode.getErrorCode();
        this.errorMessage = errorCode.getErrorDescription();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
