package com.example.vikky.hisab;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by vikky on 7/1/15.
 */
public class DialogueBoxForExpenses extends Dialog {
    private ArrayList<String> friendsList;
    private Context mContext;
    private Spinner inputWhoPaid;


    public interface DialogListener {
        public void ready(int n);

        public void cancelled();
    }

    private DialogListener mReadyListener;

    public DialogueBoxForExpenses(Context context, ArrayList<String> list) {
        super(context);
//        mReadyListener = readyListener;
        mContext = context;
        friendsList = new ArrayList<String>();
        friendsList = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialogue_box_for_expenses);
        inputWhoPaid = (Spinner) findViewById(R.id.input_who_paid);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, friendsList);
        inputWhoPaid.setAdapter(adapter);
    }
}