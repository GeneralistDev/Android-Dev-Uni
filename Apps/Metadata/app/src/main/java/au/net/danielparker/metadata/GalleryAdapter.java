package au.net.danielparker.metadata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by danielparker on 14/09/14.
 */
public class GalleryAdapter extends ArrayAdapter<ImageData> {

    private final Context context;
    private final ArrayList<ImageData> values;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd/MM/yyyy");

    public GalleryAdapter(Context context, ArrayList<ImageData> values) {
        super(context, R.layout.gallery_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.gallery_item, parent, false);
        ImageView image = (ImageView) rowView.findViewById(R.id.Image);
        TextView title = (TextView) rowView.findViewById(R.id.Image_Title);
        TextView date = (TextView) rowView.findViewById(R.id.Image_Date);

        image.setImageResource(values.get(position).getImageId());
        title.setText(values.get(position).getName());
        date.setText(simpleDateFormat.format(values.get(position).getDate().getTime()));

        return rowView;
    }
}
