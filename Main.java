import model.Expense;
import service.BudgetManager;
import service.ExpenseManager;
import service.ReportManager;
import storage.FileManager;
import util.InputValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner;
    private final ExpenseManager expenseManager;
    private final BudgetManager budgetManager;
    private final ReportManager reportManager;

    public Main(Path dataDirectory) throws IOException {
        FileManager fileManager = new FileManager(dataDirectory);
        fileManager.initialize();

        this.scanner = new Scanner(System.in);
        this.expenseManager = new ExpenseManager(fileManager);
        this.budgetManager = new BudgetManager(fileManager);
        this.reportManager = new ReportManager(expenseManager, budgetManager);
    }

    public static void main(String[] args) {
        Path dataDirectory = args.length > 0
                ? Paths.get(args[0])
                : Paths.get("data");

        try {
            new Main(dataDirectory).run();
        } catch (IOException ex) {
            System.out.println("Unable to start application: " + ex.getMessage());
        }
    }

    private void run() {
        System.out.println("======================================");
        System.out.println("   STUDENT EXPENSE & BUDGET MANAGER");
        System.out.println("======================================");

        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        addExpense();
                        break;
                    case "2":
                        viewExpenses();
                        break;
                    case "3":
                        searchExpenses();
                        break;
                    case "4":
                        deleteExpense();
                        break;
                    case "5":
                        setBudget();
                        break;
                    case "6":
                        viewBudget();
                        break;
                    case "7":
                        viewReport();
                        break;
                    case "8":
                        running = false;
                        System.out.println("Exiting. Thank you.");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose 1-8.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Input error: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("Storage error: " + ex.getMessage());
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n----------- MENU -----------");
        System.out.println("1. Add expense");
        System.out.println("2. View expenses");
        System.out.println("3. Search expenses");
        System.out.println("4. Delete expense");
        System.out.println("5. Set monthly budget");
        System.out.println("6. View monthly budget");
        System.out.println("7. View monthly report");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    private void addExpense() throws IOException {
        System.out.println("\n--- Add Expense ---");

        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = InputValidator.parseDate(scanner.nextLine());

        System.out.print("Category: ");
        String category = InputValidator.requireText(
                scanner.nextLine(), "Category"
        );

        System.out.print("Description: ");
        String description = InputValidator.requireText(
                scanner.nextLine(), "Description"
        );

        System.out.print("Amount: ");
        BigDecimal amount = InputValidator.parsePositiveAmount(
                scanner.nextLine()
        );

        Expense expense = expenseManager.addExpense(
                date, category, description, amount
        );

        System.out.println("Expense added successfully.");
        System.out.println(expense);
    }

    private void viewExpenses() {
        System.out.println("\n--- All Expenses ---");
        List<Expense> expenses = expenseManager.getAllExpenses();

        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    private void searchExpenses() {
        System.out.print("\nSearch by ID, date, category or description: ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            throw new IllegalArgumentException("Search text cannot be blank.");
        }

        List<Expense> results = expenseManager.search(query);

        if (results.isEmpty()) {
            System.out.println("No matching expenses found.");
            return;
        }

        System.out.println("Matching expenses:");
        for (Expense expense : results) {
            System.out.println(expense);
        }
    }

    private void deleteExpense() throws IOException {
        System.out.print("\nEnter expense ID to delete: ");
        int id = InputValidator.parsePositiveId(scanner.nextLine());

        if (expenseManager.deleteExpense(id)) {
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Expense ID not found.");
        }
    }

    private void setBudget() throws IOException {
        System.out.println("\n--- Set Monthly Budget ---");

        YearMonth month = readMonthAllowBlank();

        System.out.print("Budget amount: ");
        BigDecimal amount = InputValidator.parsePositiveAmount(
                scanner.nextLine()
        );

        budgetManager.setBudget(month, amount);
        System.out.println("Budget saved for " + month + ".");
    }

    private void viewBudget() {
        System.out.println("\n--- View Monthly Budget ---");

        YearMonth month = readMonthAllowBlank();
        BigDecimal budget = budgetManager.getBudget(month);
        BigDecimal spending = expenseManager.totalForMonth(month);

        if (budget == null) {
            System.out.println("No budget set for " + month + ".");
            return;
        }

        BigDecimal remaining = budget.subtract(spending);

        System.out.println("Month: " + month);
        System.out.println("Budget: Rs. " + budget.toPlainString());
        System.out.println("Spending: Rs. " + spending.toPlainString());
        System.out.println("Remaining: Rs. " + remaining.toPlainString());

        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("WARNING: Monthly budget exceeded.");
        }
    }

    private void viewReport() {
        System.out.println(reportManager.buildMonthlyReport(
                readMonthAllowBlank()
        ));
    }

    private YearMonth readMonthAllowBlank() {
        System.out.print("Month (YYYY-MM, blank for current month): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return YearMonth.now();
        }

        return InputValidator.parseMonth(input);
    }
}
