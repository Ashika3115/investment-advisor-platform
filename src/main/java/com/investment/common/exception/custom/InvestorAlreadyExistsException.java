package com.investment.common.exception.custom;

public class InvestorAlreadyExistsException extends RuntimeException {
    public InvestorAlreadyExistsException(String message) {
        super(message);
    }
}