package util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

public final class InputValidator {
    private InputValidator() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid date. Use YYYY-MM-DD.");
        }
    }

    public static YearMonth parseMonth(String value) {
        try {
            return YearMonth.parse(value.trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid month. Use YYYY-MM.");
        }
    }

    public static BigDecimal parsePositiveAmount(String value) {
        try {
            BigDecimal amount = new BigDecimal(value.trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
            return amount;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Enter a valid numeric amount.");
        }
    }

    public static int parsePositiveId(String value) {
        try {
            int id = Integer.parseInt(value.trim());
            if (id <= 0) {
                throw new IllegalArgumentException("ID must be positive.");
            }
            return id;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Enter a valid numeric ID.");
        }
    }

    public static String requireText(String value, String fieldName) {
        if (isBlank(value)) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
        return value.trim();
    }
}
