package service;

import model.Expense;
import storage.FileManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ExpenseManager {
    private final FileManager fileManager;
    private final List<Expense> expenses;

    public ExpenseManager(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        this.expenses = new ArrayList<>(fileManager.loadExpenses());
    }

    public Expense addExpense(LocalDate date, String category,
                              String description, BigDecimal amount) throws IOException {
        int nextId = expenses.stream()
                .mapToInt(Expense::getId)
                .max()
                .orElse(0) + 1;

        Expense expense = new Expense(
                nextId, date, category, description, amount
        );

        expenses.add(expense);
        try {
            fileManager.saveExpenses(expenses);
        } catch (IOException ex) {
            expenses.remove(expenses.size() - 1);
            throw ex;
        }

        return expense;
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public List<Expense> search(String query) {
        String normalized = query.toLowerCase(Locale.ROOT);

        return expenses.stream()
                .filter(e ->
                        String.valueOf(e.getId()).equals(query)
                        || e.getDate().toString().contains(query)
                        || e.getCategory().toLowerCase(Locale.ROOT).contains(normalized)
                        || e.getDescription().toLowerCase(Locale.ROOT).contains(normalized)
                )
                .collect(Collectors.toList());
    }

    public boolean deleteExpense(int id) throws IOException {
        int index = -1;

        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == id) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false;
        }

        Expense removed = expenses.remove(index);

        try {
            fileManager.saveExpenses(expenses);
        } catch (IOException ex) {
            expenses.add(index, removed);
            throw ex;
        }

        return true;
    }

    public List<Expense> forMonth(java.time.YearMonth month) {
        return expenses.stream()
                .filter(e -> java.time.YearMonth.from(e.getDate()).equals(month))
                .collect(Collectors.toList());
    }

    public BigDecimal totalForMonth(java.time.YearMonth month) {
        return forMonth(month).stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Expense highestForMonth(java.time.YearMonth month) {
        return forMonth(month).stream()
                .max(Comparator.comparing(Expense::getAmount))
                .orElse(null);
    }

    public Expense lowestForMonth(java.time.YearMonth month) {
        return forMonth(month).stream()
                .min(Comparator.comparing(Expense::getAmount))
                .orElse(null);
    }
}
