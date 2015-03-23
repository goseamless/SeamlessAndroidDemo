package mobilike.example.com.seamlessandroiddemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mobilike.seamless.FeedManager;
import com.mobilike.seamless.adapter.SeamlessAdapter;
import com.mobilike.seamless.listener.FeedListener;
import com.mobilike.seamless.util.AdCategory;

import java.util.ArrayList;


public class SDAppearanceActivity extends ActionBarActivity {

    ArrayAdapter<String> simpleAdapter;
    ListView mTableView;
    FeedListener feedListener;
    FeedManager feedManager;
    SeamlessAdapter sAdapter;
    Utility util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdappearance);

        util = Utility.newInstance();
        util.saveActivity(this);

        mTableView = (ListView) findViewById(R.id.table_list);

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i < 10; i++){
            list.add(i, "item "+ (i+1));
        }
        simpleAdapter = new ArrayAdapter<String>(this, R.layout.padded_row, R.id.textViewRow, list);
        mTableView.setAdapter(simpleAdapter);

        requestSeamless();
        setSeamlessAppearance();
    }

    public void requestSeamless()
    {
        feedListener = new FeedListener() {
            @Override
            public void onAdLoad(ListAdapter adapter, AdapterView.OnItemClickListener onItemClickListener) {
                // If ads loaded succesfully,
                // returns an adapter that contains feed ads

                // Prevent showing advertisement after the view is already popped
                if (util.activityIsAlive()) {
                    mTableView.setAdapter(adapter);
                    sAdapter = (SeamlessAdapter) adapter;
                }

                // If you have an OnItemClickListener on your ListView
                // and you passed it to FeedManager.Builder
                // you should set it here (optional)
                //mTableView.setOnItemClickListener(onOtemClickListener);
            }

            @Override
            public void onAdFailed(ListAdapter adapter){
                // If ads fail to load, returns your own adapter
                if (util.activityIsAlive()) {
                    mTableView.setAdapter(adapter);
                    sAdapter = null;
                }
            }
        };

        feedManager = new FeedManager.Builder(this)
                // Should specify your current content like "yourapp-yourcontent-adtype"
                // i.e.: "myapp-sports-feed"
                .entity("appearance-feed")
                .adapter(simpleAdapter)
                .listener(feedListener)
                .parentView(mTableView) // or gridview
                        //.onItemClickListener(onItemClickListener) // Optional - if you have an onItemClickListener, pass it here
                .category(AdCategory.AdCategories.News) // Select proper category eg: News, Sports etc.
                .build();
    }

    public void setSeamlessAppearance()
    {
        // Maia appearance
        feedManager.setMargins(16, 0, 0, 0); //bottom, top, left, right
        feedManager.setCardBackgroundID(R.drawable.maia_header_background);
        feedManager.setCardFooterBackgroundID(R.drawable.maia_footer_background);
        feedManager.setCardButtonBackgroundID(R.drawable.maia_button_background);

        // Maia text
        feedManager.setMaiaAppNameColor("#FFFFFF");
        feedManager.setMaiaAppNameSize(14.0f);
        feedManager.setMaiaSponsorColor("#FFFFFF");
        feedManager.setMaiaSponsorSize(11.0f);
        feedManager.setMaiaDescriptionColor("#FFFFFF");
        feedManager.setMaiaDescriptionSize(14.0f);
        feedManager.setMaiaCTAColor("#FFFFFF");
        feedManager.setMaiaCTASize(13.0f);
        feedManager.setMaiaTaglineColor("#58595B");
        feedManager.setMaiaTaglineSize(14.0f);
        feedManager.setMaiaDownloadInfoColor("#58595B");
        feedManager.setMaiaDownloadInfoSize(12.0f);

        // MRE appearance
        feedManager.setCustomAdFramePaddingTop(0);
        feedManager.setCustomAdFramePaddingBottom(0);

        // Container for all ad types
        feedManager.setContainerBackgroundID(R.drawable.container_background);

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
        getMenuInflater().inflate(R.menu.menu_sdappearance, menu);
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
