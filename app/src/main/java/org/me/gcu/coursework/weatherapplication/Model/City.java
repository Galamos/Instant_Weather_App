package org.me.gcu.coursework.weatherapplication.Model;

public class City {

    private String cityName;
    private Weather[] weathers;

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

}
