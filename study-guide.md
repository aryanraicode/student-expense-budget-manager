# Study Guide

## Java Concepts Demonstrated

### Classes and Objects
`Expense`, `ExpenseManager`, `BudgetManager` and `ReportManager` are separate classes with defined responsibilities.

### Encapsulation
Expense fields are private and accessed using getters.

### Collections
The application uses `ArrayList`, `List` and `Map`.

### Lambda / Stream Operations
Streams are used for searching, filtering, totals and comparisons.

### BigDecimal
`BigDecimal` is used instead of floating-point arithmetic for currency.

### LocalDate and YearMonth
`LocalDate` represents expense dates and `YearMonth` represents monthly budgets and reports.

### Exception Handling
Invalid input and file I/O errors are handled with exceptions.

### File Handling
Java NIO `Path`, `Files` and UTF-8 character encoding are used for persistence.

### CLI
`Scanner`, loops, conditionals and methods implement the terminal interface.

## Viva Questions

### Why BigDecimal?
Money should use exact decimal arithmetic rather than binary floating-point arithmetic.

### Why YearMonth?
A budget belongs to a month rather than a particular day, so YearMonth directly represents the domain concept.

### Why separate FileManager?
Storage responsibilities are separated from business logic, making the program easier to maintain.

### Why validate before saving?
Invalid records should not be persisted to the local files.

### What happens if a file write fails?
The in-memory change is rolled back and the storage exception is reported.

### What is a future enhancement?
Editing existing expenses is a future enhancement.
