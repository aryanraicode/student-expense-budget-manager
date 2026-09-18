package service;

import storage.FileManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;

public class BudgetManager {
    private final FileManager fileManager;
    private final Map<String, BigDecimal> budgets;

    public BudgetManager(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        this.budgets = new LinkedHashMap<>(fileManager.loadBudgets());
    }

    public void setBudget(YearMonth month, BigDecimal amount) throws IOException {
        String key = month.toString();
        BigDecimal previous = budgets.put(key, amount);

        try {
            fileManager.saveBudgets(budgets);
        } catch (IOException ex) {
            if (previous == null) {
                budgets.remove(key);
            } else {
                budgets.put(key, previous);
            }
            throw ex;
        }
    }

    public BigDecimal getBudget(YearMonth month) {
        return budgets.get(month.toString());
    }

    public boolean hasBudget(YearMonth month) {
        return budgets.containsKey(month.toString());
    }

    public BigDecimal getRemaining(YearMonth month, BigDecimal spending) {
        BigDecimal budget = getBudget(month);
        if (budget == null) {
            return null;
        }
        return budget.subtract(spending);
    }
}
