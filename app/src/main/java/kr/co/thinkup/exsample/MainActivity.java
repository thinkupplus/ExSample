package kr.co.thinkup.exsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kr.co.thinkup.exsample.background.RealService;
import kr.co.thinkup.exsample.camera.CameraActivity;
import kr.co.thinkup.exsample.compoent.TComponent;
import kr.co.thinkup.exsample.httprequest.HttpRequestActivity;
import kr.co.thinkup.exsample.textview.TTextView;
import kr.co.thinkup.exsample.viewpager.ViewPagerActivity;
import kr.co.thinkup.exsample.workmanager.TWorkManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "MainActivity";

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

        Button btnText = findViewById(R.id.btn_textview);
        Button btnComp = findViewById(R.id.btn_component);
        Button btnView = findViewById(R.id.btn_viewpager);
        Button btnCame = findViewById(R.id.btn_camera);
        Button btnHttp = findViewById(R.id.btn_http);
        Button btnWork = findViewById(R.id.btn_work);


        btnText.setOnClickListener(this);
        btnComp.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnCame.setOnClickListener(this);
        btnHttp.setOnClickListener(this);
        btnWork.setOnClickListener(this);

        //        initService();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_textview:
                Log.d(TAG, "onClick: textview");
                Intent textview = new Intent(this, TTextView.class);
                startActivity(textview);
                break;
            case R.id.btn_component:
                Log.d(TAG, "onClick: compoent");
                Intent component = new Intent(this, TComponent.class);
                startActivity(component);
                break;
            case R.id.btn_viewpager:
                Intent viewpager = new Intent(this, ViewPagerActivity.class);
                startActivity(viewpager);
                break;
            case R.id.btn_camera:
                Intent camera = new Intent(this, CameraActivity.class);
                startActivity(camera);
                break;
            case R.id.btn_http:
                Intent http = new Intent(this, HttpRequestActivity.class);
                startActivity(http);
                break;
            case R.id.btn_work:
                Intent work = new Intent(this, TWorkManager.class);
                startActivity(work);
                break;

        }
    }

    private void initService() {
//        https://forest71.tistory.com/185
        PowerManager pm = (PowerManager)getApplicationContext().getSystemService(POWER_SERVICE);
        boolean isWhiteListing = false;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isWhiteListing = pm.isIgnoringBatteryOptimizations(getApplicationContext().getPackageName());
        }

        if(!isWhiteListing) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivity(intent);
        }

        if(RealService.serviceIntent == null) {
            serviceIntent = new Intent(this, RealService.class);
            startService(serviceIntent);
        }else {
            serviceIntent = RealService.serviceIntent;
            Toast.makeText(getApplicationContext(), "already", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        if(serviceIntent != null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }

    @Override
    protected void onStart() {
        // 백그라운드에서 돌아올때 구현
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        // 백그라운드로 들어갈때 구현
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onPostCreate: ");
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onPostResume() {
        Log.d(TAG, "onPostResume: ");
        super.onPostResume();
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory: ");
        super.onLowMemory();
    }
}
