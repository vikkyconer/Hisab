package com.example.vikky.hisab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class ComputeActivity extends ActionBarActivity {

    TransactionDetails details = null;
    String amount, whoPaid, paidForWhom;

    public String getPaidForWhom() {
        return paidForWhom;
    }

    public void setPaidForWhom(String paidForWhom) {
        this.paidForWhom = paidForWhom;
    }

    public String getWhoPaid() {
        return whoPaid;
    }

    public void setWhoPaid(String whoPaid) {
        this.whoPaid = whoPaid;
    }


    public TransactionDetails getDetails() {
        return details;
    }

    public void setDetails(TransactionDetails details) {
        this.details = details;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        setWhoPaid(b.getString("whoPaid"));
        setPaidForWhom(b.getString("paidForWhom"));
        setAmount(b.getString("amount"));
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
