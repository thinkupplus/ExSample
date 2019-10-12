package kr.co.thinkup.exsample.SocketSingletone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import kr.co.thinkup.exsample.R;
import kr.co.thinkup.exsample.SocketSingletone.network.Message;
import kr.co.thinkup.exsample.SocketSingletone.network.NetworkUtils;
import kr.co.thinkup.exsample.SocketSingletone.network.struct.PValue;

/**
 * 2019-10-04 by yh.Choi
 */
public class WifiSocketActivity extends WifiBaseActivity {

    private static final String TAG = "WifiSocketActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wifisocket);

        Button btn_connect = findViewById(R.id.btn_wifi1);
        Button btn_move = findViewById(R.id.btn_wifi_next);

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToSmartLed();
            }
        });

        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToWifiSearchActivity();
            }
        });
    }

//    @Override
//    public void showConnectionResult(String result) {
//        Log.d(TAG, "showConnectionResult: ");
//        if(result.equals("SUCCESS")) {
//            Log.d(TAG, "showConnectionResult: 성공");
//            PValue get_version = new PValue();
//
//            get_version.setSize(10);
//            get_version.setMessage(Message.PK_MSG_GET_FIRMWARE_VERSION);
//            get_version.setValue(0);
//
//            iSocket.sendMessage(get_version.toBytes());
//
//        }else {
//            Log.d(TAG, "showConnectionResult: 실패");
//            socketClose();
//            showAlert("["+SSID+"]\n 디바이스 연결을 실패 하였습니다..\n[" + result + "]");
//        }
//    }
//
//    @Override
//    public void setMessageReceived(byte[] pbyte) {
//        Log.d(TAG, "setMessageReceived: ");
//        int message = NetworkUtils.getMessage(pbyte);
//
//        if(message == Message.PK_MSG_GET_FIRMWARE_VERSION) {
//            PValue pValue = NetworkUtils.getPValue(pbyte);
//            if(pValue.getValue() == 1) {
//                showAlert("정상적으로 디바이스를 인식하여 다음으로 이동합니다. ");
//            }
//        }
//    }


    @Override
    protected void connectionResult(String result) {
        Log.d(TAG, "showConnectionResult: 성공");
        PValue get_version = new PValue();

        get_version.setSize(10);
        get_version.setMessage(Message.PK_MSG_GET_FIRMWARE_VERSION);
        get_version.setValue(0);

        iSocket.sendMessage(get_version.toBytes());
    }

    @Override
    protected void messageReceived(byte[] pbyte) {
        Log.d(TAG, "messageReceived: ");
        int message = NetworkUtils.getMessage(pbyte);

        if(message == Message.PK_MSG_GET_FIRMWARE_VERSION) {
            PValue pValue = NetworkUtils.getPValue(pbyte);
            if(pValue.getValue() == 1) {
                showAlert("정상적으로 디바이스를 인식하여 다음으로 이동합니다. ");
            }
        }
    }

    private void moveToWifiSearchActivity() {
        Intent wifiInfo = new Intent(getApplication(), WifiSocket1Activity.class);
        startActivity(wifiInfo);
        finish();
    }
}
