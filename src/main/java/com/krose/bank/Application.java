package com.krose.bank;

import com.krose.display.NodeManager;
import com.krose.display.OnNodeContainerExitListener;
import com.krose.display.ValueContainer;
import com.krose.io.Input;
import com.krose.io.Output;
import org.krose.NodeReader;

import java.io.IOException;
import java.util.List;

public class Application implements OnNodeContainerExitListener {
    private static Application instance;

    private final NodeManager manager;
    private List<Account> accounts;
    private boolean isDeposit;

    public Application(NodeManager manager) throws IOException {
        this.manager = manager;
        this.accounts = Database.load();
        this.isDeposit = false;
        setupManager();
    }

    public void execute() {
        if (manager != null) manager.execute();
    }

    @Override
    public void onNodeContainerExit(String id, ValueContainer valueContainer) {
        switch (id) {
            case Constants.CONTAINER_MAIN -> processOnExitMain(valueContainer);
            case Constants.CONTAINER_CREATE_ACCOUNT -> processOnExitCreateAccount(valueContainer);
            case Constants.CONTAINER_CREATE_CHECKING -> processOnExitCreateChecking(valueContainer);
            case Constants.CONTAINER_CREATE_SAVINGS -> processOnExitCreateSavings(valueContainer);
            case Constants.CONTAINER_TRANSACTION -> processOnExitTransaction(valueContainer);
            case Constants.CONTAINER_TRANSACTION_HISTORY -> processOnExitTransactionHistory(valueContainer);
            case Constants.CONTAINER_DAILY_REPORT -> processOnExitDailyReport(valueContainer);
            case Constants.CONTAINER_VIEW_ACCOUNTS -> processOnExitViewAccounts(valueContainer);
        }
    }

    private void processOnExitMain(ValueContainer valueContainer) {
        int choice = valueContainer.getInteger(Constants.ID_MAIN_MENU);
        switch (choice) {
            case 1 -> manager.next(Constants.CONTAINER_CREATE_ACCOUNT);
            case 2 -> {
                isDeposit = true;
                manager.next(Constants.CONTAINER_TRANSACTION);
            }
            case 3 -> {
                isDeposit = false;
                manager.next(Constants.CONTAINER_TRANSACTION);
            }
            case 4 -> manager.next(Constants.CONTAINER_TRANSACTION_HISTORY);
            case 5 -> manager.next(Constants.CONTAINER_DAILY_REPORT);
            case 6 -> manager.next(Constants.CONTAINER_VIEW_ACCOUNTS);
            case 7 -> { try { Database.save(accounts); } catch (IOException ignored) {} }
        }
    }

    private void processOnExitCreateAccount(ValueContainer valueContainer) {
        if (valueContainer.getInteger(Constants.ID_CREATE_ACCOUNT_TYPE) == 1) manager.next(Constants.CONTAINER_CREATE_CHECKING);
        else manager.next(Constants.CONTAINER_CREATE_SAVINGS);
    }

    private void processOnExitCreateChecking(ValueContainer valueContainer) {
        accounts.add(Account.createChecking(valueContainer.getInteger(Constants.ID_CREATE_CHECKING_ID)));
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void processOnExitCreateSavings(ValueContainer valueContainer) {
        accounts.add(Account.createSavings(valueContainer.getInteger(Constants.ID_CREATE_SAVINGS_ID), valueContainer.getDouble(Constants.ID_CREATE_SAVINGS_INTEREST_RATE)));
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void processOnExitTransaction(ValueContainer valueContainer) {
        int accountNumber = valueContainer.getInteger(Constants.ID_TRANSACTION_ID);
        double amount = valueContainer.getDouble(Constants.ID_TRANSACTION_AMOUNT) * (isDeposit ? 1 : -1);
        if (Math.abs(amount) > 0) {
            Account account = getAccount(accountNumber);
            if (account != null) {
                Transaction t = new Transaction(amount);
                account.addTransaction(t);
                System.out.printf("%s was successful. Current Balance: $%.2f.%n", (t.isDeposit() ? "Deposit" : "Withdraw"), account.getBalance());
            }
            else System.out.println("Could not find account");
        } else {
            System.out.println("Skipping transaction due to empty amount.");
        }
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void processOnExitTransactionHistory(ValueContainer valueContainer) {
        System.out.println("Exiting Transaction History");
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void processOnExitDailyReport(ValueContainer valueContainer) {
        System.out.println("Exiting Daily Report");
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void processOnExitViewAccounts(ValueContainer valueContainer) {
        System.out.println("Exiting View Account");
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void setupManager() {
        if (manager == null) return;
        manager.setInput(new Input());
        manager.setOutput(new Output());
        manager.getNodeContainer(Constants.CONTAINER_MAIN).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_CREATE_ACCOUNT).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_CREATE_CHECKING).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_CREATE_SAVINGS).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_TRANSACTION).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_TRANSACTION_HISTORY).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_DAILY_REPORT).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_VIEW_ACCOUNTS).setListener(this);
        manager.next(Constants.CONTAINER_MAIN);
    }

    private Account getAccount(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) return account;
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        if (instance != null) return;
        instance = new Application(NodeReader.createNodeManager("/" + Constants.RES_UI));
        instance.execute();
//        List<Account> accounts = new ArrayList<>();
//        Account testAccount = Account.createChecking(101);
//        testAccount.setBalance(102.50);
//        accounts.add(testAccount);
//        accounts.add(Account.createChecking(102));
//        accounts.add(Account.createChecking(103));
//        accounts.add(Account.createChecking(104));
//        accounts.add(Account.createChecking(105));
//        accounts.add(Account.createSavings(201, 1.0));
//        accounts.add(Account.createSavings(202, 1.0));
//        accounts.add(Account.createSavings(203, 1.0));
//        accounts.add(Account.createSavings(204, 1.0));
//        accounts.add(Account.createSavings(205, 1.0));
//        Database.save(accounts);
//        System.out.println(accounts.size());
//        accounts.clear();
//        System.out.println(accounts.size());
//        accounts = Database.load();
//        System.out.println(accounts.size());
//        for (Account account : accounts) {
//            System.out.println("Is checking: " + account.isChecking() + " -> " + account.getAccountNumber() + " " + account.getBalance() + (account.isChecking() ? "" : (" " + account.getInterest())));
//        }
    }
}
