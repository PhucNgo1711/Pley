package edu.drexel.cs.ptn32.project;

import android.os.AsyncTask;
import android.widget.TextView;

import com.temboo.Library.Yelp.SearchByCategory;
import com.temboo.Library.Yelp.SearchByCategory;
import com.temboo.core.TembooSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PhucNgo on 8/28/15.
 */
public class YelpCategorySearch extends AsyncTask<String[], Void, String> {
    final double TO_MILES = 0.000621371;
    ArrayList<ArrayList<String>> list;
    ArrayList<String> listImageURL, listName, listDistance, listRatingImage, listRatingNum, listAddress, listCategories, listLat, listLng;

    private static SearchActivity searchActivity = null;

    public YelpCategorySearch(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
        list = new ArrayList<ArrayList<String>>();
        listImageURL = new ArrayList<>();
        listName = new ArrayList<>();
        listDistance = new ArrayList<>();
        listRatingImage = new ArrayList<>();
        listRatingNum = new ArrayList<>();
        listAddress = new ArrayList<>();
        listCategories = new ArrayList<>();
        listLat = new ArrayList<>();
        listLng = new ArrayList<>();
    }

    // Override the doInBackground method
// This method makes the API call to wunderground to get json data.
    @Override
    protected String doInBackground(String[]... inputs) {
        try {
            // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
            TembooSession session = new TembooSession("ptn32", "Project", "3a50a4154fb844b7afcedc1ce01a48f6");

            SearchByCategory searchByCategoryChoreo = new SearchByCategory(session);

            // Get an InputSet object for the choreo
            SearchByCategory.SearchByCategoryInputSet searchByCategoryInputs = searchByCategoryChoreo.newInputSet();

            // Set inputs
            searchByCategoryInputs.set_Category(inputs[0][0]);
            searchByCategoryInputs.set_Location(inputs[0][1]);
            searchByCategoryInputs.set_ConsumerSecret("YuwoFojeSn-sCNv7CNQp5x-rP2Y");
            searchByCategoryInputs.set_Token("QGuI-rNogemVJdesxd7eHsQ7UEwIxJed");
            searchByCategoryInputs.set_TokenSecret("gbwYPMl5kDYV2LgI1-05lM6yZVM");
            searchByCategoryInputs.set_ConsumerKey("n7M_UfDoQmXU-5Pl8HcuGw");

            // Execute Choreo
            SearchByCategory.SearchByCategoryResultSet searchByCategoryResults = searchByCategoryChoreo.execute(searchByCategoryInputs);
            return searchByCategoryResults.get_Response();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed searching for something";
        }
    }

    // Overrides the onPostExecute method. This method parses the json data and stores in database
    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject root = new JSONObject(result);
            JSONArray businesses = root.getJSONArray("businesses");
            for (int i = 0; i < businesses.length(); i++) {
                JSONObject location = businesses.getJSONObject(i).getJSONObject("location");
                JSONArray add = location.getJSONArray("display_address");
                String address = add.getString(0); // get first address

                JSONArray cat = businesses.getJSONObject(i).getJSONArray("categories");
                JSONArray c = cat.getJSONArray(0);
                String categories = c.getString(0);

                JSONObject coordinate = location.getJSONObject("coordinate");
                String lat = coordinate.getString("latitude");
                String lng = coordinate.getString("longitude");

                double dist = (double)Math.round((businesses.getJSONObject(i).getDouble("distance") * TO_MILES) * 10) / 10;
                String distance = Double.toString(dist) + " miles";

                String imageURL = "http://www.drdangottlieb.com/wp-content/uploads/2013/11/DREXEL-UNIVERSITY-Logo_full-color.jpg";
                try{imageURL = businesses.getJSONObject(i).getString("image_url");}catch (Exception e){};

                String ratingNum = businesses.getJSONObject(i).getString("review_count");
                if (ratingNum.equals("1")) { // 1 review
                    ratingNum += " review";
                } else { // more than 1 review
                    ratingNum += " reviews";
                }

                String ratingImage = businesses.getJSONObject(i).getString("rating_img_url_small");

                String name = businesses.getJSONObject(i).getString("name");

                listImageURL.add(imageURL);
                listName.add(name);
                listDistance.add(distance);
                listRatingImage.add(ratingImage);
                listRatingNum.add(ratingNum);
                listAddress.add(address);
                listCategories.add(categories);
                listLat.add(lat);
                listLng.add(lng);
            }

            list.add(listImageURL);
            list.add(listName);
            list.add(listDistance);
            list.add(listRatingImage);
            list.add(listRatingNum);
            list.add(listAddress);
            list.add(listCategories);
            list.add(listLat);
            list.add(listLng);

            GetBitmapFromURL getBitmapFromURL = new GetBitmapFromURL(searchActivity);
            getBitmapFromURL.execute(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SearchActivity getSearchActivity() {
        return searchActivity;
    }
}
