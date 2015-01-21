package mobilike.example.com.seamlessandroiddemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.liverail.library.AdView;
import com.liverail.library.events.VPAIDAdErrorEvent;
import com.liverail.library.events.VPAIDEvent;
import com.liverail.library.events.VPAIDEventListener;
import com.mobilike.seamless.SeamlessPlayerManager;
import com.mobilike.seamless.listener.SeamlessPlayerManagerListener;

import java.util.Map;


public class SimpleVideoActivity extends ActionBarActivity {

    private LinearLayout videoLayout;
    private VideoView simpleVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_video);
        videoLayout = (LinearLayout) findViewById(R.id.simple_video_layout);
        simpleVideoView = (VideoView) findViewById(R.id.simple_video_view);

        SeamlessPlayerManagerListener seamlessPlayerManagerListener = new SeamlessPlayerManagerListener() {

            @Override
            public void onAdPrepared(final AdView adView,final Map<String, Object> params) {

                VPAIDEventListener onAdLoaded = new VPAIDEventListener() {

                    @Override
                    public void onEvent(VPAIDEvent event) {

                        // Create container View to hold the AdView
                        // Remove all views from the container and add the AdView
                        videoLayout.removeAllViews();
                        videoLayout.addView(adView);

                        Toast.makeText(getApplicationContext(), "Video ad loaded", Toast.LENGTH_SHORT).show();
                        // Then start the video ad
                        adView.startAd();
                    }
                };

                VPAIDEventListener onAdStopped = new VPAIDEventListener() {

                    @Override
                    public void onEvent(VPAIDEvent event) {
                        // Ad Stopped
                        videoLayout.removeAllViews();
                        videoLayout.addView(simpleVideoView);
                        startVideo();
                    }
                };

                VPAIDEventListener onAdError = new VPAIDEventListener() {

                    @Override
                    public void onEvent(VPAIDEvent event) {
                        // Error
                        Toast.makeText(getApplicationContext(), "Video ad error", Toast.LENGTH_SHORT).show();
                        startVideo();
                    }
                };

                // Add the listeners that you defined above
                adView.addEventListener(VPAIDEvent.AdLoaded, onAdLoaded);
                adView.addEventListener(VPAIDEvent.AdStopped, onAdStopped);
                adView.addEventListener(VPAIDAdErrorEvent.AdError, onAdError);

                // Initialize the ad
                adView.initAd(params);
            }

            @Override
            public void onAdFailed(String error) {
                Toast.makeText(getApplicationContext(), "Video ad failed", Toast.LENGTH_SHORT).show();
                startVideo();
            }

        };
        SeamlessPlayerManager seamlessPlayerManager = new SeamlessPlayerManager.Builder(this)
                .entity("simple-video-player")
                .listener(seamlessPlayerManagerListener)
                .build();

    }

    public void startVideo()
    {
        String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        VideoView videoView = (VideoView)findViewById(R.id.simple_video_view);
        videoView.setVideoPath(url);
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_video, menu);
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
