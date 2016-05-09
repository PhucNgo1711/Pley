package edu.drexel.cs.ptn32.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by PhucNgo on 8/28/15.
 */
public class GetBitmapFromURL extends AsyncTask<ArrayList<ArrayList<String>>, Void, ArrayList<ArrayList<Bitmap>>> {
    ArrayList<ArrayList<String>> list;

    private NearbyActivity nearbyActivity = null;
    private SearchActivity searchActivity = null;

    public GetBitmapFromURL(NearbyActivity nearbyActivity) {
        this.nearbyActivity = nearbyActivity;
        list = new ArrayList<ArrayList<String>>();
    }

    public GetBitmapFromURL(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
        list = new ArrayList<ArrayList<String>>();
    }

    @Override
    protected ArrayList<ArrayList<Bitmap>> doInBackground(ArrayList<ArrayList<String>>... params) {
        list = params[0];
        ArrayList<String> listImageURL = list.get(0);
        ArrayList<String> listRatingImage = list.get(3);

        ArrayList<Bitmap> listImageBmp = new ArrayList<Bitmap>();
        ArrayList<Bitmap> listRatingBmp = new ArrayList<Bitmap>();

        try {
            for (int i = 0; i < listImageURL.size(); i++) { // go through each url
                URL url = new URL(listImageURL.get(i));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream inputImage = connection.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(inputImage); // get bitmap image from url

                listImageBmp.add(image);
                inputImage.close();

                url = new URL(listRatingImage.get(i));
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream inputRating = connection.getInputStream();
                Bitmap rating = BitmapFactory.decodeStream(inputRating); // get bitmap image from url

                listRatingBmp.add(rating);
                inputRating.close();
            }

            ArrayList<ArrayList<Bitmap>> listBmp = new ArrayList<ArrayList<Bitmap>>();
            listBmp.add(listImageBmp);
            listBmp.add(listRatingBmp);

            return listBmp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<Bitmap>> result) {
        if (searchActivity == null) {
            DisplayListView displayListView = new DisplayListView(nearbyActivity, list, result);
            displayListView.updateListNearby();
        }
        else {
            DisplayListView displayListView = new DisplayListView(searchActivity, list, result);
            displayListView.updateListSearch();

        }
    }
}
