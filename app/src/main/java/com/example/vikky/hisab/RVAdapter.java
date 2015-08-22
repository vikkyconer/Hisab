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

import java.util.Collections;
import java.util.List;

/**
 * Created by vikky on 7/2/15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> implements ItemTouchHelperAdapter {

    List<Place> placeList;
    static Context context;
    ArrayAdapter<String> friendsAdapter;
    ListView colorListView;
    DatabaseHelper db;

    public RVAdapter(List<Place> placeList, Context context) {
        this.placeList = placeList;
        this.context = context;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        db = new DatabaseHelper(context);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int i) {

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
        return placeList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(placeList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(placeList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        placeList.remove(position);
//        db.deletePlace(position + 1);
        notifyItemRemoved(position);
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        CardView cv;
        TextView venueName;
        TextView venueDate;
        TextView daysAgo;
        RelativeLayout backgroundColor;
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

            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
//            Toast.makeText(context, place.getPlaceName(), Toast.LENGTH_SHORT).show();
            return true;
        }


    }

}
