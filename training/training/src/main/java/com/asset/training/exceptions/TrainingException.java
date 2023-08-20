package com.asset.training.exceptions;

import lombok.Data;

@Data
public class TrainingException extends RuntimeException {
    protected int errorCode;
    protected int errorSeverity;
    protected String args;

    public TrainingException(int errorCode, int errorSeverity, String args) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
        this.args = args;
    }

    public TrainingException(int errorCode, String args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public TrainingException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }
}
