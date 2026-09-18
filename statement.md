# Project Statement

## Project Title

Student Expense & Budget Manager

## Problem Statement

Small purchases are easy to forget. Students need a simple way to understand where their money goes and whether their spending exceeds a monthly budget.

The project provides a simple menu-driven Java application for recording expenses, managing monthly budgets and reviewing spending.

## Objectives

1. Record and retrieve daily expenses.
2. Organize spending by category and month.
3. Compare expenditure with a monthly budget.
4. Identify overspending.
5. Preserve records between sessions.
6. Demonstrate object-oriented programming.
7. Demonstrate collections and validation.
8. Demonstrate date handling.
9. Demonstrate local file persistence.

## Functional Modules

### 1. Expense Management

- Add expense
- View expenses
- Search expenses
- Delete expense
- Validate entered values

### 2. Budget Management

- Set monthly budget
- View monthly budget
- Calculate spending
- Calculate remaining balance
- Display overspending warning

### 3. Reporting

- Select a month
- Calculate monthly total
- Calculate category totals
- Count expenses
- Find highest expense
- Find lowest expense
- Calculate remaining budget

## Non-Functional Requirements

### Usability
Numbered menu, named categories, clear formats and error messages.

### Reliability
Validate input before saving and handle invalid input without terminating the application.

### Maintainability
Separate CLI, business logic, storage and validation responsibilities.

### Performance
Linear searches and totals are suitable for ordinary student records.

### Persistence
Files are saved after successful changes.

### Privacy
Data is stored locally and runtime personal data is excluded from Git.

## Scope

The target user is a student operating a local command-line application.

Editing expenses is outside the current implemented scope and is treated as a future enhancement.

