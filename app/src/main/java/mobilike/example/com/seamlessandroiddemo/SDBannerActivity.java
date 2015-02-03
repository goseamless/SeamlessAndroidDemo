package mobilike.example.com.seamlessandroiddemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mobilike.seamless.BannerManager;
import com.mobilike.seamless.listener.BannerManagerListener;
import com.mobilike.seamless.util.AdCategory;
import com.mobilike.seamless.view.SeamlessMMAView;


public class SDBannerActivity extends ActionBarActivity {

    SeamlessMMAView mAdView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdbanner);

        mAdView  = (SeamlessMMAView) findViewById(R.id.adView);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://goseamless.com");


        BannerManagerListener bannerManagerListener = new BannerManagerListener() {
            @Override
            public void onBannerLoad(FrameLayout bannerView) {
                mAdView.setVisibility(View.VISIBLE);
                mAdView.removeAllViews();
                mAdView.addView(bannerView);
                Toast.makeText(getApplicationContext(), "Banner ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFailed(FrameLayout bannerView, String error) {
                mAdView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Banner ad failed", Toast.LENGTH_SHORT).show();
            }
        };

        BannerManager bannerManager = new BannerManager.Builder(this)
                // Should specify your current content like "yourapp-yourcontent-adtype"
                // i.e : "yourapp-sports-banner"
                .entity("seamless-simple-banner")
                .adView("250x150") // This is OPTIONAL i.e. = "250x150"/ the size of your SeamlessMMAView
                .category(AdCategory.AdCategories.Sports) // Select proper category eg: News, Sports etc.
                .listener(bannerManagerListener)
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sdbanner, menu);
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
