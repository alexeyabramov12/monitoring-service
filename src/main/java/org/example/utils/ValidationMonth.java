package org.example.utils;

import org.example.exception.reading.ReadingException;

public class ValidationMonth {

    public static int checkMonth(String input) {
        String errorMessage = "ОШИБКА: введен не правильный месяц";
        if (input.isEmpty() || !input.matches("[0-9]+")) {
            throw new ReadingException(errorMessage);
        }

        int month = Integer.parseInt(input);

        if (month > 12 || month <= 0) {
            throw new ReadingException(errorMessage);
        }

        return month;
    }
}
