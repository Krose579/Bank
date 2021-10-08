package com.krose.bank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public final class Transaction {
    @JsonProperty ("amount")
    private final double amount;
    @JsonProperty ("date")
    private final Date date;

    @JsonCreator
    public Transaction(@JsonProperty ("amount") double amount) {
        this.amount = amount;
        this.date = new Date();
    }

    @JsonGetter ("amount")
    public double getAmount() {
        return amount;
    }

    @JsonIgnore
    public double getUnsignedAmount() {
        return Math.abs(getAmount());
    }

    @JsonGetter ("date")
    public Date getDate() {
        return date;
    }

    @JsonIgnore
    public boolean isDeposit () {
        return getAmount() > 0;
    }

    @JsonIgnore
    public boolean isWithdraw () {
        return getAmount() < 0;
    }
}