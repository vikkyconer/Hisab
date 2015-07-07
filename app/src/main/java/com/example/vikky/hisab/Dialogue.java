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
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 6/30/15.
 */
public class Dialogue extends DialogFragment implements View.OnClickListener {
    LayoutInflater layoutInflater;
    View view;
    EditText inputPlace;
    String place;
    String venueDate;
    Map<String, String> placeData;
    Context context;
    BehaviorSubject<Map<String, String>> placeName = BehaviorSubject.create();
    Button buttonYes, buttonNo;
    TextView inputDate;

    static Dialogue newInstance() {
        Log.i("Notes", "newInstance called");
        Dialogue dialogue = new Dialogue();
        return dialogue;
    }

    public Observable<Map<String, String>> inputPlaceName() {
        Log.i("Notes", "Observable called");
        return placeName.asObservable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogue_box, container, false);
        buttonYes = (Button) view.findViewById(R.id.yes);
        buttonNo = (Button) view.findViewById(R.id.no);
        placeData = new HashMap<>();
        inputDate = (TextView) view.findViewById(R.id.date);
        inputPlace = (EditText) view.findViewById(R.id.enter_place);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Log.i("Notes", "onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Notes", "onActivityCreated called");
        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
        inputDate.setOnClickListener(this);
    }

    public void onPlaceNameSet() {
        Log.i("Notes", "onNext called");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.yes) {
            Log.i("Notes", String.valueOf(inputPlace.getText()));
            placeData.put("placeName", String.valueOf(inputPlace.getText()));
            placeName.onNext(placeData);
            dismiss();
        } else if (v.getId() == R.id.no) {
            Log.i("Notes", "in else");
            dismiss();
        } else if (v.getId() == R.id.date) {
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
        }
    }
}

