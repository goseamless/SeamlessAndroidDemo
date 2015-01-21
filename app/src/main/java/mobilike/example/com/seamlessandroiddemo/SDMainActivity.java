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

    //private ArrayAdapter<String> listAdapter;
    private SDListAdapter listAdapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdmain);
        SeamlessConfig.setAppToken("5e7eded8-a6e8-4556-b301-710cbb61d4a9", getApplicationContext());

        TextView versionCodeView = (TextView) findViewById(R.id.version_code);
        versionCodeView.setText("SDK Version 1.4.0");

        mListView = (ListView) findViewById(R.id.scenario_list);

        /*String[] scenarios = new String[] {"Asynchronous Data Fetch", "Paging and Refresh",
                "Multiple Interstitial Request", "Banner for all screens", "Resizing View for Banner",
                "MRE inside a scroll view", "Simple Video Player", "Text 2", "Text3", "Text 4"};*/

        String[] scenarios = new String[] {"Simple Video Player"};

        listAdapter = new SDListAdapter(this, R.layout.simple_row, scenarios);
        mListView.setAdapter(listAdapter);
       // listAdapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(SDMainActivity.this, SimpleVideoActivity.class);
                        startActivity(intent);
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
