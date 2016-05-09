package edu.drexel.cs.ptn32.project;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class NearbyActivity extends AppCompatActivity {
    public CustomAdapter customAdapter;
    private ArrayList<YelpData> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        restaurantList = new ArrayList<YelpData>();
        ListView listView = (ListView)findViewById(R.id.nearbyListView);
        // create adapter to convert array to views
        customAdapter = new CustomAdapter(this, restaurantList);
        // attach adapter to listview
        listView.setAdapter(customAdapter);

        LocationManager locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        LocationListener locationListener = new MyLocationListener(this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_window, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void viewMaps(View view) {
        ArrayList<String> listCoordinates = new ArrayList<>();
        for (YelpData yelpItem : restaurantList) {
            String coordinates = yelpItem.getLat() + "," + yelpItem.getLng() + "," + yelpItem.getName();
            listCoordinates.add(coordinates);
        }

        Intent intent = new Intent(this, DisplayGoogleMapsActivity.class);
        intent.putExtra("coordinate", listCoordinates);
        startActivity(intent);
    }
}

/*---------- Listener class to get coordinates ------------- */
class MyLocationListener implements LocationListener {
    private NearbyActivity nearbyActivity;

    public MyLocationListener (NearbyActivity nearbyActivity) {
        this.nearbyActivity = nearbyActivity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        String lon = Double.toString(loc.getLongitude());
        String lat = Double.toString(loc.getLatitude());

        String[] coordinates = {lat, lon};

        YelpCoordinatesSearch yelpCoordinatesSearch = new YelpCoordinatesSearch(nearbyActivity);
        yelpCoordinatesSearch.execute(coordinates);
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
