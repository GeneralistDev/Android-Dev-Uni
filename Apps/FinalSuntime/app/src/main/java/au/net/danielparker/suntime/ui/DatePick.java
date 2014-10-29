package au.net.danielparker.suntime.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

import au.net.danielparker.suntime.R;
import au.net.danielparker.suntime.models.Location;

@EActivity(R.layout.activity_date_pick)
public class DatePick extends Activity {

    @ViewById(R.id.start_date)
    DatePicker startDatePicker;

    @ViewById(R.id.end_date)
    DatePicker endDatePicker;

    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        location = (Location)getIntent().getExtras().get("location");
        //Calendar today = Calendar.getInstance();
//        startDatePicker.updateDate(today.get(Calendar.YEAR),
//                                   today.get(Calendar.MONTH),
//                                   today.get(Calendar.DAY_OF_MONTH));
//
//        endDatePicker.updateDate(today.get(Calendar.YEAR),
//                today.get(Calendar.MONTH),
//                today.get(Calendar.DAY_OF_MONTH));
    }

    @Click(R.id.generate_button)
    public void onGenerate() {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.YEAR, startDatePicker.getYear());
        start.set(Calendar.MONTH, startDatePicker.getMonth());
        start.set(Calendar.DAY_OF_MONTH, startDatePicker.getDayOfMonth());

        Calendar end = Calendar.getInstance();
        end.set(Calendar.YEAR, endDatePicker.getYear());
        end.set(Calendar.MONTH, endDatePicker.getMonth());
        end.set(Calendar.DAY_OF_MONTH, endDatePicker.getDayOfMonth());

        if (start.before(end)) {
            Intent intent = new Intent(this, TableActivity_.class);
            intent.putExtra("location", location);
            intent.putExtra("start", start);
            intent.putExtra("end", end);

            startActivity(intent);
        } else {
            Toast t = Toast.makeText(this, "Start date cannot be after end date", Toast.LENGTH_SHORT);
            t.show();
        }
    }
}
