package au.net.danielparker.suntime.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import au.net.danielparker.suntime.R;
import au.net.danielparker.suntime.calc.AstronomicalCalendar;
import au.net.danielparker.suntime.calc.GeoLocation;
import au.net.danielparker.suntime.models.Suntime;

/**
 * Created by danielparker on 29/10/14.
 */
public class SuntimeListAdapter extends ArrayAdapter<Suntime> {
    private final Context context;
    private final ArrayList<Suntime> values;

    public SuntimeListAdapter(Context context, ArrayList<Suntime> values) {
        super(context, R.layout.list_suntime_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_suntime_item, parent, false);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        TextView date = (TextView)rowView.findViewById(R.id.date);
        TextView rise = (TextView)rowView.findViewById(R.id.sunrise);
        TextView set = (TextView)rowView.findViewById(R.id.sunset);

        Suntime atPosition = values.get(position);

        GeoLocation geoLocation = new GeoLocation(atPosition.location.getName(),
                                                  atPosition.location.getLatitude(),
                                                  atPosition.location.getLongitude(),
                                                  atPosition.location.getTimeZone());

        AstronomicalCalendar ac = new AstronomicalCalendar(geoLocation);
        ac.setCalendar(atPosition.date);

        Date srise = ac.getSunrise();
        Date sset = ac.getSunset();

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        date.setText(sdf.format(atPosition.date.getTime()));
        rise.setText(timeFormat.format(srise));
        set.setText(timeFormat.format(sset));

        return rowView;
    }
}
