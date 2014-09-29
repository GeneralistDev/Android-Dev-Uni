package au.net.danielparker.suntime.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.EActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import au.net.danielparker.suntime.R;
import au.net.danielparker.suntime.models.Location;

/**
 * Created by danielparker on 29/09/14.
 */

@EActivity(R.layout.activity_locations)
public class LocationsActivity extends ListActivity {
    private ArrayList<Location> listData = new ArrayList<Location>();
    private ArrayAdapter<Location> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseUI();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Location selectedItem = (Location) getListView().getItemAtPosition(position);
        showSuntimesForItem(selectedItem);
    }

    private void showSuntimesForItem(Location selectedItem) {
        Intent intent = new Intent(this, SuntimeActivity.class);
        intent.putExtra("location", selectedItem);
        startActivity(intent);
    }

    private void initialiseUI() {
        AssetManager assetManager = getAssets();
        try {

            InputStream inputStream = assetManager.open("au_locations.txt");
            this.listData = Location.loadLocations(inputStream);
            adapter = new LocationsAdapter(this, this.listData);

            setListAdapter(adapter);
        } catch (IOException e) {
            Log.e("SUNTIME", e.getMessage());
        }
    }
}
