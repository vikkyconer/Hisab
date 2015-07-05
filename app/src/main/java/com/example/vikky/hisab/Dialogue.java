package com.example.vikky.hisab;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikky on 6/30/15.
 */
public class Dialogue {
    LayoutInflater layoutInflater;
    View view;
    EditText inputPlace;
    String place;
    String date;
    Map<String, String> placeData;

    public Map<String, String> addPlace(Context context) {
        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.dialogue_box, null);
        placeData = new HashMap<>();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(view);
        inputPlace = (EditText) view.findViewById(R.id.enter_place);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                place = String.valueOf(inputPlace.getText());
                                date = null;
                                placeData.put("placeName", place);
                                placeData.put("placeDate", date);
//                                places.add(new Place(String.valueOf(userInput.getText()), null, null, null, null));
//                                adapter.notifyDataSetChanged();
                                Log.i("Notes", "before calling onItemOnClick");
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return placeData;
    }
}

