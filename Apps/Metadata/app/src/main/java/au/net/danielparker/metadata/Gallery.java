package au.net.danielparker.metadata;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_gallery)
public class Gallery extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
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

    @Click(R.id.flowers_row)
    void flowersRow() {
        Toast toast = Toast.makeText(this.getApplicationContext(), "Got flowers click", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Click(R.id.food_row)
    void foodRow() {
        Toast toast = Toast.makeText(this.getApplicationContext(), "Got food click", Toast.LENGTH_SHORT);
        toast.show();
    }
}
