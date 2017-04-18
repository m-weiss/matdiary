package de.cimdata.myamazingtraveldiary.model;

/**
 * @author Max Weiss
 */
public class WeatherData {

    public String name;
    public String weatherDesc;
    public String icon;
    public Double temp;

    public WeatherData(String name, String weatherDesc, String icon, Double temp){
        this.name = name;
        this.weatherDesc = weatherDesc;
        this.icon = icon;
        this.temp = temp;
    }
}
