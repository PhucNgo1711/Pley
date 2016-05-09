package edu.drexel.cs.ptn32.project;

import android.graphics.Bitmap;

/**
 * Created by PhucNgo on 8/28/15.
 */
public class YelpData {
    private Bitmap image;
    private String name;
    private String distance;
    private Bitmap ratingImg;
    private String ratingNum;
    private String address;
    private String foodType;
    private String lat;
    private String lng;

    public YelpData(Bitmap image, String name, String distance, Bitmap ratingImg, String ratingNum, String address, String foodType, String lat, String lng) {
        this.image = image;
        this.name = name;
        this.distance = distance;
        this.ratingImg = ratingImg;
        this.ratingNum = ratingNum;
        this.address = address;
        this.foodType = foodType;
        this.lat = lat;
        this.lng = lng;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

    public Bitmap getRatingImg() {
        return ratingImg;
    }

    public String getRatingNum() {
        return ratingNum;
    }

    public String getAddress() {
        return address;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }
}


