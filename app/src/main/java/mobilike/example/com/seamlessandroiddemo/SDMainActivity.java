package mobilike.example.com.seamlessandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilike.seamless.SeamlessConfig;


public class SDMainActivity extends Activity {

    private SDListAdapter listAdapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdmain);

        TextView versionCodeView = (TextView) findViewById(R.id.version_code);
        versionCodeView.setText("SDK Version 1.5.3");

        mListView = (ListView) findViewById(R.id.scenario_list);
        int cellHeight;
        String[] scenarios;

        final boolean tabletSize  = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize){
            SeamlessConfig.setAppToken("6031d1f6-36b5-4e5f-847f-47736ab67953", getApplicationContext());
            cellHeight = 260;
            scenarios = new String[] {"Asynchronous Data Fetch","Refresh and Paging", "Multiple Interstitial Request",
                    "Banner for All Screens","Banner Inside a Scroll View","Simple Video Player", "Grid View"};
        }else {
            SeamlessConfig.setAppToken("5e7eded8-a6e8-4556-b301-710cbb61d4a9", getApplicationContext());
            cellHeight = 100;
            scenarios = new String[] {"Asynchronous Data Fetch","Refresh and Paging", "Multiple Interstitial Request",
                    "Banner for All Screens","Banner Inside a Scroll View","Simple Video Player", "Feed Ad Customization"};
        }

        listAdapter = new SDListAdapter(this, R.layout.simple_row, scenarios, cellHeight);
        mListView.setAdapter(listAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) { // Asynchronous data fetch
                    Intent i = new Intent(SDMainActivity.this, SDTableActivity.class);
                    i.putExtra("MakeButtons", false);
                    startActivity(i);
                } else if (position == 1) { // Refresh and Paging
                    Intent i = new Intent(SDMainActivity.this, SDTableActivity.class);
                    i.putExtra("MakeButtons", true);
                    startActivity(i);
                } else if (position == 2) { // Multiple Insterstitial Request
                    startActivity(new Intent(SDMainActivity.this, SDFullPageAdActivity.class));
                } else if (position == 3) { // Banner for all screens
                    startActivity(new Intent(SDMainActivity.this, SDBannerActivity.class));
                } else if (position == 4) { // Banner Inside s scroll view
                    startActivity(new Intent(SDMainActivity.this, SDBannerInScrollActivity.class));
                } else if (position == 5) { // Simple Video Player
                    startActivity(new Intent(SDMainActivity.this, SimpleVideoActivity.class));
                } else if (position == 6) {
                    if (tabletSize) {
                        // Grid view for Tablet
                        startActivity(new Intent(SDMainActivity.this, SDGridViewActivity.class));
                    }else{
                        // Feed Ad Customization for phone
                        startActivity(new Intent(SDMainActivity.this, SDAppearanceActivity.class));
                    }
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sdmain, menu);
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
