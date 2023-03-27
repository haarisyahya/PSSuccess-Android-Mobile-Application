package com.example.project;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class Node {
    String name;
    String formattedAddress;
    double lat;
    double lng;
    Location location;

    public Node() {
        this.location = new Location("");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLocation(double lat, double lng) {
        this.location.setLongitude(lat);
        this.location.setLongitude(lng);
    }
}
