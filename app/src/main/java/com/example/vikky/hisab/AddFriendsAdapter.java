package com.example.vikky.hisab;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vikky on 8/25/15.
 */
public class AddFriendsAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> name;

    public AddFriendsAdapter(ArrayList<String> name, Context context) {
        this.context = context;
        this.name = name;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.show_friends_name, null);

            viewHolder = new ViewHolder();

            viewHolder.firstLetter = (TextView) view.findViewById(R.id.names_first_letter);
            viewHolder.firstName = (TextView) view.findViewById(R.id.first_name);

            view.setTag(viewHolder);

        }
        viewHolder = (ViewHolder) view.getTag();

        viewHolder.firstLetter.setText(name.get(i).substring(0, 1).toUpperCase());
        viewHolder.firstName.setText(name.get(i).substring(0, 10).concat("..."));
        return view;
    }

    class ViewHolder {
        TextView firstLetter, firstName;
    }
}
