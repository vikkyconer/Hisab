package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


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
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue1));
        new AddFriendsPresenter(addFriendsView(), addFriendsModel());
    }

    private AddFriendsModel addFriendsModel() {
        return null;
    }

    private AddFriendsView addFriendsView() {
        return (AddFriendsFragment) getSupportFragmentManager().findFragmentById(R.id.add_friends_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                Navigator.toMainScreen(this);
                return true;
            case android.R.id.home:
                Navigator.toMainScreen(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
