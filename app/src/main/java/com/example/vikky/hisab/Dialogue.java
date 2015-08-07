package com.example.vikky.hisab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    long delay;
    long daysAgo;
    Calendar cal;
    Date date;
    DateFormat dateFormat;
    BehaviorSubject<Map<String, String>> placeName = BehaviorSubject.create();
    RelativeLayout cancel, ok;
    TextView inputDate;

    static Dialogue newInstance() {
        Log.i("Dialogue", "newInstance called");
        Dialogue dialogue = new Dialogue();
        return dialogue;
    }

    public Observable<Map<String, String>> inputPlaceName() {
        Log.i("Dialogue", "Observable inputPlace");
        return placeName.asObservable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Dialogue", "onCreateView");
        view = inflater.inflate(R.layout.dialogue_box, container, false);
        cancel = (RelativeLayout) view.findViewById(R.id.cancel);
        ok = (RelativeLayout) view.findViewById(R.id.ok);
        placeData = new HashMap<>();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        cal = Calendar.getInstance();
        inputDate = (TextView) view.findViewById(R.id.date);
        inputPlace = (EditText) view.findViewById(R.id.enter_place);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Dialogue", "onActivityCreated called");
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        inputDate.setOnClickListener(this);
    }

    public void onPlaceNameSet() {
        Log.i("Dialogue", "onNext called");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ok) {
            Log.i("Dialogue", String.valueOf(inputPlace.getText()));
            if (isValid()) {
                placeData.put("placeName", String.valueOf(inputPlace.getText()));
                placeName.onNext(placeData);
                dismiss();
            } else {
                Toast.makeText(getActivity(), "Enter Venue and Date", Toast.LENGTH_LONG).show();
            }

        } else if (v.getId() == R.id.cancel) {
            Log.i("Dialogue", "in else");
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
                    this.date = new Date();
                    delay = (this.date.getTime() - date1.getTime());
                    if (delay < 0) {
                        Toast.makeText(getActivity(), "incorrect date", Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                    daysAgo = delay / (24 * 60 * 60 * 1000);
                    placeData.put("daysAgo", String.valueOf(daysAgo));
                    Log.i("date1", date1 + "");
                    this.inputDate.setText(simpleDateFormat2.format(date1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    private boolean isValid() {
        if (inputPlace.getText().length() == 0 ) {
            return false;
        }
        return true;
    }
}

