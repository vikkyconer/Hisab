package com.ideabank.vikky.hisab.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideabank.vikky.hisab.Models.Friend;
import com.ideabank.vikky.hisab.R;

import java.util.ArrayList;

/**
 * Created by vikky on 7/20/15.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList data;
    Friend tempValues = null;
    LayoutInflater inflater;

    /**
     * **********  CustomAdapter Constructor ****************
     */
    /*public CustomAdapter(Context context,ArrayList<Friend> friendData) {

    }*/
    public CustomAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);

        /********** Take passed values **********/
        this.context = context;
        data = objects;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_rows, parent, false);

        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (Friend) data.get(position);

        TextView friendName = (TextView) row.findViewById(R.id.friend_name);
        ImageView friendStatus = (ImageView) row.findViewById(R.id.friend_status);


        // Set values for spinner each row
        friendName.setText(tempValues.getName());
        tempValues.setStatus(true);
//        friendStatus.setImageResource(R.drawable.uncheck);


        return row;
    }
}