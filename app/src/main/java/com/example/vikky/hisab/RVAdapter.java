package com.example.vikky.hisab;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vikky on 7/2/15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> implements View.OnClickListener {

    List<Place> placeList;
    View v;

    public RVAdapter(List<Place> placeList) {
        this.placeList = placeList;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int i) {
        holder.venueName.setText(placeList.get(i).getPlaceName());
        holder.venueAddress.setText("In Pune");
        holder.addFriendsIcon.setOnClickListener(this);
        holder.changeBackground.setOnClickListener(this);
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
        if (v.getId() == R.id.add_friends_icon) {
//            Navigator.toAddFriends(this.v.getContext());
        } else if (v.getId() == R.id.change_background) {
            Log.i("Motes", "changeBackground");
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView venueName;
        TextView venueAddress;
        ImageView addFriendsIcon;
        ImageView changeBackground;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            venueName = (TextView) itemView.findViewById(R.id.venue_name);
            venueAddress = (TextView) itemView.findViewById(R.id.venue_address);
            addFriendsIcon = (ImageView) itemView.findViewById(R.id.add_friends_icon);
            changeBackground = (ImageView) itemView.findViewById(R.id.change_background);
        }
    }

}
