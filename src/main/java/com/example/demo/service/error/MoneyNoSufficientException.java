package com.example.demo.service.error;

import com.example.demo.web.rest.error.BadRequestAlertException;

public class MoneyNoSufficientException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public MoneyNoSufficientException() {
        super("Better not has sufficient money to bet!", "forecastManagement", "nomoney");
    }
}
