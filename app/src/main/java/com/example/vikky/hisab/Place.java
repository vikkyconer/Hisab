package com.example.vikky.hisab;

import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vikky on 6/29/15.
 */
public class Place {
    private String placeId;
    private String placeName;
    private LinkedList<Friends> friends;
    private List<Place> placeList;
    private ImageView placePhoto;
    private String placeDate;

    public String getPlaceId() {
        return placeId;
    }

    public ImageView getPlacePhoto() {
        return placePhoto;
    }

    public void setPlacePhoto(ImageView placePhoto) {
        this.placePhoto = placePhoto;
    }

    public String getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(String placeDate) {
        this.placeDate = placeDate;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public LinkedList<Friends> getFriends() {
        return friends;
    }

    public void setFriends(LinkedList<Friends> friends) {
        this.friends = friends;
    }
}
