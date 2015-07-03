package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class AddFriendsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        Log.i("Notes", "in AddFriendsActivity");

//        new AddFriendsPresenter(addFriendsView(), addFriendsModel());
    }

    private AddFriendsModel addFriendsModel() {
        return null;
    }

    private AddFriendsView addFriendsView() {
        return null;
    }
}
