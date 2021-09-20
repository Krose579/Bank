package com.krose;

import com.krose.Input;
import com.krose.Output;
import com.krose.io.IMenu;
import com.krose.io.MainMenuIO;
import com.krose.io.NewAccountMenuIO;

public class Application {
    private static Bank currentBank;

    public static void main(String[] args) {
        currentBank = new Bank();
        Input input = new Input();
        Output output = new Output();
        IMenu mainMenu = new MainMenuIO(input, output);
        IMenu newAccountMenu = new NewAccountMenuIO(input, output);
        IMenu currentMenu = mainMenu;
        int menuChoice = 0;
        while (menuChoice != -1) {
            menuChoice = currentMenu.getMenuChoice();
            if (currentMenu == mainMenu) {
                switch (menuChoice) {
                    case 1:
                        currentMenu = newAccountMenu;
                        break;
                    case 2:
                        handleDeposit(input, output);
                        break;
                    case 3:
                        handleWithdraw(input, output);
                        break;
                    case 4:
                        handleAppliedInterest(input, output);
                        break;
                    case 5:
                        menuChoice = -1;
                        break;
                }
            } else if (currentMenu == newAccountMenu) {
                Account newAccount = null;
                switch (menuChoice) {
                    case 1:
                        newAccount = createCheckingAccount(input, output);
                        break;
                    case 2:
                        newAccount = createSavingsAccount(input, output);
                        break;
                }
                if (newAccount != null) {
                    currentBank.addAccount(newAccount);
                    output.write("New Account Added: " + newAccount.getAccountNumber());
                }
                currentMenu = mainMenu;
            }
        }
    }

    private static Account createCheckingAccount(Input input, Output output) {
        output.write("Enter Account Number:");
        int accountNumber = input.getInteger();
        return new Account(accountNumber);
    }

    private static Account createSavingsAccount(Input input, Output output) {
        output.write("Enter Account Number:");
        int accountNumber = input.getInteger();
        output.write("Enter Interest Rate:");
        double interestRate = input.getDouble();
        return new SavingsAccount(accountNumber, interestRate);
    }

    private static void handleDeposit (Input input, Output output) {
        output.write("Enter Account Number:");
        int accountNumber = input.getInteger();
        Account account = currentBank.getAccount(accountNumber);
        if (account == null) {
            output.write("Account Not Found.");
        } else {
            output.write("Enter Amount:");
            double amount = input.getDouble();
            account.deposit(amount);
            output.write("Deposit Successful.");
        }
    }

    private static void handleWithdraw (Input input, Output output) {

    }

    private static void handleAppliedInterest (Input input, Output output) {

    }
}
