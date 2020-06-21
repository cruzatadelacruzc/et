package com.example.demo.config;

import lombok.Value;

/**
 * Application constants.
 */
@Value
public class Constants {

    // Regex for acceptable login
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "es";
    public static final String ANONYMOUS_USER = "anonymoususer";
}
