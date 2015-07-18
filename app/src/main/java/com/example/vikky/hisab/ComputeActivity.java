package com.example.vikky.hisab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.ArrayList;


public class ComputeActivity extends ActionBarActivity {

    //    TransactionDetails details = null;
    String amount, whoPaid, paidForWhom;
    ArrayList<TransactionDetails> details = new ArrayList<>();

    public ArrayList<TransactionDetails> getDetails() {
        return details;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setWhoPaid(b.getString("whoPaid"));
        transactionDetails.setForWhom(b.getString("paidForWhom"));
        transactionDetails.setAmount(b.getString("amount"));
        details.add(transactionDetails);
        Log.i("ComputeActivity", details.get(0).whoPaid);
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
