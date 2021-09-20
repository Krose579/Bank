package com.krose;

public class CheckingAccount extends Account {

    public static final double fee = 1.50;

    public CheckingAccount(){
        super();
    }

    public CheckingAccount(int accountNumber, double fee) {
        super(accountNumber);
    }

    public void deposit(double amount) {
        System.out.println("A fee of $1.50 is applicable.");
        if (amount > 0) {
            balance += amount;
            balance -= fee;
            System.out.println("Balance is now $" + balance + ".");
        } else {
            System.out.println("Amount cannot be 0.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            System.out.println("A fee of $1.50 is applicable.");
            if (amount + fee <= balance) {
                balance -= amount;
                balance -= fee;
                System.out.println(amount + "withdrawn from account.");
                System.out.println("Current balance is $" + balance + ".");
            }
        } else {
            System.out.println("Amount cannot be 0.");
        }
    }
}
