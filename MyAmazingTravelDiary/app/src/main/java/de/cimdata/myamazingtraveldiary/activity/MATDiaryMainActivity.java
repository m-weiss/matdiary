package de.cimdata.myamazingtraveldiary.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import de.cimdata.myamazingtraveldiary.R;
import de.cimdata.myamazingtraveldiary.model.UserLocations;
import de.cimdata.myamazingtraveldiary.ui.LocationRecyclerViewAdapter;

/**
 * @author Max Weiss
 */
public class MATDiaryMainActivity extends AppCompatActivity {

    ArrayList<UserLocations> locations;
    private static final int MY_PERMISSIONS_REQUEST =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matdiary_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        if(toolbar!=null)
        toolbar.setBackgroundColor(Color.GRAY);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.setDisplayShowTitleEnabled(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mainRecylerView);

        UserLocations userLocations = new UserLocations();
        locations = userLocations.createLocationList(this);

            LocationRecyclerViewAdapter adapter = new LocationRecyclerViewAdapter(locations, this);
            if (recyclerView != null) {
                recyclerView.setAdapter(new LocationRecyclerViewAdapter(locations, this));
                /*
                reverse recyclerview:
                 */
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

        checkNeededPermissions();
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_image:
                Intent i = new Intent(this, RecordImageActivity.class);
                i.putExtra("openCam", true);
                startActivity(i);
                return true;
            case R.id.action_addText:
                Intent i2 = new Intent(this, DetailTextActivity.class);
                i2.putExtra("newTxt", true);
                startActivity(i2);
                return true;
            case  R.id.action_weather:
                Intent i3 = new Intent(this, JsonWeatherActivity.class);
                startActivity(i3);
                break;
            default:
                break;
        }
        return false;
    }

    private void checkNeededPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);
            }
        }
        else if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST);
            }
        }
        else if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST:
                if(!(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    Toast.makeText(this, "Sorry to use this app the requested permissions are needed",Toast.LENGTH_SHORT).show();
                    break;
                }
            default:
                break;
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//
//        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//
//        }
//    }
}
