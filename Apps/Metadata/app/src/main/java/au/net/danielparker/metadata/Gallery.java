package au.net.danielparker.metadata;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

@EActivity(R.layout.activity_gallery)
public class Gallery extends ListActivity {

    private boolean navFired = false;

    private ArrayList<ImageData> galleryData = new ArrayList<ImageData>();
    private ArrayAdapter<ImageData> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundledData = getIntent().getExtras();

        if (bundledData != null && bundledData.containsKey("Theme")) {
            setTheme(bundledData.getInt("Theme"));
        }

        // Create actionbar dropdown spinner from stringarray
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(
                                         getActionBar().getThemedContext(),
                                         R.array.text_sizes,
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

        if (bundledData != null && bundledData.containsKey("Theme")) {
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

        setContentView(R.layout.activity_gallery);

        initialiseUI();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ImageData selectedItem = (ImageData) getListView().getItemAtPosition(position);
        editMetadataForItem(selectedItem, position);
    }

    private void editMetadataForItem(ImageData selectedItem, int position) {
        Intent intent = new Intent(this, EditMetadata_.class );
        intent.setAction(Intent.ACTION_EDIT);
        intent.putExtra("ImageData", selectedItem);
        intent.putExtra("position", position);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (requestCode == 1) {
            if (responseCode == RESULT_OK) {
                ImageData editedSelection = data.getParcelableExtra("ImageData");
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    galleryData.set(position, editedSelection);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("METADATA", "Error getting edited metadata: Invalid index");
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_list, menu);
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

    private void initialiseUI() {
        this.galleryData = loadGalleryItems();
        adapter = new GalleryAdapter(this, galleryData);

        setListAdapter(adapter);
    }

    private ArrayList<ImageData> loadGalleryItems() {
        ArrayList<ImageData> images = new ArrayList<ImageData>();
        try {
            images.add(new ImageData("Flowers", "mail@example.com", R.drawable.flowers));
            images.add(new ImageData("Food", "mail@example.com", R.drawable.food));
        } catch (Exception e ) {

        }

        return images;
    }
}
