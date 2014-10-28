package au.net.danielparker.suntime.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

import au.net.danielparker.suntime.R;

/**
 * Created by danielparker on 28/10/14.
 */
@EFragment(R.layout.activity_custom_location)
public class CustomFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
