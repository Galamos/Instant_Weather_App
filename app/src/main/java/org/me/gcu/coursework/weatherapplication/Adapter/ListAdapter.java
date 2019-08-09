package org.me.gcu.coursework.weatherapplication.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.me.gcu.coursework.weatherapplication.R;

public class ListAdapter extends ArrayAdapter<String> {

    private final Activity Context;
    private final String[] weatherNow;
    private final Integer[] icons;
    private final String[] keys;

    public ListAdapter(Activity context, String[] keys,
                       Integer[] icon, String[] weatherNow) {

        super(context, R.layout.weather_items, weatherNow);

        this.Context = context;
        this.weatherNow = weatherNow;
        this.icons = icon;
        this.keys = keys;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = Context.getLayoutInflater();
        View ListViewSingle = inflater.inflate(R.layout.weather_items, null, true);

        TextView listKeys = (TextView) ListViewSingle.findViewById(R.id.key);
        TextView listValues = (TextView)ListViewSingle.findViewById(R.id.value);
        ImageView listIcon = (ImageView) ListViewSingle.findViewById(R.id.icon);

        listKeys.setText(keys[position]);
        listValues.setText(weatherNow[position]);
        listIcon.setImageResource(icons[position]);
        return ListViewSingle;

    }
}
