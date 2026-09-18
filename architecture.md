# System Architecture

The application separates terminal interaction, business responsibilities and local persistence.

## Logical Components

```text
Student / Terminal
        |
        v
      Main
        |
   +----+-------------------+
   |        |        |       |
   v        v        v       v
Expense   Budget   Report  Input
Manager   Manager  Manager  Validator
   |        |        |
   +--------+--------+
            |
            v
       FileManager
        /       \
       v         v
expenses.csv   budget.txt
```

## Responsibilities

### Main
Provides the numbered command-line menu and coordinates user interaction.

### ExpenseManager
Handles expense creation, retrieval, searching, deletion and month-based calculations.

### BudgetManager
Handles monthly budget storage and remaining-balance calculations.

### ReportManager
Aggregates expenses and builds monthly summaries.

### Expense
Represents an expense record.

### FileManager
Loads and saves local UTF-8 data.

### InputValidator
Validates dates, months, IDs, amounts and text fields.

## Deployment Boundary

The program requires JDK 11 or newer and runs without a database or external runtime dependency.
