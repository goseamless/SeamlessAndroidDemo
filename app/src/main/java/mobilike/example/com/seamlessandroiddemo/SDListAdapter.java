package mobilike.example.com.seamlessandroiddemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

       // return super.getView(position, convertView, parent);
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.simple_row, parent, false);
        }

        if (position % 8 == 0) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color1));
        }else if (position % 8 == 1){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color2));
        }else if (position % 8 == 2) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color3));
        }else if (position % 8 == 3){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color4));
        }else if (position % 8 == 4) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color5));
        }else if (position % 8 == 5){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color6));
        }else if (position % 8 == 6) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color7));
        }else if (position % 8 == 7){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color8));
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textViewRow);
        textView.setText(items[position]);

        return convertView;

    }
}
