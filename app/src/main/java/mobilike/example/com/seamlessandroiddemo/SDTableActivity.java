package mobilike.example.com.seamlessandroiddemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mobilike.seamless.FeedManager;
import com.mobilike.seamless.adapter.SeamlessAdapter;
import com.mobilike.seamless.listener.FeedListener;
import com.mobilike.seamless.util.AdCategory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SDTableActivity extends ActionBarActivity implements AsyncResponse{

    ListView mTableView;
    ArrayList<String> musicList;
    ArrayAdapter<String> musicAdapter;
    Button refreshButton;
    Button pagingButton;
    boolean paging;
    FeedListener feedListener;
    FeedManager feedManager;
    String entity;
    SeamlessAdapter sAdapter;
    Utility util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdtable);

        util = Utility.newInstance();
        util.saveActivity(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null){
            return;
        }else{
            boolean makeButtons = extras.getBoolean("MakeButtons");
            if (makeButtons == false){
                LinearLayout layout = (LinearLayout) findViewById(R.id.button_layout);
                ((ViewManager)layout.getParent()).removeView(layout);
                entity = "seamless-async-data-table";
            }else{
                setupButtons();
                entity = "seamless-feed-paging-refresh";
            }
        }

        mTableView = (ListView) findViewById(R.id.table_list);
        musicList = new ArrayList<String>();
        musicAdapter = new ArrayAdapter<String>(this, R.layout.simple_row, musicList);
        mTableView.setAdapter(musicAdapter);

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

        getData();
    }

    private void setupButtons()
    {
        refreshButton = (Button) findViewById(R.id.refresh_button);
        pagingButton = (Button) findViewById(R.id.paging_button);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paging = false;
                getData();
            }
        });
        pagingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paging = true;
                getData();
            }
        });
    }

    private void getData(){
        DataFetchTask task = new DataFetchTask();
        task.delegate = this;
        task.execute(new String[]{"https://itunes.apple.com/tr/rss/topmusicvideos/limit=10/json"});
    }

    public void processData(String[] s) {
        // Paging doesn't need to call FeedManager again
        if (paging){
            int index = musicList.size();
            for (int i = 0; i < s.length; i++) {
                index ++;
                musicList.add(index + ". " + s[i]);
            }
        }else { // initial data fetch & Refresh
            musicList.clear();

            for (int i = 0; i < s.length; i++) {
                musicList.add((i+1) + ". " + s[i]);
            }
            // data is ready, so set up the ad manager
            setAdManager();
            mTableView.smoothScrollToPosition(0);
        }
        if (sAdapter != null) {  // If Super adapter (Your adapter + Seamless) exists, notify it
            sAdapter.notifyDataSetChanged();
        }else{                  // Otherwise, use your adapter.
            musicAdapter.notifyDataSetChanged();
        }

    }

    public void setAdManager(){

          feedManager = new FeedManager.Builder(this)
                // Should specify your current content like "yourapp-yourcontent-adtype"
                // i.e.: "myapp-sports-feed"
                .entity(entity)
                .adapter(musicAdapter)
                .listener(feedListener)
                .parentView(mTableView) // or gridview
                //.onItemClickListener(onItemClickListener) // Optional - if you have an onItemClickListener, pass it here
                .category(AdCategory.AdCategories.Entertainment) // Select proper category eg: News, Sports etc.
                .build();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sdtable, menu);
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

    @Override
    protected void onDestroy() {
        if (feedManager != null){
            feedManager.destroy();
        }
        super.onDestroy();
    }

    public class DataFetchTask extends AsyncTask<String, Void, String[]> {

        public AsyncResponse delegate = null;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null){
                progressDialog = new ProgressDialog(SDTableActivity.this);
                progressDialog.setMessage("Fetching Data...");
                progressDialog.show();
            }
        }

        @Override
        protected String[] doInBackground(String... urls) {

            if (urls.length == 0)
                return null;

            String response = "";
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urls[0]);

            try {
                HttpResponse serviceResponse = client.execute(httpGet);
                InputStream content = serviceResponse.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = reader.readLine()) != null) {
                    response += s;
                }
                content.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            String[] data = new String[]{};
            try {
                data = getDataFromJson(response);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String[] s) {
            delegate.processData(s);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        private String[] getDataFromJson(String jsonString) throws JSONException
        {
            String[] musicArray = new String[10];
            JSONObject jsonObj = new JSONObject(jsonString);
            JSONObject feed = jsonObj.getJSONObject("feed");
            JSONArray entries = feed.getJSONArray("entry");

            for (int i = 0; i < entries.length(); i++){
                JSONObject entry = entries.getJSONObject(i);
                JSONObject title = entry.getJSONObject("title");
                String label = title.getString("label");
                musicArray[i] = label;
            }
            return musicArray;
        }
    }
}

