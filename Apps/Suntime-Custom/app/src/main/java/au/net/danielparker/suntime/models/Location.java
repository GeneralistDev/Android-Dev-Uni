package au.net.danielparker.suntime.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Location implements Parcelable {

    private String name;
    private double latitude;
    private double longitude;
    private TimeZone timeZone;

    private static String LOCATIONS_FILE = "locations.csv";

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

    public static ArrayList<Location> loadLocations(Context context){
        ArrayList<Location> locations = new ArrayList<Location>();
        try {
            FileInputStream fileInputStream = context.openFileInput(LOCATIONS_FILE);
            CSVReader csvReader = new CSVReader(new InputStreamReader(fileInputStream));
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                String name = nextLine[0];
                double latitude = Double.parseDouble(nextLine[1]);
                double longitude = Double.parseDouble(nextLine[2]);
                TimeZone timeZone = TimeZone.getTimeZone(nextLine[3]);
                locations.add(new Location(name, latitude, longitude, timeZone));
            }
            csvReader.close();

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
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

    public static void saveToDevice(Context context, ArrayList<Location> locations) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(LOCATIONS_FILE,
                                                       Context.MODE_PRIVATE | Context.MODE_APPEND);
            CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(fileOutputStream));
            List<String[]> lines = new ArrayList<String[]>();

            for (Location location: locations) {
                String entry[] = new String[4];
                entry[0] = location.getName();
                entry[1] = location.getLatitude().toString();
                entry[2] = location.getLongitude().toString();
                entry[3] = location.getTimeZone().getID();
                lines.add(entry);
            }

            csvWriter.writeAll(lines);
            csvWriter.close();
        } catch (IOException e) {
            Log.e("SUNTIME", e.getMessage());
        }
    }
}
