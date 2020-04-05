package com.example.earthquakereportapp;

public class Earthquake {

    private String mMagnitude;
    private String mLocation;
    private String mDate;
    private String mUrl;

    public Earthquake(String mMagnitude, String mLocation, String mDate, String mUrl) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.mDate = mDate;
        this.mUrl = mUrl;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmDate() {
        return mDate;
    }
}
