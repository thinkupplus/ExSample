package kr.co.thinkup.exsample;

import android.app.Application;
import android.util.Log;

/**
 * 2019-09-04 by yh.Choi
 */
public class Globals extends Application {
    
    private static final String TAG = "Globals";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: ");
    }
}
