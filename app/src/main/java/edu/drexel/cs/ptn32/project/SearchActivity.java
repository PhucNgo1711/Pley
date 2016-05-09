package edu.drexel.cs.ptn32.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    public CustomAdapter customAdapter;
    private ArrayList<YelpData> restaurantList;
    private  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        restaurantList = new ArrayList<YelpData>();
        listView = (ListView)findViewById(R.id.searchListView);
        // create adapter to convert array to views
        customAdapter = new CustomAdapter(this, restaurantList);
        // attach adapter to listview
        listView.setAdapter(customAdapter);
//        YelpCategorySearch yelpCategorySearch = new YelpCategorySearch(this);
//        String[] inputs = {"pizza", "3141 Chestnut St, 19104"};
//        yelpCategorySearch.execute(inputs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    public void openNearby(View view) {
        Intent intent = new Intent(this, NearbyActivity.class);
        startActivity(intent);
    }

    public void searchYelpCategory(View view){
        restaurantList.clear();
        customAdapter.notifyDataSetChanged();
        EditText searchType = (EditText)findViewById(R.id.searchType);
        EditText searchAddress = (EditText)findViewById(R.id.searchAddress);
        String[] inputs = {searchType.getText().toString(), searchAddress.getText().toString()};
        new YelpCategorySearch(this).execute(inputs);
    }
}
