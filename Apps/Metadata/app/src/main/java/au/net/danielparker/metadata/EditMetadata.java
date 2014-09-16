package au.net.danielparker.metadata;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.ToggleButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

/**
 * Created by danielparker on 14/09/14.
 */
@EActivity(R.layout.activity_edit_metadata)
public class EditMetadata extends Activity {

    private ImageData selectedImage;

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
        selectedImage = bundledData.getParcelable("ImageData");
    }

    @AfterViews
    public void restoreData() {
        this.name.setText(selectedImage.getName());
        this.sourceURL.setText(selectedImage.getUrl());
        this.keywords.setText(selectedImage.getKeyWords());
        this.sourceEmail.setText(selectedImage.getSourceEmail());
        this.shareToggle.setChecked(selectedImage.getShare());
        this.date.updateDate(selectedImage.getDate().get(Calendar.YEAR), selectedImage.getDate().get(Calendar.MONTH), selectedImage.getDate().get(Calendar.DAY_OF_MONTH));
        this.rating.setNumStars((int)selectedImage.getRating().getStarRating());
    }
}
