package au.net.danielparker.suntime.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import au.net.danielparker.suntime.R;
import au.net.danielparker.suntime.calc.AstronomicalCalendar;
import au.net.danielparker.suntime.calc.GeoLocation;
import au.net.danielparker.suntime.models.Location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

public class SuntimeActivity extends Activity
{
    /** Called when the activity is first created. */
    private Location currentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suntime);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        this.currentLocation = (Location)bundle.getParcelable("location");

        initializeUI();
    }

	private void initializeUI()
	{
		DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
        TextView locationTV = (TextView)findViewById(R.id.locationTV);
		dp.init(year,month,day,dateChangeHandler); // setup initial values and reg. handler
		updateTime(year, month, day);
	}
    
	private void updateTime(int year, int monthOfYear, int dayOfMonth)
	{
        GeoLocation geoLocation;
        if (currentLocation == null) {
            TimeZone tz = TimeZone.getDefault();
            geoLocation = new GeoLocation("Melbourne", -37.50, 145.01, tz);
        } else {
            geoLocation = new GeoLocation(
                                currentLocation.getName(), currentLocation.getLatitude(),
                                currentLocation.getLongitude(), currentLocation.getTimeZone());
        }

        TextView locationTV = (TextView)findViewById(R.id.locationTV);
        locationTV.setText(geoLocation.getLocationName());

		AstronomicalCalendar ac = new AstronomicalCalendar(geoLocation);
		ac.getCalendar().set(year, monthOfYear, dayOfMonth);
		Date srise = ac.getSunrise();
		Date sset = ac.getSunset();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		TextView sunriseTV = (TextView) findViewById(R.id.sunriseTimeTV);
		TextView sunsetTV = (TextView) findViewById(R.id.sunsetTimeTV);
		Log.d("SUNRISE Unformatted", srise+"");
		
		sunriseTV.setText(sdf.format(srise));
		sunsetTV.setText(sdf.format(sset));		
	}
	
	OnDateChangedListener dateChangeHandler = new OnDateChangedListener()
	{
		public void onDateChanged(DatePicker dp, int year, int monthOfYear, int dayOfMonth)
		{
			updateTime(year, monthOfYear, dayOfMonth);
		}	
	};
	
}