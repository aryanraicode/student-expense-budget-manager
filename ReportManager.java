package service;

import model.Expense;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportManager {
    private final ExpenseManager expenseManager;
    private final BudgetManager budgetManager;

    public ReportManager(ExpenseManager expenseManager,
                         BudgetManager budgetManager) {
        this.expenseManager = expenseManager;
        this.budgetManager = budgetManager;
    }

    public String buildMonthlyReport(YearMonth month) {
        List<Expense> expenses = expenseManager.forMonth(month);
        BigDecimal total = expenseManager.totalForMonth(month);
        BigDecimal budget = budgetManager.getBudget(month);
        BigDecimal remaining = budgetManager.getRemaining(month, total);

        StringBuilder report = new StringBuilder();
        report.append("\n===== MONTHLY REPORT =====\n");
        report.append("Month: ").append(month).append("\n");
        report.append("Expense count: ").append(expenses.size()).append("\n");
        report.append("Monthly total: Rs. ").append(total.toPlainString()).append("\n");

        report.append("\nCategory totals:\n");
        Map<String, BigDecimal> categoryTotals = new LinkedHashMap<>();

        for (Expense expense : expenses) {
            categoryTotals.merge(
                    expense.getCategory(),
                    expense.getAmount(),
                    BigDecimal::add
            );
        }

        if (categoryTotals.isEmpty()) {
            report.append("No expenses recorded.\n");
        } else {
            for (Map.Entry<String, BigDecimal> entry : categoryTotals.entrySet()) {
                report.append("- ")
                        .append(entry.getKey())
                        .append(": Rs. ")
                        .append(entry.getValue().toPlainString())
                        .append("\n");
            }
        }

        Expense highest = expenseManager.highestForMonth(month);
        Expense lowest = expenseManager.lowestForMonth(month);

        report.append("\nHighest expense: ");
        report.append(highest == null ? "None" : highest.getDescription()
                + " (Rs. " + highest.getAmount().toPlainString() + ")\n");

        if (highest == null) {
            report.append("\n");
        }

        report.append("Lowest expense: ");
        report.append(lowest == null ? "None" : lowest.getDescription()
                + " (Rs. " + lowest.getAmount().toPlainString() + ")\n");

        if (budget == null) {
            report.append("\nBudget: Not set\n");
        } else {
            report.append("\nBudget: Rs. ")
                    .append(budget.toPlainString())
                    .append("\n");
            report.append("Remaining budget: Rs. ")
                    .append(remaining.toPlainString())
                    .append("\n");

            if (remaining.compareTo(BigDecimal.ZERO) < 0) {
                report.append("WARNING: Budget exceeded by Rs. ")
                        .append(remaining.abs().toPlainString())
                        .append("\n");
            } else {
                report.append("Status: Within budget\n");
            }
        }

        report.append("==========================\n");
        return report.toString();
    }
}
