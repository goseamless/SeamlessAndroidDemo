package mobilike.example.com.seamlessandroiddemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by suzykang on 15/01/15.
 */
public class SDListAdapter extends ArrayAdapter<String>{

    Context mContext;
    private String[] items;
    public SDListAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        items = objects;
        mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return super.getView(position, convertView, parent);
        /*if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.simple_row, parent, false);
        }

        if (position % 2 == 0)
            convertView.setBackgroundColor(Color.RED);
        else
            convertView.setBackgroundColor(Color.GREEN);

        TextView textView = (TextView) convertView.findViewById(R.id.rowTextView);
        textView.setText(items[position]);

        return convertView;
        */
    }
}
