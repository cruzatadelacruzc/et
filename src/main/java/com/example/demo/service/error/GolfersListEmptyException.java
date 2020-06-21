package com.example.demo.service.error;

import com.example.demo.web.rest.error.BadRequestAlertException;

public class GolfersListEmptyException extends BadRequestAlertException {


    public GolfersListEmptyException() {
        super("Golfers list is empty!", "bet.TopFive", "golfers_empty");
    }
}
