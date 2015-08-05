package com.example.vikky.hisab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

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
        Intent i = getIntent();
        setExpenditureMap((Map<String, Integer>) i.getSerializableExtra("expenditureMap"));
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.blue1));
        new ComputePresenter(computeModel(), computeView());
    }

    private ComputeView computeView() {
        return (ComputeFragment) getSupportFragmentManager().findFragmentById(R.id.compute_fragment);
    }

    private ComputeModel computeModel() {
        return null;
    }
}
