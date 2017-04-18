package de.cimdata.myamazingtraveldiary.dao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import de.cimdata.myamazingtraveldiary.model.WeatherData;

/**
 * @author Max Weiss
 */
public class WeatherDAO {

    private static final String OPENWEATHERURL = "http://api.openweathermap.org/data/2.5/weather?q={0}";
    private static final String APIKEY = "&APPID=38d7bf0b3e68229b0b49f908fe5e4a93";

    public static WeatherData getWeather(String city) throws JSONException, IOException {
        String name = null;
        String desc = null;
        String icon = null;
        Double temp = null;

        JSONObject jsonObject = new JSONObject(WeatherDAO.getFromServer(MessageFormat.format(OPENWEATHERURL, city)));
        if(jsonObject.has("name"))
            name = jsonObject.getString("name");
        if(jsonObject.has("weather")){
            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
            if(jsonArrayWeather.length() > 0){
                JSONObject jsonWeatherObject = jsonArrayWeather.getJSONObject(0);
                if(jsonWeatherObject.has("description"))
                    desc = jsonWeatherObject.getString("description");
                if(jsonWeatherObject.has("icon"))
                    icon = jsonWeatherObject.getString("icon");
            }
        }
        if(jsonObject.has("main")){
            JSONObject main = jsonObject.getJSONObject("main");
            temp = main.getDouble("temp");
        }

        return new WeatherData(name, desc, icon, temp);
    }

    public static String getFromServer(String openWeatherUrl) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = new URL(openWeatherUrl + APIKEY);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        int responseCode = httpURLConnection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine())!=null)
                sb.append(line);
            try {
                bufferedReader.close();
            }catch (IOException e) {

            }
        }
        httpURLConnection.disconnect();
        return sb.toString();
    }

    public static Bitmap getImage(WeatherData weatherData) throws IOException {
        URL url = new URL("http://openweathermap.org/img/w/" + weatherData.icon + ".png");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
        httpURLConnection.disconnect();
        return bitmap;
    }
}
