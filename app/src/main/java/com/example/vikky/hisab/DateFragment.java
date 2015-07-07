package com.example.vikky.hisab;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 7/7/15.
 */
public class DateFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    BehaviorSubject<String> gameSelection = BehaviorSubject.create();

    public Observable<String> selectedDate() {
        return gameSelection.asObservable();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date, d = null, m = null, y = null;
        d = "" + day;
        m = month + 1 + "";
        y = "" + year;
        date = day + "/" + m + "/" + y;

        gameSelection.onNext(date);
    }
}
