package edu.drexel.cs.ptn32.project;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.ArrayList;

/**
 * This shows how to create a simple activity with streetview
 */
public class DisplayStreetViewActivity extends FragmentActivity {

    // George St, Sydney
    //private static final LatLng SYDNEY = new LatLng(-39.958159, -75.319437);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_street_view);

        ArrayList<String> listCoordinates = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //instruction = extras.getString("Instruction");
            listCoordinates = extras.getStringArrayList("coordinate");
        }

        String[] c = listCoordinates.get(0).split(","); // split string from format lat,long,title
        final double lat = Double.parseDouble(c[0]);
        final double lon = Double.parseDouble(c[1]);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);

        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        // Only set the panorama to SYDNEY on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            //panorama.setPosition(SYDNEY);
                            panorama.setPosition(new LatLng(lat, lon));
                        }
                    }
                });
    }

    public void streetViewGoBack(View view) {
        this.finish();
    }
}


