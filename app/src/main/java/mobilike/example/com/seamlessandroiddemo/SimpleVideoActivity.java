package mobilike.example.com.seamlessandroiddemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.mobilike.seamless.SeamlessPlayerManager;
import com.mobilike.seamless.listener.SeamlessPlayerManagerListener;
import com.mobilike.seamless.player.org.nexage.sourcekit.vast.VASTPlayer;


public class SimpleVideoActivity extends ActionBarActivity {

    private LinearLayout videoLayout;
    private VideoView simpleVideoView;
    Utility util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = Utility.newInstance();
        util.saveActivity(this);

        setContentView(R.layout.activity_simple_video);
        videoLayout = (LinearLayout) findViewById(R.id.simple_video_layout);
        simpleVideoView = (VideoView) findViewById(R.id.simple_video_view);

        SeamlessPlayerManagerListener seamlessPlayerManagerListener = new SeamlessPlayerManagerListener() {
            @Override
            public void onAdReady(VASTPlayer vastPlayer) {
                Toast.makeText(getApplicationContext(), "Video ad is ready", Toast.LENGTH_SHORT).show();
                if (vastPlayer != null) {
                    vastPlayer.play();
                }
            }

            @Override
            public void onAdFailed(String s) {
                Toast.makeText(getApplicationContext(), "Video ad failed", Toast.LENGTH_SHORT).show();
                startVideo();
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdCompleted() {
                Toast.makeText(getApplicationContext(), "Video ad completed", Toast.LENGTH_SHORT).show();
                startVideo();
            }

            @Override
            public void onAdDismissed() {
                Toast.makeText(getApplicationContext(), "Video ad dismissed", Toast.LENGTH_SHORT).show();
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
