package com.example.earthquakereportapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.earthquakereportapp.QueryUtils.USGS_REQUEST_URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private EarthquakeAdapter mAdapter;
    private TextView emptyTextView;

    // This really only comes into play if you're using multiple loaders.
    private static final int EARTHQUAKE_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyTextView = findViewById(R.id.empty_view);

//        LoaderManager.getInstance(this).initLoader(EARTHQUAKE_LOADER_ID, null, this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager.getInstance(this).initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_Internet);
        }

//        if(networkInfo == null){
//            emptyTextView.setText("No Internet Connection");
//        }else if(networkInfo.isConnected()){
//            emptyTextView.setText("No Earthquake data found");
//        }

        // Create a fake list of earthquake locations.
//        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();



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



//        earthquakeListView.setEmptyView(emptyTextView);

//        EarthquakeAdapter adapter = new EarthquakeAdapter(this,earthquakes);
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Earthquake currentEarthquake = (Earthquake) adapterView.getItemAtPosition(i);
                Earthquake currentEarthquake = mAdapter.getItem(i);

                Uri earthquakeUri = Uri.parse(currentEarthquake.getmUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW,earthquakeUri);
                startActivity(webIntent);
            }
        });

//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute(USGS_REQUEST_URL);

    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {

        // Create a new loader for the given URL
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);


        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // Null means object or string is not instantiated yet and isEmpty means check if a string contains no character
        if(earthquakes != null && !earthquakes.isEmpty()){
            mAdapter.addAll(earthquakes);
        }else {
            emptyTextView.setText("No Earthquake data found");
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data
        mAdapter.clear();
    }

    private static class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

        private static final String LOG_TAG = EarthquakeLoader.class.getName();

        private String mUrl;

        public EarthquakeLoader(@NonNull Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Nullable
        @Override
        public List<Earthquake> loadInBackground() {
            if(mUrl == null){
                return null;
            }

            // Perform the network request, parse the response, and extract a list of earthquakes.
            List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
            return earthquakes;
        }



//        @Override
//        protected ArrayList<Earthquake> doInBackground(String... urls) {
//            if(urls.length < 1 || urls[0] == null){
//                return null;
//            }
//
//            ArrayList<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
//            return result;
//
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Earthquake> data) {
//            // Clear the adapter of previous earthquake data
//            mAdapter.clear();
//
//            if(data != null && !data.isEmpty()){
//                mAdapter.addAll(data);
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_setting){
//            startActivity(new Intent(this,SettingsActivity.class));
            Toast.makeText(this, "Under Construction", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
