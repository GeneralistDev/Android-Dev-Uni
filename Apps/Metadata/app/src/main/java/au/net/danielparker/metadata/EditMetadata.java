package au.net.danielparker.metadata;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by danielparker on 14/09/14.
 */
@EActivity(R.layout.activity_edit_metadata)
public class EditMetadata extends Activity {

    private boolean navFired = false;

    private ImageData selectedImage;
    private int position;

    @ViewById(R.id.name)
    EditText name;

    @ViewById(R.id.source_url)
    EditText sourceURL;

    @ViewById(R.id.keywords)
    MultiAutoCompleteTextView keywords;

    @ViewById(R.id.source_email)
    EditText sourceEmail;

    @ViewById(R.id.share_toggle)
    ToggleButton shareToggle;

    @ViewById(R.id.date)
    DatePicker date;

    @ViewById(R.id.rating)
    RatingBar rating;

    @ViewById(R.id.save_button)
    Button saveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundledData = getIntent().getExtras();

        if (bundledData.containsKey("Theme")) {
            setTheme(bundledData.getInt("Theme"));
        }
        selectedImage = bundledData.getParcelable("ImageData");
        position = bundledData.getInt("position");

        // Create actionbar dropdown spinner from stringarray
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(
                                         getActionBar().getThemedContext(), R.array.text_sizes,
                                         android.R.layout.simple_spinner_dropdown_item);

        ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {

                Log.d("METADATA", "Position: " + position + " ItemId: " + itemId);
                Intent intent = getIntent();

                if (navFired == true ) {
                    switch (position) {
                        case 0:
                            intent.putExtra("Theme", R.style.NormalText);
                            finish();
                            startActivity(intent);
                            break;
                        case 1:
                            intent.putExtra("Theme", R.style.SmallText);
                            finish();
                            startActivity(intent);
                            break;
                        case 2:
                            intent.putExtra("Theme", R.style.LargeText);
                            finish();
                            startActivity(intent);
                            break;
                    }
                } else {
                    navFired = true;
                }

                return true;
            }
        };

        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);

        if (bundledData.containsKey("Theme")) {
            switch (bundledData.getInt("Theme")){
                case R.style.NormalText:
                    getActionBar().setSelectedNavigationItem(0);
                    break;
                case R.style.SmallText:
                    getActionBar().setSelectedNavigationItem(1);
                    break;
                case R.style.LargeText:
                    getActionBar().setSelectedNavigationItem(2);
                    break;
            }

        } else {
            getActionBar().setSelectedNavigationItem(0);
        }
    }

    @AfterViews
    public void restoreData() {
        this.name.setText(selectedImage.getName());
        this.sourceURL.setText(selectedImage.getUrl());
        this.keywords.setText(selectedImage.getKeyWords());
        this.sourceEmail.setText(selectedImage.getSourceEmail());
        this.shareToggle.setChecked(selectedImage.getShare());
        this.date.updateDate(selectedImage.getDate().get(Calendar.YEAR),
                             selectedImage.getDate().get(Calendar.MONTH),
                             selectedImage.getDate().get(Calendar.DAY_OF_MONTH));
        this.rating.setRating((int)selectedImage.getRating().getStarRating());
    }

    /**
     * Need to override back press to have data saved
     * when the back arrow is pressed.
     */
    @Override
    public void onBackPressed(){
        Log.d("METADATA", "Back button pressed. Saving data...");
        onSave();
    }

    @Click(R.id.save_button)
    public void onSave() {
        try {
            InputMethodManager imm =
                    (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
            if (!Patterns.WEB_URL.matcher(sourceURL.getText().toString()).matches()){
                Toast urlMismatchToast = Toast.makeText(getApplicationContext(),
                                         "The source url is invalid", Toast.LENGTH_LONG);
                sourceURL.requestFocus();
                imm.showSoftInput(sourceURL, 0);

                urlMismatchToast.show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(sourceEmail.getText().toString()).matches()) {
                Toast emailMismatchToast = Toast.makeText(getApplicationContext(),
                                           "The email address is invalid", Toast.LENGTH_LONG);
                sourceEmail.requestFocus();
                imm.showSoftInput(sourceEmail, 0);

                emailMismatchToast.show();
            } else {
                this.selectedImage.setName(name.getText().toString());
                this.selectedImage.setUrl(Uri.parse(sourceURL.getText().toString()));
                this.selectedImage.setKeyWords(stringToArrayList(keywords.getText().toString()));
                this.selectedImage.setSourceEmail(sourceEmail.getText().toString());
                this.selectedImage.setShare(shareToggle.isChecked());
                Calendar givenDate = Calendar.getInstance();
                givenDate.set(date.getYear(), date.getMonth(), date.getDayOfMonth());
                this.selectedImage.setDate(givenDate);
                this.selectedImage.setRating(Rating.newStarRating(Rating.RATING_5_STARS,
                                                                  rating.getNumStars()));
                Log.d("METADATA", new Integer(rating.getNumStars()).toString());

                Intent resultIntent = new Intent();
                resultIntent.putExtra("ImageData", selectedImage);
                resultIntent.putExtra("position", position);

                setResult(Activity.RESULT_OK, resultIntent);

                finish();
            }
        } catch ( ImageData.NameEmptyException e ) {
            Toast nameToast = Toast.makeText(getApplicationContext(),
                                             "Image must have a name", Toast.LENGTH_LONG);
            nameToast.show();
        } catch ( ImageData.EmailEmptyException e ) {
            Toast emailToast = Toast.makeText(getApplicationContext(),
                                              "Email field must not be empty", Toast.LENGTH_LONG);
            emailToast.show();
        }
    }

    public ArrayList<String> stringToArrayList(String s) {
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(
                                                         new ByteArrayInputStream(s.getBytes())));

        String temp;
        try {
            while ((temp = streamReader.readLine()) != null) {
                list.add(temp);
            }
        } catch (IOException e) {
            Log.e("METADATA", "Error converting keywords to ArrayList");
        }

        return list;
    }
}
