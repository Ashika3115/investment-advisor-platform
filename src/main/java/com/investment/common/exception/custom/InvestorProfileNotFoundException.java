package com.investment.common.exception.custom;

public class InvestorProfileNotFoundException extends RuntimeException {

    public InvestorProfileNotFoundException(String message) {
        super(message);
    }

}
