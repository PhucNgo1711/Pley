package edu.drexel.cs.ptn32.project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.Intent;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by dyu202 on 8/29/15.
 */

public class OnOverflowSelectedListener implements View.OnClickListener {
    private YelpData mYelp;
    private Context mContext;

    public OnOverflowSelectedListener(Context context, YelpData yelpItem) {
        mContext = context;
        mYelp = yelpItem;
    }

    @Override
    public void onClick(View v) {
        // This is an android.support.v7.widget.PopupMenu;
        PopupMenu popupMenu = new PopupMenu(mContext, v) {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.google_maps_option:
                        getGoogleMaps(mYelp);
                        return true;

                    case R.id.uber_option:
                        //getUber(mYelp);
                        return true;

                    case R.id.septa_option:
                        getSepta(mYelp);
                        return true;

                    default:
                        return super.onMenuItemSelected(menu, item);
                }
            }
        };
        popupMenu.inflate(R.menu.popup);

        popupMenu.show();

    }

    public void getGoogleMaps(YelpData yelpItem){
        Intent intent = new Intent(mContext, DisplayGoogleMapsActivity.class);
        ArrayList<String> listCoordinates = new ArrayList<>();

        String coordinates = yelpItem.getLat() + "," + yelpItem.getLng() + "," + yelpItem.getName();
        listCoordinates.add(coordinates);

        intent.putExtra("coordinate", listCoordinates);
        mContext.startActivity( intent);
    }

    public void getSepta(YelpData yelpItem) {
        SeptaViewAll septaViewAll = new SeptaViewAll();
        septaViewAll.execute();
    }
}