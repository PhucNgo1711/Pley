package edu.drexel.cs.ptn32.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PhucNgo on 8/28/15.
 */
public class CustomAdapter extends ArrayAdapter<YelpData> {
    public CustomAdapter(Context context, ArrayList<YelpData> forecastList) {
        super(context, R.layout.list_view_cell, forecastList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        YelpData yelpData = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_cell, parent, false);
        }

        View overflow = convertView.findViewById(R.id.overflow);
        overflow.setOnClickListener(new OnOverflowSelectedListener(getContext(), yelpData));

        // Lookup view for data population
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        LinearLayout sublayout1 = (LinearLayout) convertView.findViewById(R.id.subLayout);

        LinearLayout sublayout2 = (LinearLayout) sublayout1.findViewById(R.id.subLayout2);
        TextView nameTxt = (TextView) sublayout2.findViewById(R.id.nameTxt);
        TextView distanceTxt = (TextView) sublayout2.findViewById(R.id.distanceTxt);

        LinearLayout sublayout3 = (LinearLayout) sublayout1.findViewById(R.id.subLayout3);
        ImageView ratingImg = (ImageView) sublayout3.findViewById(R.id.ratingImg);
        TextView ratingNumTxt = (TextView) sublayout3.findViewById(R.id.ratingNumTxt);

        LinearLayout sublayout4 = (LinearLayout) sublayout1.findViewById(R.id.subLayout4);
        TextView addressTxt = (TextView) sublayout4.findViewById(R.id.addressTxt);
        TextView foodTypeTxt = (TextView) sublayout4.findViewById(R.id.foodTypeTxt);

        // Populate the data into the template view using the data object
        image.setImageBitmap(yelpData.getImage());
        nameTxt.setText(yelpData.getName());
        distanceTxt.setText(yelpData.getDistance());
        ratingImg.setImageBitmap(yelpData.getRatingImg());
        ratingNumTxt.setText(yelpData.getRatingNum());
        addressTxt.setText(yelpData.getAddress());
        foodTypeTxt.setText(yelpData.getFoodType());

        // Retrievingturn the completed view to render on screen
        return convertView;
    }
}