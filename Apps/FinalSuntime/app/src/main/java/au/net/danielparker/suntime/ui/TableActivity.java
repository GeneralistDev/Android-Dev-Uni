package au.net.danielparker.suntime.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import au.net.danielparker.suntime.R;
import au.net.danielparker.suntime.models.Location;
import au.net.danielparker.suntime.models.Suntime;

@EActivity(R.layout.activity_table)
public class TableActivity extends ListActivity {
    private ArrayList<Suntime> data = new ArrayList<Suntime>();
    private ArrayAdapter<Suntime> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseUI(getIntent().getExtras());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id ) {
        Suntime selectedItem = (Suntime)getListView().getItemAtPosition(position);
        Intent intent = new Intent(this, SuntimeActivity.class);
        intent.putExtra("location", selectedItem.location);
        intent.putExtra("date", selectedItem.date);

        startActivity(intent);
    }

    private void initialiseUI(Bundle bundle) {
        Location location = (Location)bundle.get("location");
        Calendar start = (Calendar)bundle.get("start");
        Calendar end = (Calendar)bundle.get("end");

        while (start.before(end)) {
            Suntime suntime = new Suntime(location, (Calendar)start.clone());
            data.add(suntime);
            start.add(Calendar.SECOND, 86400);
        }

        Suntime suntime = new Suntime(location, end);
        data.add(suntime);

        adapter = new SuntimeListAdapter(this, data);
        setListAdapter(adapter);
    }
}
