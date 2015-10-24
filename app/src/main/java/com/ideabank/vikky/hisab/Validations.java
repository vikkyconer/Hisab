package com.ideabank.vikky.hisab;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vikkycorner on 19/10/15.
 */
public class Validations {

    private ArrayList<String> friendsList;
    private Context context;

    public Validations(ArrayList<String> friendsList,Context context) {
        this.friendsList = friendsList;
        this.context = context;
    }

    public boolean dublicateName(String name) {
        for (int i = 0; i < friendsList.size(); i++) {
            Log.i("Notes", friendsList.get(i));
            if (name.equals(friendsList.get(i))) {
                Toast.makeText(context , "Name already exist", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public boolean nameLength(String name) {
        if (name.length() == 0) {
            return false;
        }
        return true;
    }

}
