package edu.drexel.cs.ptn32.project;

import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by PhucNgo on 9/1/15.
 */
public class SeptaTrainView extends AsyncTask<ArrayList<ArrayList<String>>, Void, ArrayList<ArrayList<String>>> {
    public SeptaTrainView() {

    }

    @Override
    protected ArrayList<ArrayList<String>> doInBackground(ArrayList<ArrayList<String>>... params) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        list = params[0];

        try {
            String connString = "http://www3.septa.org/hackathon/TrainView/";

            URL url = new URL(connString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(new InputStreamReader((InputStream) conn.getContent()));
            JsonArray rootArray = root.getAsJsonArray();

            ArrayList<String> listLat = new ArrayList<String>();
            ArrayList<String> listLon = new ArrayList<String>();
            ArrayList<String> listService = new ArrayList<String>();
            ArrayList<String> listDest = new ArrayList<String>();
            ArrayList<String> listNextStop = new ArrayList<String>();
            ArrayList<String> listSource = new ArrayList<String>();

            for (int i = 0; i < rootArray.size(); i++) {
                String lat = rootArray.get(i).getAsJsonObject().get("lat").getAsString();

                String lon = rootArray.get(i).getAsJsonObject().get("lon").getAsString();

                String service = rootArray.get(i).getAsJsonObject().get("trainno").getAsString();

                String dest = rootArray.get(i).getAsJsonObject().get("dest").getAsString();

                String nextStop = rootArray.get(i).getAsJsonObject().get("nextstop").getAsString();

                String source = rootArray.get(i).getAsJsonObject().get("SOURCE").getAsString();

                listLat.add(lat);
                listLon.add(lon);
                listService.add("Train No: " + service);
                listDest.add("Dest: " + dest);
                listNextStop.add(nextStop);
                listSource.add("Origin: " + source);
            }

            list.add(listLat);
            list.add(listLon);
            list.add(listService);
            list.add(listDest);
            list.add(listSource);

            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<String>> result) {
        Intent intent;
        if (YelpCategorySearch.getSearchActivity() != null) {
            intent = new Intent(YelpCategorySearch.getSearchActivity(), DisplaySEPTAMapsActivity.class);
        }
        else {
            intent = new Intent(YelpCoordinatesSearch.getNearbyActivity(), DisplaySEPTAMapsActivity.class);
        }

        ArrayList<String> listLat = result.get(0);
        ArrayList<String> listLon = result.get(1);
        ArrayList<String> listService = result.get(2);
        ArrayList<String> listDest = result.get(3);
        ArrayList<String> listSource = result.get(4);

        ArrayList<String> listCoordinates = new ArrayList<>();

        for (int i = 0; i < listLat.size(); i++) {
            String coordinates = listLat.get(i) + "," + listLon.get(i) + "," + listService.get(i) + "," + listDest.get(i) + "," + listSource.get(i);
            listCoordinates.add(coordinates);
        }

        intent.putExtra("coordinate", listCoordinates);

        if (YelpCategorySearch.getSearchActivity() != null) {
            YelpCategorySearch.getSearchActivity().startActivity(intent);
        }
        else {
            YelpCoordinatesSearch.getNearbyActivity().startActivity(intent);
        }

    }
}
