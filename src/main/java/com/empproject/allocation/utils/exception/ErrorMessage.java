
package com.empproject.allocation.utils.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorMessage {

    private String status;

    private List<com.empproject.allocation.utils.exception.Error> errors = new ArrayList<com.empproject.allocation.utils.exception.Error>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorMessage withStatus(String status) {
        this.status = status;
        return this;
    }

    public List<com.empproject.allocation.utils.exception.Error> getErrors() {
        return errors;
    }

    public void setErrors(List<com.empproject.allocation.utils.exception.Error> errors) {
        this.errors = errors;
    }

    public ErrorMessage withErrors(List<com.empproject.allocation.utils.exception.Error> errors) {
        this.errors = errors;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return status.equals(that.status) && errors.equals(that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, errors);
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "status='" + status + '\'' +
                ", errors=" + errors +
                '}';
    }
}
