package com.krose;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank extends Account{

    static Scanner input = new Scanner(System.in);
    public static int choice;
    public static double menuChoice;

    protected Bank(int accountNumber) {
        super(accountNumber);
    }

    @Override
    public void deposit(double amount) {
    }

    @Override
    public void withdraw(double amount) {
    }

    public static void main(String[] args) {

        ArrayList<Account> accounts = new ArrayList<>();
        do {
            int choice = (int) menu(input);
            System.out.println();

            if (choice == 1) {
                accounts.add(createAccount(input));
            } else if (choice == 2) {
                doDeposit(accounts, input);
            } else if (choice == 3) {
                doWithdraw(accounts, input);
            } else if (choice == 4) {
                applyInterest(accounts, input);
            } else {
                System.out.println("Goodbye.");
            }

            System.out.println();
        } while (choice != 5);
    }

        public static int accountMenu (Scanner keyboard){
            System.out.println("Select account type.");
            System.out.println("1. Checking");
            System.out.println("2. Savings");

            do {
                System.out.println("Enter:");
                choice = keyboard.nextInt();
            } while (choice < 1 || choice > 2);
            return choice;
        }
        public static int searchAccount (ArrayList<Account> accounts, int accountNumber){
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getAccountNumber() == accountNumber) {
                    return i;
                }
            }
            return -1;
        }


        public static void doDeposit (ArrayList<Account> accounts, Scanner keyboard){
            System.out.println("Please enter account number.");
            int accountNumber = keyboard.nextInt();

            int index = searchAccount(accounts, accountNumber);

            if (index >= 0) {
                System.out.println("Please enter deposit amount.");
                double amount = keyboard.nextDouble();
                accounts.get(index).deposit(amount);
            } else {
                System.out.println("Account could not be found with number" + accountNumber + ".");
            }
        }

        public static void doWithdraw (ArrayList<Account> accounts, Scanner keyboard){

            System.out.println("Please enter account number.");
            int accountNumber = keyboard.nextInt();

            int index = searchAccount(accounts, accountNumber);

            if (index >= 0) {
                System.out.println("Please enter withdraw amount.");
                double amount = keyboard.nextDouble();
                accounts.get(index).withdraw(amount);

            } else {
                System.out.println("Account could not be found with number" + accountNumber + ".");
            }
        }

        public static void applyInterest (ArrayList<Account> accounts, Scanner input) {
            System.out.println("\nPlease enter account number: ");
            int accountNumber = input.nextInt();

            int index = searchAccount(accounts, accountNumber);

            if (index >= 0) {

                if (accounts.get(index) instanceof SavingsAccount) {
                    ((SavingsAccount) accounts.get(index)).applyInterest();
                } else {
                    System.out.println("Account could not be found with number" + accountNumber + ".");
                }
            }
        }

            public static Account createAccount(Scanner input){

                Account account = null;
                int choice = accountMenu(input);

                System.out.println("Enter account number.");
                int accountNumber = input.nextInt();

                if (choice == 1) {
                    System.out.println("Enter transaction fee:");
                    double fee = input.nextDouble();
                    account = new CheckingAccount(accountNumber, fee);

                } else {
                    System.out.println("Please enter interest rate.");
                    double interestRate = input.nextDouble();
                    account = new SavingsAccount(accountNumber, interestRate);
                }
                return account;
            }

            public static double menu(Scanner input){
                System.out.println("Bank Menu");
                System.out.println("1. create new account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Apply Interest");
                System.out.println("5. Exit");

                int choice;

                do {
                    System.out.println("Enter:");
                    choice = input.nextInt();

                } while (choice < 1 || choice > 5);
                return choice;
            }
        }

