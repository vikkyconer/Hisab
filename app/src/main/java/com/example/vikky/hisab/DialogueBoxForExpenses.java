package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 7/1/15.
 */
public class DialogueBoxForExpenses extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    View view;
    //    EditText inputName;
    Map<String, String> placeData;
    BehaviorSubject<Map<String, String>> whoPaid = BehaviorSubject.create();
    Button buttonYes, buttonNo;
    EditText amount, description;
    Spinner inputWhopaid, inputPaidForWhom;
    ArrayList<String> friends = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;
    String friendWhoPaid, friendPaidForWhom;
    boolean check = false;

    static DialogueBoxForExpenses newInstance() {
        Log.i("DialogueForAddFriendss", "newInstance called");
        DialogueBoxForExpenses dialogue = new DialogueBoxForExpenses();

        return dialogue;
    }

    public Observable<Map<String, String>> inputPlaceName(ArrayList<String> friends) {
        Log.i("DialogueForAddFriendss", "Observable called");
//        Log.i("DialogueForAddFriends", String.valueOf(frineds));
        this.friends = friends;
        return whoPaid.asObservable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogue_box_for_expenses, container, false);


        Log.i("DialogueForAddFriendss", "onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
        defaultConfiguration();
        setEventsForViews();
    }

    private void setEventsForViews() {
        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
//        inputWhopaid.setOnItemClickListener(this);
//        inputPaidForWhom.setOnItemClickListener(this);
//        inputWhopaid.setOnClickListener(this);
//        inputPaidForWhom.setOnClickListener(this);
    }

    private void defaultConfiguration() {

    }

    private void initializeViews() {
        buttonYes = (Button) view.findViewById(R.id.yes);
        buttonNo = (Button) view.findViewById(R.id.no);
        placeData = new HashMap<>();
        amount = (EditText) view.findViewById(R.id.amount_paid);
        description = (EditText) view.findViewById(R.id.input_description);
        inputWhopaid = (Spinner) view.findViewById(R.id.input_who_paid);
        inputPaidForWhom = (Spinner) view.findViewById(R.id.input_paid_for_whom);
        stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, this.friends);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputWhopaid.setAdapter(stringArrayAdapter);
        inputPaidForWhom.setAdapter(stringArrayAdapter);
        stringArrayAdapter.notifyDataSetChanged();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                if (!isValid()) {
                    return;
                }
                whoPaid.onNext(mapTransactionDetails());
                dismiss();
                break;
            case R.id.no:
                Log.i("DialogueForAddFriendss", "in else");
                dismiss();
                break;
            case R.id.input_who_paid:
                check = true;
                inputWhopaid.setOnItemClickListener(this);
                break;
            case R.id.paid_for_whom:
                check = true;
                inputPaidForWhom.setOnItemClickListener(this);
                break;
        }
    }

    private Map<String, String> mapTransactionDetails() {
        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("whoPaid", friendWhoPaid);
        transactionDetails.put("paidForWhom", friendPaidForWhom);
        transactionDetails.put("amount", amount.getText().toString());
        transactionDetails.put("description", description.getText().toString());
        return transactionDetails;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        if (check == false) {
            friendWhoPaid = inputWhopaid.getItemAtPosition(i).toString();
        } else {
            friendPaidForWhom = inputPaidForWhom.getItemAtPosition(i).toString();
        }
    }

    private boolean isValid() {
        String msg = "";
        if (amount.getText().length() == 0) {
            Log.i("DialogueForAddFriendss", "in isValid");
            msg = "please specify amount";
        }
        if (description.getText().length() == 0) {
            msg = "please specify description";
        }
        if (msg.length() != 0) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
        return msg.length() == 0;
    }
}