import model.Expense;
import service.BudgetManager;
import service.ExpenseManager;
import service.ReportManager;
import storage.FileManager;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) throws Exception {
        Path tempDirectory = Files.createTempDirectory("student-expense-budget-test-");

        try {
            FileManager fileManager = new FileManager(tempDirectory);
            fileManager.initialize();

            ExpenseManager expenseManager = new ExpenseManager(fileManager);
            BudgetManager budgetManager = new BudgetManager(fileManager);
            ReportManager reportManager =
                    new ReportManager(expenseManager, budgetManager);

            YearMonth month = YearMonth.of(2026, 9);

            test("Initial expense list is empty",
                    expenseManager.getAllExpenses().isEmpty());

            Expense first = expenseManager.addExpense(
                    LocalDate.of(2026, 9, 17),
                    "Food",
                    "Lunch",
                    new BigDecimal("125.50")
            );

            test("Expense receives ID 1", first.getId() == 1);
            test("Expense amount is exact",
                    first.getAmount().compareTo(new BigDecimal("125.50")) == 0);

            Expense second = expenseManager.addExpense(
                    LocalDate.of(2026, 9, 18),
                    "Travel",
                    "Bus",
                    new BigDecimal("50.00")
            );

            test("Second expense receives ID 2", second.getId() == 2);

            test("Month total is 175.50",
                    expenseManager.totalForMonth(month)
                            .compareTo(new BigDecimal("175.50")) == 0);

            List<Expense> foodResults = expenseManager.search("food");
            test("Search is case-insensitive", foodResults.size() == 1);

            budgetManager.setBudget(month, new BigDecimal("1000.00"));

            test("Budget is saved",
                    budgetManager.getBudget(month)
                            .compareTo(new BigDecimal("1000.00")) == 0);

            BigDecimal remaining = budgetManager.getRemaining(
                    month,
                    expenseManager.totalForMonth(month)
            );

            test("Remaining budget is 824.50",
                    remaining.compareTo(new BigDecimal("824.50")) == 0);

            String report = reportManager.buildMonthlyReport(month);

            test("Report contains monthly total",
                    report.contains("175.50"));

            test("Report contains category totals",
                    report.contains("Food") && report.contains("Travel"));

            test("Delete expense works",
                    expenseManager.deleteExpense(2));

            test("Total after deletion is 125.50",
                    expenseManager.totalForMonth(month)
                            .compareTo(new BigDecimal("125.50")) == 0);

            // Reload managers to verify persistence.
            ExpenseManager reloadedExpenses = new ExpenseManager(fileManager);
            BudgetManager reloadedBudgets = new BudgetManager(fileManager);

            test("Expense data persists",
                    reloadedExpenses.getAllExpenses().size() == 1);

            test("Budget data persists",
                    reloadedBudgets.getBudget(month)
                            .compareTo(new BigDecimal("1000.00")) == 0);

            System.out.println();
            System.out.println("Tests passed: " + passed);
            System.out.println("Tests failed: " + failed);

            if (failed > 0) {
                throw new AssertionError("Some tests failed.");
            }

            System.out.println("ALL TESTS PASSED");
        } finally {
            deleteRecursively(tempDirectory);
        }
    }

    private static void test(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    private static void deleteRecursively(Path path) throws Exception {
        if (!Files.exists(path)) {
            return;
        }

        try (java.util.stream.Stream<Path> stream = Files.walk(path)) {
            stream.sorted(java.util.Comparator.reverseOrder())
                    .forEach(p -> {
                        try {
                            Files.deleteIfExists(p);
                        } catch (Exception ignored) {
                        }
                    });
        }
    }
}
