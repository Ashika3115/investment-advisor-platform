package com.investment.common.exception.custom;

public class InvestorDetailsIncompleteException extends RuntimeException {
    public InvestorDetailsIncompleteException(String message) {
        super(message);
    }
}
