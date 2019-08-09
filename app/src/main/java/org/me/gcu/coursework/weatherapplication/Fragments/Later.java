package org.me.gcu.coursework.weatherapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.me.gcu.coursework.weatherapplication.Adapter.ListAdapter;
import org.me.gcu.coursework.weatherapplication.Model.City;
import org.me.gcu.coursework.weatherapplication.Model.Weather;
import org.me.gcu.coursework.weatherapplication.Persistence.DataManager;
import org.me.gcu.coursework.weatherapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Later extends Fragment {


    private City currentCity;
    private Weather[] weathers;
    private Weather currentWeather;
    private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("EEEE", Locale.US);
    private ListAdapter listAdapter;
    private TextView date, description;
    private ListView listView;
    private String mCityName;
    private Date mDate = new Date();
    private Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment, container, false);

        listView = view.findViewById(R.id.listView);
        date = view.findViewById(R.id.dayToday);
        description = view.findViewById(R.id.descriptionToday);

        //Get the string extra from the activity
        mCityName = getActivity().getIntent().getStringExtra(City.NAME_KEY);
        currentCity = DataManager.getInstance().getCity(mCityName);

        weathers = currentCity.getWeathers();
        currentWeather = weathers[2];

        //Set the weather description
        String[] titleArray = currentWeather.getTitle().trim().split(",");
        description.setText(titleArray[0].split(":")[1]);

        calendar.setTime(mDate);
        calendar.add(Calendar.DATE, 2);
        mDate = calendar.getTime();
        date.setText(DAY_FORMAT.format(mDate));

        //Create an array for labels of the weather items
        String[] labels = getResources().getStringArray(R.array.weather_keys);
        String[] weatherNow = {currentWeather.getMaxTemperature(), currentWeather.getMinTemperature(),
                currentWeather.getWindDirection(), currentWeather.getWindSpeed(),
                currentWeather.getVisibility(), currentWeather.getPressure(),
                currentWeather.getHumidity(), currentWeather.getUvRisk(), currentWeather.getPollution(),
                currentWeather.getSunrise(), currentWeather.getSunset()};
        Integer icons[] = {R.drawable.ic_thermometer, R.drawable.ic_thermometer, R.drawable.ic_wind_direction,
                R.drawable.ic_wind_speed, R.drawable.ic_visibility, R.drawable.ic_pressure, R.drawable.ic_humidity, R.drawable.ic_uv,
                R.drawable.ic_pollution, R.drawable.ic_sunrise, R.drawable.ic_sunset};

        listAdapter = new ListAdapter(getActivity(), labels, icons, weatherNow);

        /*
         *Set contents into the views
         */
        listView.setAdapter(listAdapter);


        return view;
    }


}
