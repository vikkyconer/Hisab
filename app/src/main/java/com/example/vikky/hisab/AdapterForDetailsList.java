package com.example.vikky.hisab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by vikky on 7/14/15.
 */
public class AdapterForDetailsList extends BaseAdapter {
    Context context;
    String whoHasToPay, whomToPay, howMuch;
    public static LayoutInflater layoutInflater = null;

    public AdapterForDetailsList(Context context, String whoHasToPay, String howMuch, String whomToPay) {
        this.context = context;
        this.whoHasToPay = whoHasToPay;
        this.howMuch = howMuch;
        this.whomToPay = whomToPay;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return whoHasToPay.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = layoutInflater.inflate(R.layout.transaction_details_list_item, null);
        holder.whoHasToPay = (TextView) rowView.findViewById(R.id.who_has_to_pay);
        holder.howMuch = (TextView) rowView.findViewById(R.id.how_much);
        holder.whomToPay = (TextView) rowView.findViewById(R.id.whom_to_pay);
        holder.whoHasToPay.setText(this.whoHasToPay);
        holder.howMuch.setText(this.howMuch);
        holder.whomToPay.setText(this.whomToPay);
        return rowView;
    }

    public class Holder {
        TextView whoHasToPay, whomToPay, howMuch;
/*
        Holder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            venueName = (TextView) itemView.findViewById(R.id.venue_name);
            venueDate = (TextView) itemView.findViewById(R.id.venue_date);
            addFriendsIcon = (ImageView) itemView.findViewById(R.id.add_friends_icon);
            changeBackground = (ImageView) itemView.findViewById(R.id.change_background);
        }*/
    }
}
