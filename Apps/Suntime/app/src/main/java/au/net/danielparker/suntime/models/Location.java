package au.net.danielparker.suntime.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TimeZone;

import au.com.bytecode.opencsv.CSVReader;

public class Location implements Parcelable {

    private String name;
    private double latitude;
    private double longitude;
    private TimeZone timeZone;

    public static final Parcelable.Creator<Location> CREATOR
            = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public Location(Parcel in) {
        this.name = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.timeZone = (TimeZone)in.readValue(TimeZone.class.getClassLoader());
    }

    public Location(String name, double latitude, double longitude, TimeZone timeZone) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZone = timeZone;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.name);
        out.writeDouble(this.latitude);
        out.writeDouble(this.longitude);
        out.writeValue(this.timeZone);
    }

    public static ArrayList<Location> loadLocations(InputStream locationsFile){
        CSVReader csvReader = new CSVReader(new InputStreamReader(locationsFile));
        String[] nextLine;
        ArrayList<Location> locations = new ArrayList<Location>();
        try {
            while ((nextLine = csvReader.readNext()) != null) {
                String name = nextLine[0];
                double latitude = Double.parseDouble(nextLine[1]);
                double longitude = Double.parseDouble(nextLine[2]);
                TimeZone timeZone = TimeZone.getTimeZone(nextLine[3]);
                locations.add(new Location(name, latitude, longitude, timeZone));
            }
        } catch (IOException e){
            Log.e("SUNTIME", e.getMessage());
        }

        return locations;
    }

    public int describeContents() {
        return 0;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
}
