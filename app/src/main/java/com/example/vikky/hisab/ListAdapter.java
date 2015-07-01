package com.example.vikky.hisab;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by vikky on 6/29/15.
 */
public class ListAdapter extends BaseAdapter {
    Context context;
    Boolean status = false;
    LinkedList<Place> places;

    public ListAdapter(Context context, LinkedList<Place> places) {
        this.context = context;
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int i) {
        return places.get(i);
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
            view = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();

            viewHolder.placesNameText = (TextView) view.findViewById(R.id.place_name);
            viewHolder.placePosition = (TextView) view.findViewById(R.id.place_item_number);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        Place place = places.get(i);
        viewHolder.placesNameText.setText(place.getPlaceName());
        viewHolder.placePosition.setText(i + 1 + "");
        return view;
    }

    class ViewHolder {
        TextView placesNameText;
        TextView placePosition;
    }
}
