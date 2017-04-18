package de.cimdata.myamazingtraveldiary.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import de.cimdata.myamazingtraveldiary.R;
import de.cimdata.myamazingtraveldiary.dao.WeatherDAO;
import de.cimdata.myamazingtraveldiary.model.RecentLocation;
import de.cimdata.myamazingtraveldiary.model.WeatherData;

public class JsonWeatherActivity extends AppCompatActivity {

    static String NL = System.getProperty("line.seperator");

    TextView desc;
    TextView temperature;
    ImageView img;
    EditText city;

    String description;
    String temp;
    Bitmap symbol;
    String weatherTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        desc = (TextView)findViewById(R.id.json_ausgabe);
        temperature = (TextView)findViewById(R.id.json_temperatur);
        city = (EditText)findViewById(R.id.json_city);
        img = (ImageView)findViewById(R.id.json_img);

        RecentLocation recentLocation = new RecentLocation(this);
        city.setText(recentLocation.getUserLocation());

        Button button = (Button) findViewById(R.id.json_btn);
        if(button!=null)
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeather();
            }
        });
    }

    public void getWeather(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final WeatherData weather;
                try {
                    String req = city.getText().toString().toLowerCase();
                    weather = WeatherDAO.getWeather(req);
                    final Bitmap bitmapWeather = WeatherDAO.getImage(weather);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bitmapWeather);
                            desc.setText(weather.weatherDesc);
                            Double temp = weather.temp - 273.15;
                            temperature.setText(getString(R.string.temp_template, temp.intValue()));
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
