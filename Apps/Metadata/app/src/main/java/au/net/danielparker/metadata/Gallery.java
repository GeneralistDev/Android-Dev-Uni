package au.net.danielparker.metadata;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

@EActivity(R.layout.activity_gallery)
public class Gallery extends ListActivity {

    private ArrayList<ImageData> galleryData = new ArrayList<ImageData>();
    private ArrayAdapter<ImageData> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
