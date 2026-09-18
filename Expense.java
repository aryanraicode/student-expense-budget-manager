package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
    private final int id;
    private final LocalDate date;
    private final String category;
    private final String description;
    private final BigDecimal amount;

    public Expense(int id, LocalDate date, String category,
                   String description, BigDecimal amount) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String toCsv() {
        return id + "," + date + "," + escape(category) + ","
                + escape(description) + "," + amount.toPlainString();
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace(",", "\\,")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    public static Expense fromCsv(String line) {
        String[] parts = splitEscapedCsv(line);
        if (parts.length != 5) {
            throw new IllegalArgumentException("Malformed expense record");
        }

        int id = Integer.parseInt(parts[0]);
        LocalDate date = LocalDate.parse(parts[1]);
        String category = unescape(parts[2]);
        String description = unescape(parts[3]);
        BigDecimal amount = new BigDecimal(parts[4]);

        return new Expense(id, date, category, description, amount);
    }

    private static String unescape(String value) {
        StringBuilder result = new StringBuilder();
        boolean escaped = false;

        for (char c : value.toCharArray()) {
            if (escaped) {
                if (c == 'n') {
                    result.append('\n');
                } else {
                    result.append(c);
                }
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else {
                result.append(c);
            }
        }

        if (escaped) {
            result.append('\\');
        }

        return result.toString();
    }

    private static String[] splitEscapedCsv(String line) {
        java.util.List<String> result = new java.util.ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean escaped = false;

        for (char c : line.toCharArray()) {
            if (escaped) {
                current.append('\\').append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else if (c == ',') {
                result.add(unescape(current.toString()));
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        if (escaped) {
            current.append('\\');
        }

        result.add(unescape(current.toString()));
        return result.toArray(new String[0]);
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Date: %s | Category: %s | Description: %s | Amount: Rs. %s",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                category,
                description,
                amount.toPlainString()
        );
    }
}
