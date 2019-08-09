package org.me.gcu.coursework.weatherapplication.Persistence;

import android.util.Xml;

import org.me.gcu.coursework.weatherapplication.Model.City;
import org.me.gcu.coursework.weatherapplication.Model.Weather;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PullParser {

    private static final Logger LOGGER = Logger.getLogger(PullParser.class.getSimpleName());
    private static final String feed = null;

    public static City accessXML(String baseUrl, String cityName) {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.connect();

            try (InputStream inputStream = connection.getInputStream()) {

                XmlPullParser xmlPullParser = Xml.newPullParser();
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(inputStream, null);
                xmlPullParser.nextTag();
                return parseXml(xmlPullParser, cityName);

            } catch (XmlPullParserException e) {
                LOGGER.log(Level.WARNING, "XmlPullParser Exception", e.getMessage());
            }

        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING, "MalformedUrl Exception", e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "IO Exception: ", e.getMessage());
        }
        return null;
    }

    private static City parseXml(XmlPullParser xmlPullParser, String cityName) throws IOException, XmlPullParserException {
        City currentCity = null;
        xmlPullParser.require(XmlPullParser.START_TAG, feed, "rss");
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) continue;
            String name = xmlPullParser.getName();
            if (name.equals("channel")) {
                currentCity = parseChannel(xmlPullParser, cityName);
            } else skip(xmlPullParser);
        }
        return currentCity;
    }

    private static City parseChannel(XmlPullParser xmlPullParser, String cityName) throws IOException, XmlPullParserException {
        List<Weather> weathers = new ArrayList<>();
        xmlPullParser.require(XmlPullParser.START_TAG, feed, "channel");

        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) continue;
            String name = xmlPullParser.getName();

            if (name.equals("item")) weathers.add(parseItems(xmlPullParser));
            else skip(xmlPullParser);
        }

        return new City(cityName, !weathers.isEmpty() ?
                weathers.toArray(new Weather[0]) :
                null);
    }

    private static Weather parseItems(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, feed, "item");
        String description = null;
        String title = null;
        String date = null;
        Weather weather = null;
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) continue;
            String name = xmlPullParser.getName();

            switch (name) {
                case "title":
                    title = parseText(xmlPullParser);
                    break;
                case "description":
                    description = parseText(xmlPullParser);
                    break;
                case "pubDate":
                    date = parseText(xmlPullParser);
                    break;
                default:
                    skip(xmlPullParser);
                    break;
            }

            HashMap<String, String> values;
            if (description != null && title != null && date != null) {
                values = extractValues(description);
                weather = new Weather();

                if (values != null) {
                    for (String str : values.keySet()) {
                        switch (str) {
                            case "Minimum Temperature":
                                weather.setMinTemperature(values.get("Minimum Temperature"));
                                break;
                            case "Maximum Temperature":
                                weather.setMaxTemperature(values.get("Maximum Temperature"));
                                break;
                            case "Wind Direction":
                                weather.setWindDirection(values.get("Wind Direction"));
                                break;
                            case "Wind Speed":
                                weather.setWindSpeed(values.get("Wind Speed"));
                                break;
                            case "Visibility":
                                weather.setVisibility(values.get("Visibility"));
                                break;
                            case "Pressure":
                                weather.setPressure(values.get("Pressure"));
                                break;
                            case "Humidity":
                                weather.setHumidity(values.get("Humidity"));
                                break;
                            case "UV Risk":
                                weather.setUvRisk(values.get("UV Risk"));
                                break;
                            case "Pollution":
                                weather.setPollution(values.get("Pollution"));
                                break;
                            case "Sunrise":
                                weather.setSunrise(values.get("Sunrise"));
                                break;
                            case "Sunset":
                                weather.setSunset(values.get("Sunset"));
                                break;
                        }
                    }
                    weather.setTitle(title);
                    weather.setDate(date);
                    description = null;
                    title = null;
                    date = null;
                }
            }
        }
        return weather;
    }


    private static HashMap<String, String> extractValues(String description) {
        HashMap<String, String> values = new HashMap<>();

        if (description.equals("")) return null;

        String[] listOfValues = description.split(", ");
        for (String listOfValue : listOfValues) {
            String[] value = listOfValue.trim().split(": ");
            values.put(value[0].trim(), value[1].trim());
        }
        return values;
    }

    private static String parseText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String text = "";
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.getText();
            parser.nextTag();
        }
        return text;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
