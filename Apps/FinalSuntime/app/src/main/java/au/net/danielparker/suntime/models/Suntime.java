package au.net.danielparker.suntime.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import au.net.danielparker.suntime.calc.AstronomicalCalendar;

/**
 * Created by danielparker on 29/10/14.
 */
public class Suntime implements Parcelable{
    public Location location;
    public Calendar date;

    public Suntime(Location location, Calendar date) {
        this.location = location;
        this.date = date;
    }

    public Suntime(Parcel in) {
        this.location = (Location)in.readValue(Location.class.getClassLoader());
        this.date = (Calendar)in.readValue(
                                     Calendar.class.getClassLoader());
    }

    public static final Parcelable.Creator<Suntime> CREATOR
            = new Parcelable.Creator<Suntime>() {
        public Suntime createFromParcel(Parcel in) {
            return new Suntime(in);
        }

        public Suntime[] newArray(int size) {
            return new Suntime[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.location);
        dest.writeValue(this.date);
    }
}
