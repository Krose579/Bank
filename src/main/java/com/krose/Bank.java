package com.krose;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    private List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account newAccount) {
        accounts.add(newAccount);
    }

    public Account getAccount(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) return account;
        }
        return null;
    }

}

