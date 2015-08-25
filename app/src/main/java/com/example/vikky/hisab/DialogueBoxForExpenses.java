package com.example.vikky.hisab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
public class DialogueBoxForExpenses extends DialogFragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {
    View view;
    Map<String, String> placeData;
    BehaviorSubject<Map<String, String>> whoPaid = BehaviorSubject.create();
    RelativeLayout cancel, ok;
    EditText amount, description;
    int friendWhoPaidIndex;
    Spinner inputWhopaid;
    ArrayList<Friend> customSpinnerFriendsList = new ArrayList<Friend>();
    ArrayList<String> friends = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;
    String friendWhoPaid;
    CustomAdapter adapter;
    MultiSelectionSpinner inputPaidForWhom;

    static DialogueBoxForExpenses newInstance() {
        DialogueBoxForExpenses dialogue = new DialogueBoxForExpenses();
        return dialogue;
    }

    public Observable<Map<String, String>> inputPlaceName(ArrayList<String> friends) {
        this.friends = friends;
        setListData(friends);
        return whoPaid.asObservable();
    }

    private void setListData(ArrayList<String> friends) {
        for (int i = 0; i < friends.size(); i++) {
            final Friend friend = new Friend();
            friend.setName(friends.get(i));
            friend.setStatus(false);
            customSpinnerFriendsList.add(friend);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogue_box_for_expenses, container, false);
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
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        inputWhopaid.setOnItemSelectedListener(this);
//        String s = inputPaidForWhom.getSelectedItemsAsString();
//        Log.i("arrayList", s);


    }


    private void defaultConfiguration() {

    }

    private void initializeViews() {
        ok = (RelativeLayout) view.findViewById(R.id.ok);
        cancel = (RelativeLayout) view.findViewById(R.id.cancel);
        placeData = new HashMap<>();
        amount = (EditText) view.findViewById(R.id.amount_paid);
        description = (EditText) view.findViewById(R.id.input_description);
        inputWhopaid = (Spinner) view.findViewById(R.id.input_who_paid);
        inputPaidForWhom = (MultiSelectionSpinner) view.findViewById(R.id.mySpinner1);
        stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, this.friends);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter = new CustomAdapter(getActivity(), R.layout.spinner_rows, customSpinnerFriendsList);
        inputWhopaid.setAdapter(stringArrayAdapter);
        inputPaidForWhom.setItems(friends);
        stringArrayAdapter.notifyDataSetChanged();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                if (!isValid()) {
//                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return;
                }
                whoPaid.onNext(mapTransactionDetails());
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
            case R.id.mySpinner1:
                String s = inputPaidForWhom.getSelectedItemsAsString();
                Log.i("Notes", "inOnClick ofMultiSpinner");
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                break;

        }
    }

    private Map<String, String> mapTransactionDetails() {
        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("whoPaid", friendWhoPaid);
        transactionDetails.put("amount", amount.getText().toString());
        transactionDetails.put("friendWhoPaidIndex", String.valueOf(friendWhoPaidIndex));
        transactionDetails.put("description", description.getText().toString().toUpperCase());
        return transactionDetails;
    }


    private boolean isValid() {
        String msg = "";

        if (amount.getText().length() == 0 || description.getText().length() == 0 || spinnerIsEmpty()) {
            msg = "You Might Forget Some Field";
        }
        if (msg.length() != 0) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
        return msg.length() == 0;
    }

    private boolean spinnerIsEmpty() {
        for (int i = 0; i < MultiSelectionSpinner.mSelection.length; i++) {
            if (MultiSelectionSpinner.mSelection[i] == true)
                return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.input_who_paid) {
            friendWhoPaid = inputWhopaid.getSelectedItem().toString();
            friendWhoPaidIndex = inputWhopaid.getSelectedItemPosition();
//            friendWhoPaid = friendWhoPaid.substring(0, 1).toUpperCase() + friendWhoPaid.substring(1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (parent.getId() == R.id.input_who_paid) {
            friendWhoPaid = inputWhopaid.getItemAtPosition(0).toString();
        }
    }
}