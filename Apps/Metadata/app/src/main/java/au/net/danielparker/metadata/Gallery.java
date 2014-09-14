package au.net.danielparker.metadata;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

@EActivity(R.layout.activity_gallery)
public class Gallery extends ListActivity {

    private ArrayList<ImageData> galleryData = new ArrayList<ImageData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initialiseUI();
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
        ArrayList<ImageData> galleryData = loadGalleryItems();
        ArrayAdapter<ImageData> adapter = new GalleryAdapter(this, loadGalleryItems());

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
