package au.net.danielparker.suntime.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import au.net.danielparker.suntime.R;
import au.net.danielparker.suntime.models.Location;


public class LocationsAdapter extends ArrayAdapter<Location> {

    private final Context context;
    private final ArrayList<Location> values;

    public LocationsAdapter(Context context, ArrayList<Location> values) {
        super(context, R.layout.list_location_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_location_item, parent, false);

        TextView locationName = (TextView)rowView.findViewById(R.id.Location_Name);
        locationName.setText(values.get(position).getName());

        return rowView;
    }
}
