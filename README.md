# Student Expense & Budget Manager

## VITyarthi - Build Your Own Project
**CSE1021 | Java Course Project**

Student Expense & Budget Manager is an offline Java command-line application for recording daily expenses, managing monthly budgets and reviewing spending.

### Features

- Add expenses
- View all expenses
- Search expenses
- Delete expenses
- Set monthly budgets
- View monthly budget information
- Generate monthly spending reports
- Detect overspending
- Preserve data between sessions
- Validate user input
- Store data locally using UTF-8 files

### Technology

- Java 11 or newer
- Java Standard Library
- Command-line interface
- Local file storage
- `BigDecimal` for currency
- `LocalDate` and `YearMonth` for dates

## Project Structure

```text
student-expense-budget-manager/
│
├── src/
│   ├── Main.java
│   ├── model/
│   │   └── Expense.java
│   ├── service/
│   │   ├── ExpenseManager.java
│   │   ├── BudgetManager.java
│   │   └── ReportManager.java
│   ├── storage/
│   │   └── FileManager.java
│   └── util/
│       └── InputValidator.java
│
├── tests/
│   └── TestRunner.java
│
├── docs/
│   ├── architecture.md
│   ├── diagrams.md
│   ├── study-guide.md
│   ├── submission.md
│   ├── verification.md
│   └── cli-session.txt
│
├── data/
│   └── .gitkeep
│
├── statement.md
├── .gitignore
└── README.md
```

## Requirements

Install **JDK 11 or newer**.

Check the installation:

```bash
java -version
javac -version
```

Both commands must work from the terminal.

## How to Run

Open a terminal in the project root.

### 1. Compile the application

```bash
javac -d out src/model/*.java src/service/*.java src/storage/*.java src/util/*.java src/Main.java
```

### 2. Run the application

```bash
java -cp out Main
```

### 3. Use a separate data directory

```bash
java -cp out Main demo-data
```

The default data directory is `data`.

Only one running application instance should use a given data directory at a time.

## How to Run Tests

First compile the application:

```bash
javac -d out src/model/*.java src/service/*.java src/storage/*.java src/util/*.java src/Main.java
```

Then compile the test runner:

```bash
javac -cp out -d out tests/TestRunner.java
```

Run:

```bash
java -cp out TestRunner
```

The test runner uses an isolated temporary directory and removes its test data when it finishes.

## Menu

```text
1. Add expense
2. View expenses
3. Search expenses
4. Delete expense
5. Set monthly budget
6. View monthly budget
7. View monthly report
8. Exit
```

## Expense Fields

Each expense contains:

- ID
- Date
- Category
- Description
- Amount

## Budget

A separate budget can be stored for each month.

Example:

```text
Month: 2026-09
Budget: Rs. 1000.00
```

## Example

Set a budget:

```text
Choose 5
Month: 2026-09
Budget: 1000
```

Add an expense:

```text
Choose 1
Date: 2026-09-17
Category: Food
Description: Lunch
Amount: 125.50
```

View the report:

```text
Choose 7
Month: 2026-09
```

Expected remaining budget:

```text
Rs. 1000.00 - Rs. 125.50 = Rs. 874.50
```

## Data Storage

The application creates:

```text
data/expenses.csv
data/budget.txt
```

The files are UTF-8 text files and are written after successful changes.

Runtime personal data should not be committed to Git. The `.gitignore` file therefore ignores the contents of the `data` directory while keeping `data/.gitkeep`.

## Design

The application separates:

- CLI interaction in `Main`
- Expense business logic in `ExpenseManager`
- Budget logic in `BudgetManager`
- Reporting logic in `ReportManager`
- Persistence in `FileManager`
- Validation in `InputValidator`
- Expense data representation in `Expense`

## Limitations / Future Enhancements

Possible future enhancements include:

- Editing existing expenses
- Exporting reports
- Recurring expenses
- Configurable categories
- Full CSV-library import/export for multiline fields
- Additional reporting features

## Important Note

This repository is a project-ready reconstruction based on the supplied Student Expense & Budget Manager project report. The report states that the original source code was not supplied as evidence, so undocumented source-level method signatures, exact serialization details and exact internal call order are not claimed.
