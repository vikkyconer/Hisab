package com.ideabank.vikky.hisab;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Map;


public class ComputeActivity extends ActionBarActivity {
    Map<String, Integer> expenditureMap;

    public Map<String, Integer> getExpenditureMap() {
        return expenditureMap;
    }

    public void setExpenditureMap(Map<String, Integer> expenditureMap) {
        this.expenditureMap = expenditureMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent i = getIntent();
        setExpenditureMap((Map<String, Integer>) i.getSerializableExtra("expenditureMap"));

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        setTitle("Result");
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue1));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        new ComputePresenter(computeModel(), computeView());
    }

    private ComputeView computeView() {
        return (ComputeFragment) getSupportFragmentManager().findFragmentById(R.id.compute_fragment);
    }

    private ComputeModel computeModel() {
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_compute, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_pay:
                Toast.makeText(this,"Coming Soon!!",Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
