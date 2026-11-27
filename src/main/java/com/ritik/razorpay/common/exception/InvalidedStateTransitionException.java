package com.ritik.razorpay.common.exception;

public class InvalidedStateTransitionException extends RuntimeException {

    private final String fromState;
    private final String toevent;
    public InvalidedStateTransitionException(String fromState, String event) {
        super("Invalid state transition from " + fromState + " to " + event);
        this.fromState = fromState;
        this.toevent = event;
    }
}
