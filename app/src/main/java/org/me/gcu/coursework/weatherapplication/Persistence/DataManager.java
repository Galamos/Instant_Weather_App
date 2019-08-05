package org.me.gcu.coursework.weatherapplication.Persistence;

import android.location.Location;

import org.me.gcu.coursework.weatherapplication.Model.City;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance = null;
    private List<City> mCities = new ArrayList<>();

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public List<City> getCities() {
        return mCities;
    }

    public void setCities(List<City> cities) {
        mCities = cities;
    }

    public void addCity(City city) {
        this.mCities.add(city);
    }

    public City getCities(String name) {
        for (City city : this.mCities) {
            if (city.getCityName().equals(name)) return city;
        }
        return null;
    }

    private DataManager() {
    }
}
