package com.company.lendingbackend.exception;

public class HighRiskException extends Exception {

    private static final long serialVersionUID = -4601140577409425685L;

    public HighRiskException(String message) {
        super(message);
    }
}
