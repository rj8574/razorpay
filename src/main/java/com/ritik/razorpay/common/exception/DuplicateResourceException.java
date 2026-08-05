package com.ritik.razorpay.common.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException{

    public final String errorCode;

    public DuplicateResourceException(String errorCode,String message){
        super(message);
        this.errorCode = errorCode;

    }
}
