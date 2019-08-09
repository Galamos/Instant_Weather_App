package org.me.gcu.coursework.weatherapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.me.gcu.coursework.weatherapplication.Model.City;
import org.me.gcu.coursework.weatherapplication.Model.Weather;
import org.me.gcu.coursework.weatherapplication.R;
import org.me.gcu.coursework.weatherapplication.View.DetailActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    //Class variables
    private List<City> mCities;
    private Context mContext;
    private Date mDate = new Date();
    private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("EEEE", Locale.US);
    public static final String NAME_KEY = "City Name";
    private String mCityNmae;

    /**
     * Constructor that passes the cities data and the context
     * @param cities arraylist containing cities name
     * @param context which denotes the context of the application
     */
    public RecyclerAdapter(Context context, List<City> cities){
        this.mCities = cities;
        this.mContext = context;
    }

    /**
     * Method for creating the viewholder objects
     * @param parent the ViewGroup into which the new view will be added after it is bound to an adapter position
     * @param viewType The view
     * @return the newly created ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.city_list, parent, false));
    }

    /**
     * Method that binds data to the viewholder
     * @param holder The viewholder into which the data should be put
     * @param position The adapter position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        City currentCity = this.mCities.get(position);
        holder.bindTo(currentCity);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        private RecyclerView recyclerView;
            City mCurrentCity;
            final Context mContext;
            final TextView cityName;
            final TextView minTemperature;
            final TextView title;
            final TextView currentDay;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         * @param itemView The rootview of the list_cities.xml layout file
         */
        public ViewHolder(Context context, @NonNull View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.city_name);
            minTemperature = itemView.findViewById(R.id.minTemp);
            title = itemView.findViewById(R.id.description);
            currentDay = itemView.findViewById(R.id.date);

            mContext = context;

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(City currentCity){
            //Populate the textviews with data
            Weather[] weathers = currentCity.getWeathers();
            Weather currentWeather = weathers[0];
            cityName.setText(currentCity.getCityName());
            minTemperature.setText(currentWeather.getMinTemperature());
            title.setText(currentWeather.getTitle());
            currentDay.setText(DAY_FORMAT.format(mDate));

            //Get the current city
            mCurrentCity = currentCity;

        }


        @Override
        public void onClick(View v) {
            Intent detailIntent = City.starter(mContext, mCurrentCity.getCityName());
            detailIntent.putExtra("City", mCityNmae);
            //Start the main weather page for each city
            mContext.startActivity(detailIntent);
        }
    }
}
