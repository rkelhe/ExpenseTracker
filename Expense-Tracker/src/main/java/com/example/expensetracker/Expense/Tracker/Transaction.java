package com.example.expensetracker.Expense.Tracker;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private TransactionType type;
    private String category;
    private double amount;
    private LocalDate date;

    public Transaction(TransactionType type, String category, double amount, LocalDate date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Constructor for the original addTransaction which takes String type
    public Transaction(String type, String category, double amount, LocalDate date) {
        this.type = TransactionType.valueOf(type.toUpperCase()); // Convert string to enum
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return type.name() + "," + category + "," + amount + "," + date.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                type == that.type &&
                Objects.equals(category, that.category) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, category, amount, date);
    }
}