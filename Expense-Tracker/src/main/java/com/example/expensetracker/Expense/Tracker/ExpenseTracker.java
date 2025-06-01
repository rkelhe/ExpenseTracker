package com.example.expensetracker.Expense.Tracker;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class ExpenseTracker {
    private static List<Transaction> transactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Monthly Summary");
            System.out.println("4. Load Data from File");
            System.out.println("5. Save Data to File");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addTransaction("INCOME");
                case 2 -> addTransaction("EXPENSE");
                case 3 -> showMonthlySummary();
                case 4 -> loadFromFile("C:\\Users\\rmkel\\Downloads\\Transactions.csv");
                case 5 -> saveToFile();
                case 6 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void addTransaction(String type) {
        System.out.print("Enter category (" + (type.equals("INCOME") ? "Salary, Business" : "Food, Rent, Travel") + "): ");
        String category = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter date (yyyy-mm-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        // Use the constructor that takes a String for type, which then converts it to enum
        transactions.add(new Transaction(type, category, amount, date));
        System.out.println("Transaction added successfully.");
    }

    private static void showMonthlySummary() {
        System.out.print("Enter month (yyyy-mm): ");
        YearMonth month = YearMonth.parse(scanner.nextLine());

        double income = 0, expense = 0;
        System.out.println("\n--- Monthly Summary for " + month + " ---");

        for (Transaction t : transactions) {
            if (YearMonth.from(t.getDate()).equals(month)) {
                if (t.getType() == TransactionType.INCOME) { // Compare using enum directly
                    income += t.getAmount();
                } else { // It must be EXPENSE
                    expense += t.getAmount();
                }
            }
        }

        System.out.println("Total Income : $" + income);
        System.out.println("Total Expense: $" + expense);
        System.out.println("Net Savings  : $" + (income - expense));
    }

    public static void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip the header
            line = reader.readLine(); // this reads and discards the header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Ensure all parts are present before attempting to parse
                if (parts.length != 4) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }

                // Use trim() to remove any leading/trailing whitespace
                // Then convert to uppercase for matching with enum constants
                TransactionType type = TransactionType.valueOf(parts[0].trim().toUpperCase());
                String category = parts[1].trim();
                double amount = Double.parseDouble(parts[2].trim());
                LocalDate date = LocalDate.parse(parts[3].trim());

                transactions.add(new Transaction(type, category, amount, date));
            }

            System.out.println("Loaded " + transactions.size() + " transactions.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.out.println("Please ensure the file path is correct and the file exists.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing data from file. Check data format (e.g., date, enum values): " + e.getMessage());
            System.out.println("Specifically, ensure 'Type' column contains 'INCOME' or 'EXPENSE' exactly.");
        } catch (Exception e) { // Catch any other unexpected exceptions
            System.err.println("An unexpected error occurred during file loading: " + e.getMessage());
        }
    }


    private static void saveToFile() {
        System.out.print("Enter file path to save: ");
        String path = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            writer.println("Type,Category,Amount,Date");
            for (Transaction t : transactions) {
                writer.println(t.toString());
            }
            System.out.println("Transactions saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}