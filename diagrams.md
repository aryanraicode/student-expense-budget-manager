# Diagrams

## Use Case Diagram

```text
              +-------------------------------------------+
              |       Student Expense & Budget Manager   |
              |                                           |
Student ------|--> Add / View / Search / Delete Expenses |
              |                                           |
Student ------|--> Set / View Monthly Budget             |
              |                                           |
Student ------|--> View Monthly Report and Balance        |
              +-------------------------------------------+
```

## Menu Workflow

```text
Start / Load Local Data
          |
          v
   Show Numbered Menu
          |
          v
    Select Operation
          |
          v
     Validate Input
       /       \
   invalid     valid
     |           |
     v           v
 Return Menu   Process Request
                   |
                   v
              Save Changes
                   |
                   v
              Display Result
                   |
                   v
               Main Menu
```

## Add Expense Sequence

```text
Student -> Main:
Choose add and enter fields

Main -> InputValidator:
Validate input

InputValidator -> Main:
Valid values

Main -> ExpenseManager:
Request expense addition

ExpenseManager -> FileManager:
Persist successful change

FileManager -> ExpenseManager:
Storage outcome

ExpenseManager -> Main:
Operation outcome

Main -> Student:
Display result
```

These diagrams represent the documented logical responsibilities of the project.
