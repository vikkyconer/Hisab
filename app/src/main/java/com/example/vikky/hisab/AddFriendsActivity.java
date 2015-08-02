package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class AddFriendsActivity extends ActionBarActivity {

    TransactionDetails details;

    public TransactionDetails getDetails() {
        return details;
    }

    public void setDetails(TransactionDetails details) {
        this.details = details;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        Log.i("AddFriendsActivity", "in onCreate");
        getSupportActionBar().hide();

        new AddFriendsPresenter(addFriendsView(), addFriendsModel());
    }

    private AddFriendsModel addFriendsModel() {
        return null;
    }

    private AddFriendsView addFriendsView() {
        return (AddFriendsFragment) getSupportFragmentManager().findFragmentById(R.id.add_friends_fragment);
    }
}
