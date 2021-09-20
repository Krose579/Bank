package com.krose;

public class Account {
    protected final int accountNumber;
    protected double balance;

    public Account (int accountNumber){
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public int getAccountNumber(){
        return this.accountNumber;
    }

    public double getBalance(){
        return this.balance;
    }

    public void deposit(double amount) {


    }

    public boolean withdraw(double amount) {
        return false;

    }
}
