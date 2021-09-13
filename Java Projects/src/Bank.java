import java.util.Scanner;

public class Bank extends Account{

    static Scanner input = new Scanner(System.in);

    static Account[] account = new Account[10];

    public static int numAccounts = 0;

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

        Account[] accounts = new Account[0];
        do {
            int choice = (int) menu(input);
            System.out.println();

            if (choice == 1) {
                accounts[numAccounts++] = createAccount(input);
            } else if (choice == 2) {
                doDeposit(account, numAccounts, input);
            } else if (choice == 3) {
                doWithdraw(account, numAccounts, input);
            } else if (choice == 4) {
                applyInterest(account, numAccounts, input);
            } else {
                System.out.println("Goodbye.");
            }

            System.out.println();
        } while (choice != 1);
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
        public static int searchAccount (Account accounts[],int count, int accountNumber){
            for (int i = 0; i < count; i++) {
                if (accounts[i].getAccountNumber() == accountNumber) {
                    return i;
                }
            }
            return count;
        }

        public static void doDeposit (Account accounts[],int count, Scanner keyboard){
            System.out.println("Please enter account number.");
            int accountNumber = keyboard.nextInt();

            int index = searchAccount(accounts, count, accountNumber);

            if (index >= 0) {
                System.out.println("Please enter deposit amount.");
                double amount = keyboard.nextDouble();
                accounts[index].deposit(amount);
            } else {
                System.out.println("Account could not be found with number" + accountNumber + ".");
            }
        }

        public static void doWithdraw (Account accounts[],int count, Scanner keyboard){

            System.out.println("Please enter account number.");
            int accountNumber = keyboard.nextInt();

            int index = searchAccount(accounts, count, accountNumber);

            if (index >= 0) {
                System.out.println("Please enter withdraw amount.");
                double amount = keyboard.nextDouble();
                accounts[index].withdraw(amount);

            } else {
                System.out.println("Account could not be found with number" + accountNumber + ".");
            }
        }

        public static void applyInterest (Account[]accounts,int count, Scanner input) {
            System.out.println("\nPlease enter account number: ");
            int accountNumber = input.nextInt();

            int index = searchAccount(accounts, count, accountNumber);

            if (index >= 0) {

                if (accounts[index] instanceof SavingsAccount) {
                    ((SavingsAccount) accounts[index]).applyInterest();
                } else {
                    System.out.println("Account could not be found with number" + accountNumber + ".");
                }
            }
        }

            public static Account createAccount(Scanner input){

                Account account = null;
                int choice = accountMenu(input);
                int localAccountNum;

                System.out.println("Enter account number.");
                accountNumber = input.nextInt();

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

