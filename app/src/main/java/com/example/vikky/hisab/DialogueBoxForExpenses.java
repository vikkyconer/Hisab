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
    //    EditText inputName;
    Map<String, String> placeData;
    BehaviorSubject<Map<String, String>> whoPaid = BehaviorSubject.create();
    RelativeLayout cancel, ok;
    EditText amount, description;
    Spinner inputWhopaid, inputPaidForWhom;
    ArrayList<String> friends = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;
    String friendWhoPaid;
    String friendPaidForWhom;
    boolean[] selection = null;

    static DialogueBoxForExpenses newInstance() {
        Log.i("DialogueBoxForExpenses", "newInstance called");
        DialogueBoxForExpenses dialogue = new DialogueBoxForExpenses();

        return dialogue;
    }

    public Observable<Map<String, String>> inputPlaceName(ArrayList<String> friends) {
        Log.i("DialogueBoxForExpenses", "Observable called");
//        Log.i("DialogueForAddFriends", String.valueOf(frineds));
        this.friends = friends;
        return whoPaid.asObservable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogue_box_for_expenses, container, false);


        Log.i("DialogueBoxForExpenses", "onCreateView");
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
        inputPaidForWhom.setOnItemSelectedListener(this);
//        inputWhopaid.setOnItemClickListener(this);
//        inputPaidForWhom.setOnItemClickListener(this);
//        inputWhopaid.setOnClickListener(this);
//        inputPaidForWhom.setOnClickListener(this);
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
            case R.id.ok:
                if (!isValid()) {
                    return;
                }
                whoPaid.onNext(mapTransactionDetails());
                dismiss();
                break;
            case R.id.cancel:
                Log.i("DialogueBoxForExpenses", "in else");
                dismiss();
                break;
        }
    }

    private Map<String, String> mapTransactionDetails() {
        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("whoPaid", friendWhoPaid);
        transactionDetails.put("paidForWhom", friendPaidForWhom);
        transactionDetails.put("amount", amount.getText().toString());
        transactionDetails.put("description", description.getText().toString().toUpperCase());
        return transactionDetails;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.input_who_paid) {
            friendWhoPaid = inputWhopaid.getSelectedItem().toString();
            friendWhoPaid = friendWhoPaid.substring(0, 1).toUpperCase() + friendWhoPaid.substring(1);
            Log.i("DialogueBoxExpenses", "in if");
        } else if (parent.getId() == R.id.input_paid_for_whom) {
            friendPaidForWhom = inputPaidForWhom.getSelectedItem().toString();
            friendPaidForWhom = friendPaidForWhom.substring(0, 1).toUpperCase() + friendPaidForWhom.substring(1);
            Log.i("DialogueBoxExpenses", "in else");

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (parent.getId() == R.id.input_who_paid) {
            friendWhoPaid = inputWhopaid.getItemAtPosition(0).toString();
        } else if (parent.getId() == R.id.input_paid_for_whom) {
            friendPaidForWhom = inputPaidForWhom.getItemAtPosition(0).toString();
        }
    }

   /* @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

        if (selection != null && which < selection.length) {
            selection[which] = isChecked;

            stringArrayAdapter.clear();
            stringArrayAdapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }
    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(friendPaidForWhom, selection, this);
        builder.show();
        return true;
    }*/
}