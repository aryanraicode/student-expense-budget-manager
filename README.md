# student-expense-budget-manager
## Overview
A Java command-line application designed to help students record daily expenses, manage monthly budgets, and analyze spending patterns. The application works offline using local file storage and the Java standard library. 

## Problem Statement
Small purchases are easy to forget. Students need a simple way to see where their money goes and whether a month's spending exceeds their budget. See [statement.md](statement.md).

## Features
- Add, list, search, and delete expenses with validated inputs.
- Set a separate budget for each month and display overspending warnings.
- Monthly category totals, count, highest/lowest expense, and remaining budget.
- Automatic local file creation and persistence after each successful change.

## Technologies Used
Java standard library, JDK command-line tools, Git, Markdown, and Mermaid diagrams. No external runtime dependencies, database, IDE, or network connection is needed to run the app.

## Java Concepts Used
Classes/objects and constructors: `Expense` and managers. Encapsulation: private fields and getters. `ArrayList` and collections: expenses, sorting comparisons, maps and sets. Packages: `model`, `service`, `storage`, `util`. File handling: `Files` and `Path`. Exception handling: validation and I/O errors. Loops, conditionals, methods, and `Scanner`: the menu. `BigDecimal` keeps currency exact; `LocalDate` and `YearMonth` validate dates and select months. Small lambdas support searching, deletion, and comparisons.

## Project Structure
```text
student-expense-budget-manager/
  README.md
  statement.md
  .gitignore
  src/
    Main.java
    model/Expense.java
    service/ExpenseManager.java
    service/BudgetManager.java
    service/ReportManager.java
    storage/FileManager.java
    util/InputValidator.java
  tests/TestRunner.java
  data/.gitkeep
  docs/
    architecture.md
    diagrams.md
    study-guide.md
    submission.md
    verification.md
    cli-session.txt
```
`data/expenses.csv` and `data/budget.txt` are created on first execution and excluded from Git to avoid publishing personal expense records.

## Requirements
JDK 11 or newer (a JRE alone is insufficient). Ensure `java` and `javac` are on PATH. Check both with `java -version` and `javac -version`. Commands below work in PowerShell, Windows Command Prompt, and ordinary Unix shells.

## Installation
Download/extract the project, then open a terminal in its root folder. If it has been published to the intended account, alternatively run:
```sh
git clone https://github.com/sancharika25bai10963-creator/student-expense-budget-manager.git
cd student-expense-budget-manager
```
That URL is the intended destination, not a claim that a public repository exists. No dependency installation or configuration is required beyond the JDK.

## Compilation
From the project root:
```sh
javac -d out src/model/*.java src/service/*.java src/storage/*.java src/util/*.java src/Main.java
```

## Running the Project
```sh
java -cp out Main
```
Keep the working directory at the project root to reuse the same `data` directory. Optionally select another data folder: `java -cp out Main demo-data`. Use only one running application per data directory.

## Example Usage
Choose `5`, enter `2026-09`, then `1000` to set the September budget. Choose `1` and enter `2026-09-17`, `Food`, `Lunch`, and `125.50`. Choose `7` and enter `2026-09` to see the report. For this illustrative single-record scenario the remaining budget is Rs. 874.50. Entering a blank month selects the current system month. Choose `8` to exit.

Search is case insensitive across descriptions and categories, substring-based for dates, and exact for ID text. Results match any of these fields. All expenses are listed in insertion order. Deletion takes a numeric ID. Invalid entry cancels that operation and returns to the menu; enter the operation again to retry. End-of-input exits cleanly.

## Testing
Compile the app first, then:
```sh
javac -cp out -d out tests/TestRunner.java
java -cp out TestRunner
```
The runner creates an isolated temporary directory and deletes only its own test data. It throws an error and exits unsuccessfully if any check fails. It tests expense operations, exact money arithmetic, month filtering, budgets, reports, input validation, persistence, malformed files, and failed writes. See [verification.md](docs/verification.md) for actual execution evidence and menu checks.

## Functional Requirements
1. Expense management: store ID, date, category, description, amount; add/view/search/delete.
2. Budget management: set/view monthly budgets, calculate spending and remaining balance, warn on overspending.
3. Reporting: monthly totals, category totals, highest/lowest expense, count, and remaining budget.

## Non-Functional Requirements
- Usability: numbered menu, named categories, clear formats and error messages.
- Reliability: validate before saving; recover from invalid input; preserve malformed source files for repair.
- Maintainability: seven production classes with separate CLI, business logic, storage, and validation responsibilities.
- Performance: linear searches and totals for ordinary student records; each mutation rewrites its file. No large-scale performance claim is made.
- Persistence: UTF-8 files saved after each successful change; temporary-file replacement reduces partial-write risk.
- Privacy: offline storage; personal data excluded from Git. Files are not encrypted.

## Future Improvements
Edit existing expenses, export reports, recurring expenses, configurable categories, and import/export with a full CSV library if multiline fields become necessary. See [architecture.md](docs/architecture.md) for current limits and [study-guide.md](docs/study-guide.md) for explanations.
