package com.example.vikky.hisab;

import java.util.ArrayList;

/**
 * Created by vikky on 7/12/15.
 */
public class TransactionDetails {
    String whoPaid, amount;
    ArrayList<String> forWhom;
    String description;

    public String getWhoPaid() {
        return whoPaid;
    }

    public void setWhoPaid(String whoPaid) {
        this.whoPaid = whoPaid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

 /*   @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(whoPaid);
        dest.writeString(forWhom);
        dest.writeString(amount);
        dest.writeString(description);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public TransactionDetails createFromParcel(Parcel source) {
            return new TransactionDetails(source);
        }

        @Override
        public TransactionDetails[] newArray(int size) {
            return new TransactionDetails[size];
        }
    };

    public TransactionDetails(Parcel source) {
        whoPaid = source.readString();
        forWhom = source.readString();
        amount = source.readString();
        description = source.readString();
    }*/
}
