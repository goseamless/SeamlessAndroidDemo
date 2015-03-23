package mobilike.example.com.seamlessandroiddemo;

import android.content.Context;
import android.graphics.Color;
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
    public int cellHeight;

    public SDListAdapter(Context context, int textViewResourceId, String[] objects, int cellHeight) {
        super(context, textViewResourceId, objects);
        items = objects;
        mContext = context;
        this.cellHeight = cellHeight;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       // return super.getView(position, convertView, parent);
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.simple_row, parent, false);
        }

        if (position % 9 == 0) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color1));
        }else if (position % 9 == 1){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color2));
        }else if (position % 9 == 2) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color3));
        }else if (position % 9 == 3){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color4));
        }else if (position % 9 == 4) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color5));
        }else if (position % 9 == 5){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color6));
        }else if (position % 9 == 6) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color7));
        }else if (position % 9 == 7){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color8));
        }else if (position % 9 == 8){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color9));
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textViewRow);
        textView.setTextColor(Color.WHITE);
        textView.setText(items[position]);

        convertView.setMinimumHeight(260);
        return convertView;

    }
}
