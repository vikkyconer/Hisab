package com.example.vikky.hisab;

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

    public String getPlaceId() {
        return placeId;
    }

    public Place(String placeName, String placeId, LinkedList<Friends> friends, List<Place> placeList) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.friends = friends;
        this.placeList = placeList;
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
