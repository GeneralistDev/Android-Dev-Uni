package au.net.danielparker.imageintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_image_gallery)
public class ImageGallery extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_gallery, menu);
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


    /**
     * Create intent for lasagna press.
     */
    @Click(R.id.lasagna_image)
    void lasangaImage() {
        Intent imageIntent = new Intent(this, ImageActivity_.class);
        imageIntent.putExtra("image_name", R.drawable.lasagna);
        imageIntent.putExtra("desc", R.string.lasagna);
        startActivity(imageIntent);
    }

    /**
     * Create intent for pasta press.
     */
    @Click(R.id.pasta_image)
    void pastaImage() {
        Intent imageIntent = new Intent(this, ImageActivity_.class);
        imageIntent.putExtra("image_name", R.drawable.pasta);
        imageIntent.putExtra("desc", R.string.pasta);
        startActivity(imageIntent);
    }

    /**
     * Create intent for porridge press.
     */
    @Click(R.id.porridge_image)
    void porridgeImage() {
        Intent imageIntent = new Intent(this, ImageActivity_.class);
        imageIntent.putExtra("image_name", R.drawable.porridge);
        imageIntent.putExtra("desc", R.string.porridge);
        startActivity(imageIntent);
    }

    /**
     * Create intent for sardines press.
     */
    @Click(R.id.sardines_image)
    void sardinesImage() {
        Intent imageIntent = new Intent(this, ImageActivity_.class);
        imageIntent.putExtra("image_name", R.drawable.sardines);
        imageIntent.putExtra("desc", R.string.sardines);
        startActivity(imageIntent);
    }
}
