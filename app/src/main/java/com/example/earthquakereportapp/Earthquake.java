package com.example.earthquakereportapp;

import org.json.JSONObject;

public class Earthquake {

    private float mMagnitude;
    private String mLocation;
    private int mDate;

    public Earthquake(float mMagnitude, String mLocation, int mDate) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.mDate = mDate;
    }

    public float getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public int getmDate() {
        return mDate;
    }
}
