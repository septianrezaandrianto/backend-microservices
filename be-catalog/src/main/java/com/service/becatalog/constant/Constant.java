package com.service.becatalog.constant;

import java.text.SimpleDateFormat;
import java.util.Locale;

public interface Constant {

    class Response {
        public static final int SUCCESS_CODE = 200;
        public static final String SUCCESS_MESSAGE = "Success";
    }

    class Message {
        public static final String EXIST_DATA_MESSAGE = "data already exist";
        public static final String NOT_FOUND_DATA_MESSAGE = "data not found";
        public static final String FORBIDDEN_REQUEST_MESSAGE = "Different {value} with exist data is forbidden";
    }

    class DateFormatter {
        public static final SimpleDateFormat DISPLAY_DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
        public static final SimpleDateFormat FULL_DISPLAY_DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        public static final SimpleDateFormat FULL_DATE_FORMATTER = new SimpleDateFormat("dd MMMM yyyy",  new Locale("id", "ID"));
    }

}
