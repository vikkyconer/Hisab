package com.ideabank.vikky.hisab.Models;

/**
 * Created by vikky on 6/29/15.
 */
public class Place {
    private int placeId;
    private String placeName;
    private int daysAgo;
    private String placeDate;
    private int noOfPeopleWent;
//    private String createdAt;

    public int getDaysAgo() {
        return daysAgo;
    }

    public void setDaysAgo(int daysAgo) {
        this.daysAgo = daysAgo;
    }

    public Place() {
    }

    public Place(String placeName, int daysAgo, String placeDate, int noOfPeopleWent) {
        this.placeName = placeName;
        this.daysAgo = daysAgo;
        this.placeDate = placeDate;
        this.noOfPeopleWent = noOfPeopleWent;
    }

    public Place(int placeId, String placeName, int daysAgo, String placeDate, int noOfPeopleWent) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.daysAgo = daysAgo;
        this.placeDate = placeDate;
        this.noOfPeopleWent = noOfPeopleWent;
    }

//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }

    public int getPlaceId() {
        return placeId;
    }

    public String getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(String placeDate) {
        this.placeDate = placeDate;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getNoOfPeopleWent() {
        return noOfPeopleWent;
    }

    public void setNoOfPeopleWent(int noOfPeopleWent) {
        this.noOfPeopleWent = noOfPeopleWent;
    }
}
