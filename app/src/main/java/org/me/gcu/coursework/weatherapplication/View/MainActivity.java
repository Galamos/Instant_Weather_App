package org.me.gcu.coursework.weatherapplication.View;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.me.gcu.coursework.weatherapplication.Model.City;
import org.me.gcu.coursework.weatherapplication.Persistence.DataManager;
import org.me.gcu.coursework.weatherapplication.Persistence.PullParser;
import org.me.gcu.coursework.weatherapplication.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.me.gcu.coursework.weatherapplication.Persistence.DataManager.getInstance;

public class MainActivity extends AppCompatActivity {

    private static String baseUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/";

    private static final Map<String, String> city_id = new HashMap<String, String>() {{
        put("Abuja", "2352777");
        put("Glasgow", "2648579");
        put("London", "2643743");
        put("New York", "5128581");
        put("Oman", "287286");
        put("Mauritius", "934154");
        put("Bangladesh", "1185241");
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new xmlLoader().execute(baseUrl);

       List<City> cityList = DataManager.getInstance().getCities();
       System.out.println(cityList);
    }

    private class xmlLoader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            for (String city_name : city_id.keySet()) {
                City city = PullParser.accessXML(urls[0] + city_id.get(city_name),
                        city_name);
                getInstance().addCity(city);
            }
            return "Completed...";
        }

//        @Override
//        protected void onPostExecute(String result) {
//            setContentView(R.layout.activity_main);
//            initializeDisplay();
//        }
    }
}
