package edu.drexel.cs.ptn32.project;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by PhucNgo on 9/1/15.
 */
public class SeptaViewAll extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {
    public SeptaViewAll() {
    }

    @Override
    protected ArrayList<ArrayList<String>> doInBackground(Void... params) {
        try {
            String connString = "http://www3.septa.org/hackathon/TransitViewAll/";

            URL url = new URL(connString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            BufferedReader streamReader = new BufferedReader(new InputStreamReader((InputStream) conn.getContent()));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            JSONObject rootObj = new JSONObject(responseStrBuilder.toString());

            ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
            ArrayList<String> listLat = new ArrayList<String>();
            ArrayList<String> listLng = new ArrayList<String>();
            ArrayList<String> listVehicleID = new ArrayList<String>();
            ArrayList<String> listDirection = new ArrayList<String>();
            ArrayList<String> listDest = new ArrayList<String>();

            for (Iterator iterator = rootObj.keys(); iterator.hasNext(); ) {
                String key = (String) iterator.next();

                JSONArray rootArray = rootObj.getJSONArray(key);

                for (int i = 0; i < rootArray.length(); i++) {
                    JSONObject route = rootArray.getJSONObject(i);

                    for (Iterator it = route.keys(); it.hasNext(); ) {
                        String k = (String) it.next();

                        JSONArray routeArray = route.getJSONArray(k);

                        for (int j = 0; j < routeArray.length(); j++) {
                            String lat = routeArray.getJSONObject(j).getString("lat");

                            String lng = routeArray.getJSONObject(j).getString("lng");

                            String vehicleID = routeArray.getJSONObject(j).getString("VehicleID");

                            String dest = routeArray.getJSONObject(j).getString("destination");

                            String direction = routeArray.getJSONObject(j).getString("Direction");

                            listLat.add(lat);
                            listLng.add(lng);
                            listVehicleID.add("Route: " + k);
                            listDest.add("Dest: " + dest);
                            listDirection.add("Direction: " + direction);
                        }
                    }
                }
            }

            list.add(listLat);
            list.add(listLng);
            list.add(listVehicleID);
            list.add(listDirection);
            list.add(listDest);

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<String>> result) {
        SeptaTrainView septaTrainView = new SeptaTrainView();
        septaTrainView.execute(result);
    }
}
