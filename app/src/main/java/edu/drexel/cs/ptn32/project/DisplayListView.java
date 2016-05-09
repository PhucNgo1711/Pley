package edu.drexel.cs.ptn32.project;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by PhucNgo on 8/28/15.
 */
public class DisplayListView {
    private NearbyActivity nearbyActivity;
    private SearchActivity searchActivity;
    private ArrayList<ArrayList<String>> list;
    private ArrayList<ArrayList<Bitmap>> listBmp;

    public DisplayListView(NearbyActivity nearbyActivity, ArrayList<ArrayList<String>> list, ArrayList<ArrayList<Bitmap>> listBmp) {
        this.nearbyActivity = nearbyActivity;
        this.list = list;
        this.listBmp = listBmp;
    }

    public DisplayListView(SearchActivity searchActivity, ArrayList<ArrayList<String>> list, ArrayList<ArrayList<Bitmap>> listBmp) {
        this.searchActivity = searchActivity;
        this.list = list;
        this.listBmp = listBmp;
    }

    public void updateListNearby() {
        ArrayList<String> listName = list.get(1), listDistance = list.get(2), listRatingNum = list.get(4), listAddress = list.get(5), listCategories = list.get(6), listLat = list.get(7), listLng = list.get(8);
        ArrayList<Bitmap> listImageBmp = listBmp.get(0), listRatingBmp = listBmp.get(1);

        for (int i = 0; i < listName.size(); i++) { // go through each row
            YelpData yelpData = new YelpData(listImageBmp.get(i), listName.get(i), listDistance.get(i), listRatingBmp.get(i), listRatingNum.get(i), listAddress.get(i), listCategories.get(i), listLat.get(i), listLng.get(i));

            nearbyActivity.customAdapter.add(yelpData);
        }
    }

    public void updateListSearch() {
        ArrayList<String> listName = list.get(1), listDistance = list.get(2), listRatingNum = list.get(4), listAddress = list.get(5), listCategories = list.get(6), listLat = list.get(7), listLng = list.get(8);
        ArrayList<Bitmap> listImageBmp = listBmp.get(0), listRatingBmp = listBmp.get(1);

        for (int i = 0; i < listName.size(); i++) { // go through each row
            YelpData yelpData = new YelpData(listImageBmp.get(i), listName.get(i), listDistance.get(i), listRatingBmp.get(i), listRatingNum.get(i), listAddress.get(i), listCategories.get(i), listLat.get(i), listLng.get(i));

            searchActivity.customAdapter.add(yelpData);
        }
    }
}
