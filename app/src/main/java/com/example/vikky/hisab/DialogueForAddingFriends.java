package com.example.vikky.hisab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 7/7/15.
 */
public class DialogueForAddingFriends extends DialogFragment implements View.OnClickListener {
    LayoutInflater layoutInflater;
    View view;
    EditText inputName;
    String place;
    //    String venueDate;
    Map<String, String> placeData;
    Context context;
    BehaviorSubject<Map<String, String>> name = BehaviorSubject.create();
    Button buttonYes, buttonNo;
//    TextView inputDate;

    static DialogueForAddingFriends newInstance() {
        Log.i("AddFriends", "newInstance called");
        DialogueForAddingFriends dialogue = new DialogueForAddingFriends();
        return dialogue;
    }

    public Observable<Map<String, String>> inputPlaceName() {
        Log.i("AddFriends", "Observable called");
        return name.asObservable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogue_for_adding_friends, container, false);
        buttonYes = (Button) view.findViewById(R.id.yes);
        buttonNo = (Button) view.findViewById(R.id.no);
        placeData = new HashMap<>();
//        inputDate = (TextView) view.findViewById(R.id.date);
        inputName = (EditText) view.findViewById(R.id.enter_name);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Log.i("AddFriends", "onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("AddFriends", "onActivityCreated called");
        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
//        inputDate.setOnClickListener(this);
    }

    public void onPlaceNameSet() {
        Log.i("AddFriends", "onNext called");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.yes) {
//            Log.i("AddFriends", String.valueOf(inputName.getText()));
            if (!isValid()) {
                return;
            }
            placeData.put("friendName", String.valueOf(inputName.getText()));
            name.onNext(placeData);
            dismiss();
        } else if (v.getId() == R.id.no) {
            Log.i("AddFriends", "in else");
            dismiss();
        } /*else if (v.getId() == R.id.date) {
            DateFragment newFragment = new DateFragment();
            newFragment.show(getFragmentManager(), "timePicker");

            newFragment.selectedDate().subscribe(date -> {
                venueDate = date;
                Log.i("Date", date);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MMM-yyyy");
                placeData.put("placeDate", venueDate);
                try {
                    Date date1 = simpleDateFormat.parse(date);

                    Log.i("date1", date1 + "");
                    this.inputDate.setText(simpleDateFormat2.format(date1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            });
        }*/
    }

    private boolean isValid() {
        String msg = "";
        if (inputName.getText().length() == 0) {
            Log.i("AddFriends", "in isValid");
            msg = "please specify place name";
        } /*else if (venueDate.length() == 0) {
            msg = "please specify date";
        }*/
        if (msg.length() != 0) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
        return msg.length() == 0;
    }
}
