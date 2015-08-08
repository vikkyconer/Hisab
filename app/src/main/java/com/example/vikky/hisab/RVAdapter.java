package com.example.vikky.hisab;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by vikky on 7/2/15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    List<Place> placeList;
    Context context;
    ArrayAdapter<String> friendsAdapter;
    ListView colorListView;

    public RVAdapter(List<Place> placeList, Context context) {
        this.placeList = placeList;
        this.context = context;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int i) {
        initialize(holder, i);
        setEventsForViews(holder);
    }

    private void initialize(PersonViewHolder holder, int i) {
        holder.venueName.setText(placeList.get(i).getPlaceName().substring(0, 1).toUpperCase() + placeList.get(i).getPlaceName().substring(1));
        holder.venueDate.setText(placeList.get(i).getPlaceDate());
        holder.daysAgo.setText(String.valueOf(placeList.get(i).getDaysAgo()));
//        String color = placeList.get(i).getBackgroundColor();
//        holder.backgroundColor.setBackgroundColor(0xff0c85b9);
    }

    private void setEventsForViews(PersonViewHolder holder) {
        holder.cv.setOnClickListener(this);
        holder.cv.setOnLongClickListener(this);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    @Override
    public void onClick(View v) {
        Navigator.toAddFriends(context);
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(context, "Hey Watch Out", Toast.LENGTH_LONG).show();

        return true;
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView venueName;
        TextView venueDate;
        TextView daysAgo;
        RelativeLayout backgroundColor;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            venueName = (TextView) itemView.findViewById(R.id.venue_name);
            daysAgo = (TextView) itemView.findViewById(R.id.days_ago);
            venueDate = (TextView) itemView.findViewById(R.id.venue_date);
            backgroundColor = (RelativeLayout) itemView.findViewById(R.id.background_color);
        }
    }

}
