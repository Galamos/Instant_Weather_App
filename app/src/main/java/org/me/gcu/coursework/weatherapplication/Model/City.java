package org.me.gcu.coursework.weatherapplication.Model;

import android.content.Context;
import android.content.Intent;

import org.me.gcu.coursework.weatherapplication.View.DetailActivity;

public class City {

    private String cityName;
    private Weather[] weathers;
    public static final String NAME_KEY = "City Name";

    public City(String cityName, Weather[] weathers) {
        this.cityName = cityName;
        this.weathers = weathers;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Weather[] getWeathers() {
        return weathers;
    }

    public void setWeathers(Weather[] weathers) {
        this.weathers = weathers;
    }

    /**
     * Method for creating the Main weather page of the individual city intent
     * @param context Application context, used for launching the intent
     * @param name The name of the current city
     * @return The intent containing the extras about the sport, launches the detail activity
     */
    public static Intent starter(Context context, String name) {
        Intent detailIntent = new Intent(context, DetailActivity.class);
        detailIntent.putExtra(NAME_KEY, name);
        return detailIntent;
    }
}
