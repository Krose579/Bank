package com.krose.bank;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class Account {
    @JsonProperty("accountNumber")
    private final int accountNumber;
    @JsonProperty("isChecking")
    private final boolean isChecking;
    @JsonProperty("interest")
    private final double interest;
    @JsonProperty("transactions")
    private final List <Transaction> transactions;

    @JsonCreator
    private Account(@JsonProperty("accountNumber") int accountNumber, @JsonProperty("isChecking") boolean isChecking, @JsonProperty("interest") double interest) {
        this.accountNumber = accountNumber;
        this.isChecking = isChecking;
        this.interest = interest;
        this.transactions = new ArrayList<>();
    }

    @JsonGetter("accountNumber")
    public int getAccountNumber() {
        return accountNumber;
    }

    @JsonGetter("isChecking")
    public boolean isChecking() {
        return isChecking;
    }

    @JsonGetter("interest")
    public double getInterest() {
        return interest;
    }

    @JsonGetter("transactions")
    public List<Transaction> getTransactions () {
        return transactions;
    }

    @JsonIgnore
    public double getBalance() {
        double balance = 0.0;
        for (Transaction t : transactions) balance += t.getAmount();
        return balance;
    }

    public void addTransaction (Transaction transaction) {
        transactions.add(transaction);
    }

    public static Account createChecking (int id) {
        return new Account(id, true, 0.0);
    }

    public static Account createSavings (int id, double interest) {
        return new Account(id, false, interest);
    }
}
