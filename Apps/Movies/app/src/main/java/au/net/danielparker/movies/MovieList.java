package au.net.danielparker.movies;

import android.app.ListActivity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class MovieList extends ListActivity {

    private ArrayList<Movie> listData = new ArrayList<Movie>();
    private ArrayAdapter<Movie> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        initializeUI();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Movie selectedItem = (Movie) getListView().getItemAtPosition(position);
        Toast ratingToast = Toast.makeText(getApplicationContext(), selectedItem.getRating(), Toast.LENGTH_LONG);
        ratingToast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_list, menu);
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

    public void initializeUI() {
        AssetManager assetManager = getAssets();

        try {
            this.listData = Movie.loadMovies(assetManager.open("movies.json"));
            this.adapter = new MovieListAdapter(this, this.listData);

            setListAdapter(adapter);

        } catch (IOException e) {
            Log.e("MOVIES", e.getMessage());
        }
    }
}
