package com.example.vikky.hisab;

import android.widget.ImageView;

import java.util.LinkedList;

/**
 * Created by vikky on 6/29/15.
 */
public class Place {
    private Integer placeId;
    private String placeName;
    private LinkedList<Friend> friends;
    private String daysAgo;
    private ImageView placePhoto;
    private String placeDate;

    public String getDaysAgo() {
        return daysAgo;
    }

    public void setDaysAgo(String daysAgo) {
        this.daysAgo = daysAgo;
    }

    public Place() {
    }

    public Place(String placeName, String daysAgo, String placeDate) {
        super();
        this.placeName = placeName;
        this.daysAgo = daysAgo;
        this.placeDate = placeDate;
    }


    public Integer getPlaceId() {
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

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public LinkedList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(LinkedList<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "Place [id=" + placeId + ",Place Name=" + placeName + "days ago=" + daysAgo + "date" + placeDate + "]";
    }
}
