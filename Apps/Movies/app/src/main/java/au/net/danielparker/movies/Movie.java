package au.net.danielparker.movies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by danielparker on 15/10/14.
 */
public class Movie implements Parcelable {
    private String title;
    private Double rating;
    private String iconName;

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(Parcel in) {
        this.title = in.readString();
        this.rating = in.readDouble();
        this.iconName = in.readString();
    }

    public Movie(String name, Double rating, String iconName) {
        this.title = name;
        this.rating = rating;
        this.iconName = iconName;
    }

    public static ArrayList<Movie> loadMovies(InputStream moviesFile){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(moviesFile));
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String temp;
            while ((temp = inputStream.readLine()) != null) {
                stringBuilder.append(temp);
            }

            Type movieList = new TypeToken<ArrayList<Movie>>() {
            }.getType();

            movies = new Gson().fromJson(stringBuilder.toString(), movieList);
            // Read from JSON
        } catch (IOException e) {
            Log.e("MOVIES", "Error reading from JSON");
        }

        return movies;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.title);
        out.writeDouble(this.rating);
        out.writeString(this.iconName);
    }

    public String getName() {
        return title;
    }

    public String getRating() {
        return rating.toString();
    }

    public String getIconName() {
        return iconName;
    }

    public int describeContents() {
        return 0;
    }
}
