package com.ideabank.vikky.hisab.Models;

import java.util.ArrayList;

/**
 * Created by vikky on 7/12/15.
 */
public class TransactionDetails {
    String whoPaid;
    Integer placeId, amount, transactionId;
    ArrayList<String> forWhom;
    String description;
    String forWhomIdStrore;


    public TransactionDetails() {
    }

    public TransactionDetails(int placeId, String whoPaid, ArrayList<String> forWhom, int amount, String description, String forWhomIdStrore) {
        this.placeId = placeId;
        this.whoPaid = whoPaid;
        this.forWhom = forWhom;
        this.amount = amount;
        this.description = description;
        this.forWhomIdStrore = forWhomIdStrore;
    }

    public TransactionDetails(int transactionId, int placeId, String whoPaid, ArrayList<String> forWhom, int amount, String description,String forWhomIdStrore) {
        this.transactionId = transactionId;
        this.placeId = placeId;
        this.whoPaid = whoPaid;
        this.forWhom = forWhom;
        this.amount = amount;
        this.description = description;
        this.forWhomIdStrore = forWhomIdStrore;
    }

    public String getWhoPaid() {
        return whoPaid;
    }

    public void setWhoPaid(String whoPaid) {
        this.whoPaid = whoPaid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getForWhomIds() {
        return forWhom;
    }

    public void setForWhom(ArrayList<String> forWhom) {
        this.forWhom = forWhom;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public ArrayList<String> getForWhom() {
        return forWhom;
    }

    public String getForWhomIdStrore() {
        return forWhomIdStrore;
    }

    public void setForWhomIdStrore(String forWhomIdStrore) {
        this.forWhomIdStrore = forWhomIdStrore;
    }
}
