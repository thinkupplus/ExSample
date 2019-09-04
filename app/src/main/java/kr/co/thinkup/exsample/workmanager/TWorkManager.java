package kr.co.thinkup.exsample.workmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import kr.co.thinkup.exsample.R;

/**
 * 2019-08-27 by yh.Choi
 */
public class TWorkManager extends AppCompatActivity {
    public static final String MESSAGE_STATUS = "message_status";

    public TextView     tvStatus;
    public Button       btnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workmanager);

        // 배터리 사용량 최적화 앱에서 제외
        // https://kakaomap.tistory.com/250

        tvStatus = findViewById(R.id.work_tv);
        btnSend = findViewById(R.id.work_btn);

        final WorkManager workManager = WorkManager.getInstance();
//        final OneTimeWorkRequest timeWorkRequest = new OneTimeWorkRequest.Builder(NotificationWork.class).build();

        Constraints myConstraints = new Constraints.Builder()
//                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build();


        final PeriodicWorkRequest.Builder workBuilder =
                new PeriodicWorkRequest.Builder(
                        NotificationWork.class,
                        15,
                        TimeUnit.MINUTES);

        workBuilder.addTag("periodic_work");
        workBuilder.setConstraints(myConstraints);

        final PeriodicWorkRequest workRequest = workBuilder.build();

        workManager.enqueue(workRequest);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                workManager.enqueue(timeWorkRequest);

//                workManager.getWorkInfosByTag("periodic_work");

//                workManager.enqueue(workRequest);
            }
        });

//        workManager.getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
//            @Override
//            public void onChanged(@Nullable WorkInfo workInfo) {
//                if(workInfo != null) {
//                    WorkInfo.State state = workInfo.getState();
//                    tvStatus.append(state.toString() + "\n");
//                }
//            }
//        });

//        workManager.getWorkInfoByIdLiveData(timeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
//            @Override
//            public void onChanged(@Nullable WorkInfo workInfo) {
//                if(workInfo != null) {
//                    WorkInfo.State state = workInfo.getState();
//                    tvStatus.append(state.toString() + "\n");
//                }
//            }
//        });
    }

    public void refreshPeriodicWork() {
        // default constraints
        Constraints myConstraints = new Constraints.Builder()
//                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    // https://github.com/patelviraj/WorkManagerDayCounter/blob/master/DayCounter/app/src/main/java/com/daycounter/viewmodel/DayIncrementViewModel.java

    }
}
