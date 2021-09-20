package com.krose;

public class SavingsAccount extends Account {
    private final double interestRate;

    public SavingsAccount(int accountNumber, double interestRate) {
        super(accountNumber);
        this.interestRate = interestRate;
    }
}
