package au.net.danielparker.suntime.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EFragment;

import au.net.danielparker.suntime.R;

/**
 * Created by danielparker on 28/10/14.
 */
@EFragment(R.layout.activity_custom_location)
public class CustomFragment extends Fragment {

    private MapFragment fragment;
    private GoogleMap googleMap;
    private ViewGroup mapContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapContainer = container;
        return inflater.inflate(R.layout.activity_custom_location, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (MapFragment)fm.findFragmentById(R.id.map_fragment);
        if (fragment == null) {
            fragment = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_fragment, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(googleMap == null) {
            googleMap = fragment.getMap();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(0,0)));
        }
    }
}
