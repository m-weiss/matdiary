package de.cimdata.myamazingtraveldiary.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author Max Weiss
 */
public class RecentLocation {


    Double latitude;
    Double longitude;
    LocationManager lm;
    LocationListener ll;

    String provider;
    Location location;
    Geocoder geocoder;

    private String userLocation;

    Activity act;

    public RecentLocation(Activity act){

        this.act = act;

        lm = (LocationManager)act.getSystemService(Context.LOCATION_SERVICE);
        if(lm!=null){
            ll = new MyLocationListener();

            if(ContextCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 5, ll);}
                else if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100,5,ll);
                }
            }
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            provider = lm.getBestProvider(criteria,true);
            if(provider!=null) {
                location = lm.getLastKnownLocation(provider);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
                showLocationInfo();
            }
        }
    }

    private void showLocationInfo() {

        if(provider!=null && checkInternetConnection() && latitude!=null && longitude!=null){

            geocoder = new Geocoder(act, Locale.GERMAN);
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
                if(addresses!=null && addresses.size()>0){
                    Address address = addresses.get(0);
                    userLocation = address.getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)act.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            if(connectivityManager.getActiveNetworkInfo()!=null &&
                    connectivityManager.getActiveNetworkInfo().isAvailable() &&
                    connectivityManager.getActiveNetworkInfo().isConnected()){
                return true;
            } else {
                Toast.makeText(act, "Internet not available", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    public String getUserLocation() {
        return userLocation;
    }

}
