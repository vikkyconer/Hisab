package com.example.vikky.hisab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class ComputeActivity extends ActionBarActivity {

    //    TransactionDetails details = null;
    String amount, whoShouldPay, payToWhom;
//    ArrayList<TransactionDetails> details = new ArrayList<>();

//    public ArrayList<TransactionDetails> getDetails() {
//        return details;
//    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWhoShouldPay() {
        return whoShouldPay;
    }

    public void setWhoShouldPay(String whoShouldPay) {
        this.whoShouldPay = whoShouldPay;
    }

    public String getPayToWhom() {
        return payToWhom;
    }

    public void setPayToWhom(String payToWhom) {
        this.payToWhom = payToWhom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);
        Intent i = getIntent();
        Bundle b = i.getExtras();

//        Log.i("whoShouldPay", b.getString("whoShouldPay", "sorry"));
//        Log.i("payToWhom", b.getString("payToWhom", "sorry"));
//        Log.i("amount", b.getString("amount", "sorry"));

        setWhoShouldPay(b.getString("whoShouldPay", "sorry"));
        setPayToWhom(b.getString("payToWhom", "sorry"));
        setAmount(b.getString("amount", "0"));
//        TransactionDetails transactionDetails = new TransactionDetails();
//        transactionDetails.setWhoPaid(b.getString("whoShouldPaid"));
//        transactionDetails.setForWhom(b.getString("paiyToWhom"));
//        transactionDetails.setAmount(b.getString("amount"));
//        details.add(transactionDetails);
//        Log.i("ComputeActivity", details.get(0).whoPaid);
//        Log.i("ComputeActivity", getWhoPaid());
        new ComputePresenter(computeModel(), computeView());
    }

    private ComputeView computeView() {
        return (ComputeFragment) getSupportFragmentManager().findFragmentById(R.id.compute_fragment);
    }

    private ComputeModel computeModel() {
        return null;
    }
}
