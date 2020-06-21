package com.example.demo.web.rest.error;

import java.net.URI;

final class ErrorConstants {

    private static final String PROBLEM_BASE_URL = "http://localhost:8080/problem";
    static final String ERR_VALIDATION = "error.validation";
    static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");
    static final URI FILE_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/file-not-found");
    static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    static final URI ABBREVIATE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/abbreviate-already-used");
    static final URI FULLNAME_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/fullname-already-used");
    static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");
}
