package com.empproject.allocation.utils.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    CONNECTIVITY_ERROR_DB("CONNECTIVITY_ERROR_DB", "Not able to connect to Database",
            HttpStatus.SERVICE_UNAVAILABLE.value()),

    CONTRACT_VALIDATION_ERROR("CONTRACT_VALIDATION_ERROR", "",
            HttpStatus.BAD_REQUEST.value()),
    TECHNICAL_ERROR("TECHNICAL_ERROR",
            "Unable to process request",
            HttpStatus.INTERNAL_SERVER_ERROR.value()),
    UNAUTHORIZED("UNAUTHORIZED", "Invalid Credentials for Websec", HttpStatus.UNAUTHORIZED.value());

    private String errorCode;

    private String errorDescription;

    private int httpStatus;

    public int getHttpStatus() {
        return httpStatus;
    }

    ErrorCode(String errorCode, String errorDescription, int httpStatus) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    @Override
    public String toString() {
        return "ErrorCode{" + "errorCode=" + errorCode + ", errorDescription='" + errorDescription + '\''
                + ", httpStatus='" + httpStatus + '\'' + '}';
    }

}
