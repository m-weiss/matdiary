package de.cimdata.myamazingtraveldiary.model;


import android.content.Context;
import android.os.Handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.cimdata.myamazingtraveldiary.R;
import de.cimdata.myamazingtraveldiary.dao.LocationsDAO;

public class UserLocations implements Serializable{

    private int id;
    private String location;
    private String text;
    private String image;
    private String weather;
    private String temperature;

    private LocationsDAO dao;

    public UserLocations(String location, String text, String image, String weather, String temperature) {
        this.location = location;
        this.text = text;
        this.image = image;

        this.weather = weather;
        this.temperature = temperature;
    }

    public UserLocations(){

    }

    public ArrayList<UserLocations> createLocationList(Context con){
        ArrayList<UserLocations> locations = new ArrayList<UserLocations>();

        dao = new LocationsDAO(con);

        List<UserLocations> allLocations;


            allLocations = dao.getAllLocations();

            for (int i = 0; i < allLocations.size(); ++i) {
                locations.add(allLocations.get(i));
            }
            return locations;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
