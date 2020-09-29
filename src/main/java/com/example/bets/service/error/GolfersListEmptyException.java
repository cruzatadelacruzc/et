package com.example.bets.service.error;


public class GolfersListEmptyException extends RuntimeException {


    public GolfersListEmptyException() {
        super("Golfers list is empty!");
    }
}
