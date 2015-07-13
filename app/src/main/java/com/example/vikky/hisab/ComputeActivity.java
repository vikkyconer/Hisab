package com.example.vikky.hisab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class ComputeActivity extends ActionBarActivity {

    TransactionDetails details = null;
    String amount;

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
        Bundle b = getIntent().getExtras();
        setDetails(b.getParcelable("transactionDetails"));
        setAmount(i.getStringExtra("amount"));
        Log.i("ComputeActivity", getAmount());
    }
}
