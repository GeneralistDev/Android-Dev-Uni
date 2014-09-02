package au.net.danielparker.heightconvert;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.*;

import java.text.DecimalFormat;

@EActivity(R.layout.activity_convert)
public class ConvertActivity extends Activity {

    @ViewById
    CheckBox metersCheckbox;

    @ViewById
    EditText feet;

    @ViewById
    EditText inches;

    @ViewById
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.convert, menu);
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

    @AfterViews
    @Click
    void convertButton() {


        if (feet.getText().length() == 0 || inches.getText().length() == 0) {
            result.setText("");
        } else {
            double feetValue = Double.parseDouble(feet.getText().toString());
            double inchValue = Double.parseDouble(inches.getText().toString());

            if (feetValue * 12 + inchValue > 96) {
                Toast tallToast = Toast.makeText(getApplicationContext(), "You're suspiciously tall", Toast.LENGTH_LONG);
                tallToast.show();
            }

            if (!metersCheckbox.isChecked()) {
                DecimalFormat dec = new DecimalFormat("0.## cm");
                dec.setMinimumFractionDigits(2);
                result.setText(dec.format(heightInCentimetres(feetValue, inchValue)));
            } else {
                DecimalFormat dec = new DecimalFormat("0.## m");
                dec.setMinimumFractionDigits(2);
                result.setText(dec.format(heightInMetres(feetValue, inchValue)));
            }
        }
    }

    /**
     * Convert feet, inches to centimetres
     * @param feet      Number of feet
     * @param inches    Number of inches
     * @return          Number of centimetres
     */
    public Double heightInCentimetres(double feet, double inches) {
        double totalInches = inches + ( feet * 12 );
        return totalInches * 2.54;
    }

    /**
     * Convert feet, inches to metres using the heightInCentimetres function
     * @param feet      Number of feet
     * @param inches    Number of inches
     * @return          Number of metres
     */
    public Double heightInMetres(double feet, double inches) {
        double centimetres = heightInCentimetres(feet, inches);
        return centimetres / 100;
    }
}
