package com.example.vikky.hisab;

/**
 * Created by vikky on 8/1/15.
 */
public class ExpenditureModel {
    String WhoHasToPay,WhomToPay;
    int amount;

    public String getWhoHasToPay() {
        return WhoHasToPay;
    }

    public void setWhoHasToPay(String whoHasToPay) {
        WhoHasToPay = whoHasToPay;
    }

    public String getWhomToPay() {
        return WhomToPay;
    }

    public void setWhomToPay(String whomToPay) {
        WhomToPay = whomToPay;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
