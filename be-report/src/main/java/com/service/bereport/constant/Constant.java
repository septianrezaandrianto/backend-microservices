package com.service.bereport.constant;

import java.text.SimpleDateFormat;
import java.util.Locale;

public interface Constant {

    class DateFormatter {
        public static final SimpleDateFormat DISPLAY_DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
        public static final SimpleDateFormat FULL_DISPLAY_DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        public static final SimpleDateFormat FULL_DATE_FORMATTER = new SimpleDateFormat("dd MMMM yyyy",  new Locale("id", "ID"));
        public static final SimpleDateFormat DB_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    }

}
