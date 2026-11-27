package com.ritik.razorpay.common.exception;

public class BusinessRuleViolationExcepetion extends RuntimeException {

    public final String errorCode;

    public BusinessRuleViolationExcepetion(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
