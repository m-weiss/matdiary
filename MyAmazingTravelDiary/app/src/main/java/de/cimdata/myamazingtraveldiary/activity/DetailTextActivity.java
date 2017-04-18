package de.cimdata.myamazingtraveldiary.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import de.cimdata.myamazingtraveldiary.R;
import de.cimdata.myamazingtraveldiary.dao.LocationsDAO;
import de.cimdata.myamazingtraveldiary.dao.WeatherDAO;
import de.cimdata.myamazingtraveldiary.model.RecentLocation;
import de.cimdata.myamazingtraveldiary.model.UserLocations;
import de.cimdata.myamazingtraveldiary.model.WeatherData;

public class DetailTextActivity extends AppCompatActivity implements View.OnClickListener{


    private static final int IMAGE_CAPTURE = 1;
    private static final String TAG = DetailTextActivity.class.getSimpleName();

    private Button saveBtn;
    private Button camBtn;
    private Button gallery;
    private EditText userTxt;
    private ImageView imageView;
    private ImageView symbolView;
    private TextView textView;
    private TextView weatherView;

    private String currentLocation;
    private String weatherDesc;
    private String temperature;
    private String newImagePath;

    private Bitmap symbol;
    private Uri imageUri;
    private LocationsDAO dao;
    private Uri imgUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_text);
        textView = (TextView)findViewById(R.id.detail_text);
        if(getIntent().getBooleanExtra("newTxt", true))
            prepareNewText();

        dao = new LocationsDAO(this);
        dao.getWritableDatabase();

        saveBtn = (Button)findViewById(R.id.detail_save);
        camBtn = (Button)findViewById(R.id.detail_cambtn);
        gallery = (Button)findViewById(R.id.detail_gallery);
        imageView = (ImageView)findViewById(R.id.detail_img);
        symbolView = (ImageView)findViewById(R.id.detail_symbol);
        weatherView = (TextView)findViewById(R.id.detail_weather);
        userTxt = (EditText)findViewById(R.id.detail_usertext);

        saveBtn.setOnClickListener(this);
        camBtn.setOnClickListener(this);
        gallery.setOnClickListener(this);

        if(getIntent().getBooleanExtra("newImg", true)){
            showNewImage();
        }
        getWeather();
    }

    private void showNewImage() {
        newImagePath = getIntent().getStringExtra("imgUri");
        if(newImagePath!=null){
            imgUri = Uri.parse(newImagePath);
            imageView.setImageURI(imgUri);
        }

    }


    private void prepareNewText() {

        RecentLocation recentLocation = new RecentLocation(this);
        currentLocation = recentLocation.getUserLocation();
        String day = new SimpleDateFormat("EEEE").format(Calendar.getInstance().getTime());
        String date = new SimpleDateFormat("dd-MMMM-yyyy").format(Calendar.getInstance().getTime());
        String dateString = day + ", " + date + ": ";
        if(currentLocation!=null)
            currentLocation = currentLocation.toUpperCase();

        String locationAndTime = currentLocation + ", " + dateString;
        textView.setText(locationAndTime);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_cambtn:
                Intent camIntent = new Intent(this,RecordImageActivity.class);
                camIntent.putExtra("openCam", true);
                startActivity(camIntent);
                break;
            case R.id.detail_gallery:
                //TODO open gallery
                Toast.makeText(this, "not implemented yet :(", Toast.LENGTH_SHORT).show();
                break;
            case R.id.detail_save:
                String usertxt = userTxt.getText().toString();
                if(newImagePath == null)
                dao.insert(new UserLocations(currentLocation, usertxt, "img_path", "rain", "12.5"));
                else
                    dao.insert(new UserLocations(currentLocation, usertxt, newImagePath, weatherDesc, temperature));
                Toast.makeText(this, "diary-text saved", Toast.LENGTH_SHORT).show();
                Intent backToMain = new Intent(this, MATDiaryMainActivity.class);
                startActivity(backToMain );
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    float width = bitmap.getWidth();
                    float height = bitmap.getHeight();
                    int smalHeight = 300;
                    int smallWidth = (int)(width/height*(float)smalHeight);
                    Bitmap smallBitmap = Bitmap.createScaledBitmap(bitmap, smallWidth, smalHeight, false);
                    imageView.setImageBitmap(smallBitmap);
                } catch (IOException e) {
                    Log.e(TAG, "setBitmap()", e);
                    e.printStackTrace();
                }
            }else {
                int rowsdeleted = getContentResolver().delete(imageUri, null,null);
                Log.d(TAG, rowsdeleted + " rows deleted");
            }
        }
    }

    public void getWeather(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final WeatherData weather;
                if(currentLocation!=null)
                try {

                    String req = currentLocation.toLowerCase();
                    weather = WeatherDAO.getWeather(req);
                    final Bitmap bitmapWeather = WeatherDAO.getImage(weather);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            symbol = bitmapWeather;
                            symbolView.setImageBitmap(bitmapWeather);
                            weatherDesc = weather.weatherDesc;
                            Double temp = weather.temp - 273.15;
                            temperature = getString(R.string.temp_template, temp.intValue());
                            weatherView.setText(weatherDesc + ", " + temperature);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
