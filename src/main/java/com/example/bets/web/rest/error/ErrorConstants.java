package com.example.bets.web.rest.error;

import java.net.URI;

final class ErrorConstants {

    private static final String PROBLEM_BASE_URL = "http://localhost:8080/problem";
    static final String ERR_VALIDATION = "error.validation";
    static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");
    static final URI EMPTY_GOLFERS_TYPE = URI.create(PROBLEM_BASE_URL + "/list-empty-golfer");
    static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
}
