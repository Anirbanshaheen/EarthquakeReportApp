package com.example.earthquakereportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();



//        earthquakes.add(new Earthquake("5","San fransisco","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","London","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Tokyo","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Mexico City","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Moscow","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Paris","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Dhaka","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Chittagong","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Feni","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Bangladesh","Feb 5,2020"));
//
//        earthquakes.add(new Earthquake("5","San fransisco","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","London","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Tokyo","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Mexico City","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Moscow","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Paris","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Dhaka","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Chittagong","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Feni","Feb 5,2020"));
//        earthquakes.add(new Earthquake("5","Bangladesh","Feb 5,2020"));

        ListView earthquakeListView = findViewById(R.id.list);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Earthquake currentEarthquake = (Earthquake) adapterView.getItemAtPosition(i);
                Uri earthUrl = Uri.parse(currentEarthquake.getmUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW,earthUrl);
                startActivity(webIntent);
            }
        });

        EarthquakeAdapter adapter = new EarthquakeAdapter(this,earthquakes);

        earthquakeListView.setAdapter(adapter);

    }
}
