package mobilike.example.com.seamlessandroiddemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobilike.seamless.InterstitialManager;
import com.mobilike.seamless.listener.InterstitialManagerListener;
import com.mobilike.seamless.mopub.mobileads.MoPubInterstitial;
import com.mobilike.seamless.util.AdCategory;


public class SDFullPageAdActivity extends ActionBarActivity {

    Button loadButton;
    InterstitialManagerListener intersititalManagerListener;
    InterstitialManager interstitialManager;
    Utility util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdfull_page_ad);

        util = Utility.newInstance();
        util.saveActivity(this);

        intersititalManagerListener = new InterstitialManagerListener() {
            // If the Interstitial Request is successful, but the Ad is not provided by ad server,
            // It doesn't trigger onInterstitialLoad callback. (Becasue ad is not loaded)
            @Override
            public void onInterstitialLoad(MoPubInterstitial mInterstitial, boolean isReady) {
                Toast.makeText(getApplicationContext(), "Interstitial loaded", Toast.LENGTH_SHORT).show();
                if(isReady) {
                    if (util.activityIsAlive()) {
                        mInterstitial.show();
                        Toast.makeText(getApplicationContext(), "Interstitial is showing", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onInterstitialFailed(String error) {
                if (util.activityIsAlive()) {
                    Toast.makeText(getApplicationContext(), "Interstitial failed", Toast.LENGTH_SHORT).show();
                }
            }
        };

        loadButton = (Button) findViewById(R.id.fullScreen_loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitialManager = new InterstitialManager.Builder(v.getContext())
                        // Should specify your current content like "yourapp-yourcontent-adtype"
                        // i.e. : "yourapp-sports-fullpagelayer"
                        .entity("seamless-full-screen-ad")
                        .listener(intersititalManagerListener)
                        .category(AdCategory.AdCategories.News) // Select proper category eg: News, Sports etc.
                        .build();

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (interstitialManager != null) {
            interstitialManager.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sdfull_page_ad, menu);
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
