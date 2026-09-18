# Verification

## Build Verification

Run:

```bash
javac -d out src/model/*.java src/service/*.java src/storage/*.java src/util/*.java src/Main.java
```

A successful command should produce compiled classes in `out`.

## Test Verification

Run:

```bash
javac -cp out -d out tests/TestRunner.java
java -cp out TestRunner
```

The test runner checks:

- Empty initial expense list
- Expense IDs
- Exact money arithmetic
- Month totals
- Case-insensitive search
- Monthly budget
- Remaining budget
- Report generation
- Deletion
- Persistence after reload

## Manual Verification Scenario

1. Run the application.
2. Choose `5`.
3. Enter `2026-09`.
4. Enter `1000`.
5. Choose `1`.
6. Enter `2026-09-17`.
7. Enter `Food`.
8. Enter `Lunch`.
9. Enter `125.50`.
10. Choose `7`.
11. Enter `2026-09`.

The remaining budget should be:

```text
Rs. 874.50
```

This scenario follows the illustrative example documented in the project report.
