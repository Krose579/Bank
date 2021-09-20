package com.krose;

public abstract class Account {
    protected int accountNumber;

    protected double balance;

    protected double amount;

    protected Account(){
    }

    public Account (int accountNumber){
        this.accountNumber = accountNumber;
        balance = 0;
    }

    public double getBalance(){
        return this.balance;
    }

    public int getAccountNumber(){
        return this.accountNumber;
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);
}