package mobilike.example.com.seamlessandroiddemo;

import android.app.Activity;

/**
 * Created by suzykang on 03/02/15.
 */
public class Utility {
    Activity activity;
    public static Utility newInstance()
    {
        return new Utility();
    }

    public void saveActivity (Activity activity){
        this.activity = activity;
    }

    public boolean activityIsAlive()
    {
        if (this.activity != null && !this.activity.isFinishing()) {
            return true;
        }else{
            return false;
        }
    }
}
