package au.net.danielparker.suntime.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.TimeZone;

import au.net.danielparker.suntime.R;
import au.net.danielparker.suntime.models.Location;

/**
 * Created by danielparker on 15/10/14.
 */
@EActivity(R.layout.activity_add_custom)
public class AddNewLocation extends Activity {
    @ViewById(R.id.form_name)
    public EditText formName;

    @ViewById(R.id.form_latitude)
    public EditText formLat;

    @ViewById(R.id.form_longitude)
    public EditText formLon;

    @ViewById(R.id.form_timezone)
    public EditText formTimezone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Click(R.id.form_submit)
    void onFormSubmit() {
        String name;
        Double latitude;
        Double longitude;
        TimeZone timeZone;

        try {
            name = formName.getText().toString();
            latitude = validateLatitude();
            longitude = validateLongitude();
            timeZone = validateTimezone();

            Location newLocation = new Location(name, latitude, longitude, timeZone);
            ArrayList<Location> newLocations = new ArrayList<Location>();
            newLocations.add(newLocation);
            Location.saveToDevice(getApplicationContext(), newLocations);

            finish();
        } catch (Exception e){
            Toast validationError = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            validationError.show();
        }
    }

    Double validateLatitude() throws Exception {
        Double latitude;
        try {
            latitude = Double.parseDouble(formLat.getText().toString());
            if (!(-90 <= latitude && latitude <= 90)) {

            }
        } catch (Exception e) {
            throw new Exception("Latitude must be between -90 and 90");
        }

        return latitude;
    }

    Double validateLongitude() throws Exception {
        Double longitude;
        try {
            longitude = Double.parseDouble(formLat.getText().toString());
            if (!(-180 <= longitude && longitude <= 180)) {
                throw new Exception("Longitude must be between -180 and 180");
            }
        } catch (Exception e) {
            throw new Exception("Longitude must be between -180 and 180");
        }

        return longitude;
    }

    TimeZone validateTimezone() throws Exception {
        TimeZone timeZone = TimeZone.getTimeZone("GMT" + formTimezone.getText().toString());
        return timeZone;
    }
}
