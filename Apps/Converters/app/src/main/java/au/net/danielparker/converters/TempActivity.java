package au.net.danielparker.converters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;

/**
 * Created by danielparker on 4/09/14.
 */
@EActivity(R.layout.activity_temp)
public class TempActivity extends Activity {

    @ViewById(R.id.fahr)
    EditText Fahrenheit;

    @ViewById(R.id.result_temp)
    TextView Result;

    DecimalFormat celFormatter = new DecimalFormat("0.## C");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        celFormatter.setMinimumFractionDigits(2);
    }

    @Click(R.id.convert_button)
    void convertButton(){
        if (Fahrenheit.getText().length() == 0) {
            Toast emptyToast = Toast.makeText(getApplicationContext(), "Please enter a temperature to convert", Toast.LENGTH_LONG);
            emptyToast.show();
        } else {
            Double celsius = (Double.parseDouble(Fahrenheit.getText().toString()) - 32) / 1.8;
            Result.setText(celFormatter.format(celsius));
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Fahrenheit.getWindowToken(), 0);
        }
    }
}
