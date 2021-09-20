package com.krose;
public class SavingsAccount extends Account {

    private double interestRate;

    public static final double fee = 1.50;

    public SavingsAccount(){
    }

    public SavingsAccount(int accountNumber, double interestRate) {
        super(accountNumber);
        this.interestRate = interestRate;
    }

    public double getInterestRate(){
        return this.interestRate;
    }

    public void setInterestRate(double interestRate){
        this.interestRate = interestRate;
    }

    public double calcInterest(){
        return balance + interestRate;
    }

    public void applyInterest(){
        double interest = calcInterest();
        System.out.println("Interest amount added to balance.");
        deposit(interest);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Balance is now $" + balance + ".");
        } else {
            System.out.println("Amount cannot be 0.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                System.out.println(amount + "withdrawn from account.");
                System.out.println("Current balance is $" + balance + ".");
            }
        } else {
            System.out.println("Amount cannot be 0.");
        }
    }
}
