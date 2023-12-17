package com.service.begateway.constant;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public interface Constant {

    List<String> AUTH_WHITELIST = List.of(
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-ui.html",
            "/verif/register",
            "/verif/login",
            "/verif/validateAccessToken",
            "/eureka",
            "/productReport/generateProductReport"
    );

    class Message {
        public static final String FORBIDDEN_MESSAGE = "You don't have access";
        public static final String INVALID_TOKEN_MESSAGE = "Invalid access token";
    }

    class DateFormatter {
        public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");
    }
}
