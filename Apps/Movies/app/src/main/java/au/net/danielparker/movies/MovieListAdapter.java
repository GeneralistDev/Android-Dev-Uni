package au.net.danielparker.movies;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by danielparker on 15/10/14.
 */
public class MovieListAdapter extends ArrayAdapter<Movie>{
    private final Context context;
    private final ArrayList<Movie> values;

    public MovieListAdapter(Context context, ArrayList<Movie> values) {
        super(context, R.layout.movie_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.movie_layout, parent, false);

        ImageView movieIcon = (ImageView)rowView.findViewById(R.id.icon);
        TextView movieName = (TextView)rowView.findViewById(R.id.movie_name);
        TextView movieRating = (TextView)rowView.findViewById(R.id.movie_rating);

        movieIcon.setImageResource(R.drawable.movie_icon);
        movieName.setText(values.get(position).getName());
        movieRating.setText(values.get(position).getRating());

        return rowView;
    }
}
