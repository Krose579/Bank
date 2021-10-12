package com.krose.bank;

import com.krose.display.*;
import com.krose.io.Input;
import com.krose.io.Output;
import org.krose.NodeReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application implements OnNodeContainerExitListener {
    private static final Input INPUT = new Input();
    private static final Output OUTPUT = new Output();

    private static Application instance;

    private final NodeManager manager;
    private final List<Account> accounts;
    private boolean isDeposit;

    public Application(NodeManager manager) throws IOException {
        OUTPUT.setLength(100);
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
                OUTPUT.write(String.format("%s was successful. Current Balance: $%.2f.%n", (t.isDeposit() ? "Deposit" : "Withdraw"), account.getBalance()));
            }
            else OUTPUT.write("Could not find account");
        } else OUTPUT.write("Skipping transaction due to empty amount.");
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void processOnExitTransactionHistory(ValueContainer valueContainer) {
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void processOnExitDailyReport(ValueContainer valueContainer) {
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void processOnExitViewAccounts(ValueContainer valueContainer) {
        manager.next(Constants.CONTAINER_MAIN);
    }

    private void setupManager() {
        if (manager == null) return;
        manager.setInput(INPUT);
        manager.setOutput(OUTPUT);
        manager.getNodeContainer(Constants.CONTAINER_MAIN).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_CREATE_ACCOUNT).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_CREATE_CHECKING).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_CREATE_SAVINGS).setListener(this);
        manager.getNodeContainer(Constants.CONTAINER_TRANSACTION).setListener(this);
        NodeContainer transactionHistory = manager.getNodeContainer(Constants.CONTAINER_TRANSACTION_HISTORY);
        transactionHistory.setListener(this);
        transactionHistory.addNode(new Node(Constants.ID_TRANSACTION_HISTORY_NODE) {
            @Override
            public void execute() {
                getOutput().write("Enter Account Number:");
                int accountNumber = getInput().getInteger();
                Account account = getAccount(accountNumber);
                if (account == null) getOutput().write("Account not found.");
                else for (Transaction t : account.getTransactions()) getOutput().write(String.format("%s $%.2f on %s", t.isDeposit() ? "Deposited" : "Withdrew", t.getUnsignedAmount(), DateUtil.format(t.getDate())));
            }
        });
        NodeContainer dailyReport = manager.getNodeContainer(Constants.CONTAINER_DAILY_REPORT);
        dailyReport.setListener(this);
        dailyReport.addNode(new Node(Constants.ID_DAILY_REPORT_NODE) {
            @Override
            public void execute() {
                List<String> display = new ArrayList<>();
                for (Account a: accounts) {
                    display.clear();
                    display.add(String.format("Account %d", a.getAccountNumber()));
                    for (Transaction t : a.getTransactions()) {
                        if (DateUtil.today().before(t.getDate())) display.add(String.format("> %s $%.2f", t.isDeposit() ? "Deposited" : "Withdrew", t.getUnsignedAmount()));
                    }
                    if (display.size() > 1) for (String line : display) getOutput().write(line);
                }
            }
        });
        NodeContainer viewAccounts = manager.getNodeContainer(Constants.CONTAINER_VIEW_ACCOUNTS);
        viewAccounts.setListener(this);
        viewAccounts.addNode(new Node(Constants.ID_VIEW_ACCOUNTS_NODE) {
            @Override
            public void execute() {
                for (Account a : accounts ) getOutput().write(String.format("%s %d: $%.2f", a.isChecking() ? "Checking" : "Savings", a.getAccountNumber(), a.getBalance()));
            }
        });
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
    }
}
