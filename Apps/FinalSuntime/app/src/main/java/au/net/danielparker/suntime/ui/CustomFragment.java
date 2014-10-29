package au.net.danielparker.suntime.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EFragment;

import java.util.TimeZone;

import au.net.danielparker.suntime.R;
import au.net.danielparker.suntime.models.Location;

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
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                new AlertDialog.Builder(getActivity())
                    .setTitle("Confirm location")
                    .setMessage("Would you like see suntimes for \n" +
                                "Latitude: " + latLng.latitude + "\n" +
                                "Longitude: " + latLng.longitude + "?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Location location = new Location("Custom Location", latLng.latitude, latLng.longitude, TimeZone.getDefault() );
                            Intent intent = new Intent(getActivity(), DatePick_.class );
                            intent.putExtra("location", location);

                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            }
        });
    }
}
