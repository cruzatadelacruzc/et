package com.example.bets.service.error;


public class MoneyNoSufficientException extends RuntimeException {

    public MoneyNoSufficientException() {
        super("Better not has sufficient money to bet!");
    }
}
