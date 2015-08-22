package com.example.vikky.hisab;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by vikky on 7/2/15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    List<Place> placeList;
    static Context context;
    ArrayAdapter<String> friendsAdapter;
    ListView colorListView;
    static int position;

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
        holder.place = placeList.get(i);
        initialize(holder, i);

    }

    private void initialize(PersonViewHolder holder, int i) {
        holder.venueName.setText(placeList.get(i).getPlaceName().substring(0, 1).toUpperCase() + placeList.get(i).getPlaceName().substring(1));
        holder.venueDate.setText(placeList.get(i).getPlaceDate());
        holder.daysAgo.setText(String.valueOf(placeList.get(i).getDaysAgo()));
        if (placeList.get(i).getNoOfPeopleWent() > 0) {
            holder.noOfPeopleWentContainer.setVisibility(View.VISIBLE);
            holder.noOfPeopleWent.setText(placeList.get(i).getNoOfPeopleWent());

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        Log.i("Notes", String.valueOf(placeList.size()));
        position = placeList.size();
        return placeList.size();
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        CardView cv;
        TextView venueName;
        TextView venueDate;
        TextView daysAgo;
        RelativeLayout backgroundColor;
        public Place place;
        LinearLayout noOfPeopleWentContainer;
        TextView noOfPeopleWent;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            venueName = (TextView) itemView.findViewById(R.id.venue_name);
            daysAgo = (TextView) itemView.findViewById(R.id.days_ago);
            venueDate = (TextView) itemView.findViewById(R.id.venue_date);
            backgroundColor = (RelativeLayout) itemView.findViewById(R.id.background_color);
            noOfPeopleWentContainer = (LinearLayout) itemView.findViewById(R.id.no_of_people_container);
            noOfPeopleWent = (TextView) itemView.findViewById(R.id.no_of_people_went);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("Bind", String.valueOf(place.getPlaceId()));
            if (place.getPlaceId() == 0)
                Navigator.toAddFriends(context, position);
            else
                Navigator.toAddFriends(context, place.getPlaceId());

        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(context, place.getPlaceName(), Toast.LENGTH_SHORT).show();
            return true;
        }


    }

}
