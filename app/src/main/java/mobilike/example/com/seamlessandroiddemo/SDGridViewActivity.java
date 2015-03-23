package mobilike.example.com.seamlessandroiddemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.mobilike.seamless.FeedManager;
import com.mobilike.seamless.listener.FeedListener;
import com.mobilike.seamless.util.AdCategory;


public class SDGridViewActivity extends ActionBarActivity {

    FeedManager feedManager;
    GridView gridView;
    static final String[] numbers = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        gridView = (GridView) findViewById(R.id.gridView);
        //gridView.setBackgroundResource(R.drawable.gridview_portrait);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_grid_row, numbers);
        gridView.setAdapter(adapter);

        FeedListener feedListener = new FeedListener() {
            @Override
            public void onAdLoad(ListAdapter adapter, AdapterView.OnItemClickListener onItemClickListener) {
                // If ads loaded succesfully,
                // returns an adapter that contains feed ads
                gridView.setAdapter(adapter);

                // If you have an OnItemClickListener on your ListView
                // and you passed it to FeedManager.Builder
                // you should set it here (optional)
                //gridView.setOnItemClickListener(onOtemClickListener);
            }

            @Override
            public void onAdFailed(ListAdapter adapter) {
                // If ads fail to load, returns your own adapter
                gridView.setAdapter(adapter);
            }
        };

        feedManager = new FeedManager.Builder(this)
                // Should specify your current content like "yourapp-yourcontent-adtype"
                // i.e.: "myapp-sports-feed"
                .entity("seamless-grid-feed")
                .adapter(adapter)
                .listener(feedListener)
                .parentView(gridView) // or gridview
                //.onItemClickListener(onItemClickListener) // Optional - if you have an onItemClickListener, pass it here
                .category(AdCategory.AdCategories.News) // Select proper category eg: News, Sports etc.
                .build();
    }

    @Override
    protected void onDestroy() {
        if (feedManager != null){
            feedManager.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
