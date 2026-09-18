package storage;

import model.Expense;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class FileManager {
    private final Path dataDirectory;
    private final Path expenseFile;
    private final Path budgetFile;

    public FileManager(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
        this.expenseFile = dataDirectory.resolve("expenses.csv");
        this.budgetFile = dataDirectory.resolve("budget.txt");
    }

    public void initialize() throws IOException {
        Files.createDirectories(dataDirectory);
        if (!Files.exists(expenseFile)) {
            Files.createFile(expenseFile);
        }
        if (!Files.exists(budgetFile)) {
            Files.createFile(budgetFile);
        }
    }

    public List<Expense> loadExpenses() throws IOException {
        List<Expense> expenses = new ArrayList<>();

        if (!Files.exists(expenseFile)) {
            return expenses;
        }

        for (String line : Files.readAllLines(expenseFile, StandardCharsets.UTF_8)) {
            if (line.trim().isEmpty()) {
                continue;
            }
            try {
                expenses.add(Expense.fromCsv(line));
            } catch (RuntimeException ex) {
                throw new IOException("Malformed expense file. Repair the file before continuing.", ex);
            }
        }

        return expenses;
    }

    public void saveExpenses(List<Expense> expenses) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Expense expense : expenses) {
            lines.add(expense.toCsv());
        }
        atomicWrite(expenseFile, lines);
    }

    public Map<String, BigDecimal> loadBudgets() throws IOException {
        Map<String, BigDecimal> budgets = new LinkedHashMap<>();

        if (!Files.exists(budgetFile)) {
            return budgets;
        }

        for (String line : Files.readAllLines(budgetFile, StandardCharsets.UTF_8)) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split("=", 2);
            if (parts.length != 2) {
                throw new IOException("Malformed budget file. Repair the file before continuing.");
            }

            try {
                budgets.put(parts[0].trim(), new BigDecimal(parts[1].trim()));
            } catch (RuntimeException ex) {
                throw new IOException("Malformed budget file. Repair the file before continuing.", ex);
            }
        }

        return budgets;
    }

    public void saveBudgets(Map<String, BigDecimal> budgets) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> entry : budgets.entrySet()) {
            lines.add(entry.getKey() + "=" + entry.getValue().toPlainString());
        }

        atomicWrite(budgetFile, lines);
    }

    private void atomicWrite(Path target, List<String> lines) throws IOException {
        Files.createDirectories(dataDirectory);

        Path temporary = Files.createTempFile(
                dataDirectory,
                target.getFileName().toString(),
                ".tmp"
        );

        try {
            Files.write(
                    temporary,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            try {
                Files.move(
                        temporary,
                        target,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE
                );
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(
                        temporary,
                        target,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
        } finally {
            Files.deleteIfExists(temporary);
        }
    }
}
