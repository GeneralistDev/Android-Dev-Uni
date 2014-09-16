package au.net.danielparker.countdown;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

@EActivity(R.layout.activity_countdown)
public class Countdown extends Activity {

    private CountDownTimer thisTimer;

    @ViewById
    TextView countdown;

    @ViewById
    DatePicker datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.countdown, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Click(R.id.start_button)
    public void onDateSet() {
        Calendar endTime = Calendar.getInstance();
        endTime.clear();
        endTime.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
        long timeLeft = endTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

        // Cancel and deallocate old timer
        if (thisTimer != null) {
            thisTimer.cancel();
            thisTimer = null;
        }

        // Start a new timer
        thisTimer = new CountDownTimer(timeLeft, 1000) {
            public void onTick(long millisecondsUntilFinished) {
                countdown.setText(new Period(millisecondsUntilFinished));
            }

            public void onFinish() {
                countdown.setText("Countdown Ended");
            }
        }.start();
    }
}
