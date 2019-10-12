package kr.co.thinkup.exsample.SocketSingletone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import kr.co.thinkup.exsample.R;
import kr.co.thinkup.exsample.SocketSingletone.network.Message;
import kr.co.thinkup.exsample.SocketSingletone.network.NetworkUtils;
import kr.co.thinkup.exsample.SocketSingletone.network.struct.PData;
import kr.co.thinkup.exsample.SocketSingletone.network.struct.PValue;
import kr.co.thinkup.exsample.SocketSingletone.socket.AsyncResponse;
import kr.co.thinkup.exsample.SocketSingletone.socket.SocketConnect;
import kr.co.thinkup.exsample.wifi.WifiUtil;

/**
 * 2019-10-04 by yh.Choi
 */
public abstract class WifiBaseActivity extends AppCompatActivity implements AsyncResponse{

    private static final String TAG = "WifiBaseActivity";

    public String           SSID ;
    public String           BSSID;
    public String           serverIP;
    public int              serverPORT = 15830;

    public WifiUtil         wifiUtil;

    public static SocketConnect iSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wifiUtil = WifiUtil.getInstance();
    }

    public void connectToSmartLed() {

        SSID = wifiUtil.getSSID(WifiBaseActivity.this);
        BSSID = wifiUtil.getBSSID(WifiBaseActivity.this);
        serverIP = wifiUtil.getWifiAPInfo(WifiBaseActivity.this);

        AsyncResponse activityResponse = this;
        try {
            if(iSocket == null) {
                iSocket = SocketConnect.connect(serverIP, serverPORT, activityResponse/*, connectionSemaphore*/);
            }
        }catch (IOException/* | InterruptedException */e) {
            Log.e(TAG, "connectToSmartLed: ", e);
        }
    }

    @Override
    public void alertServerDown(String message) {
        showAlert(message);
    }

    @Override
    public void setMessageReceived(byte[] pbyte){
        Log.d(TAG, "setMessageReceived: ");

        messageReceived(pbyte);
//        int message = NetworkUtils.getMessage(pbyte);
//
//        if(message == Message.PK_MSG_GET_FIRMWARE_VERSION) {
//            PValue pValue = NetworkUtils.getPValue(pbyte);
//            if(pValue.getValue() == 1) {
////                recvPK_MSG_GET_FIRMWARE_VERSION(pValue.getValue());
//            }
//        }else if(message == Message.PK_MSG_GET_LIST_AP) {
//            PData pData = NetworkUtils.getPData(pbyte);
//            byte[] bAPList = pData.getData();
//            String szAPList = new String(bAPList);
//            Log.d(TAG, "setMessageReceived: " + szAPList);
////            getToAPList(szAPList);
//        }



    }

    protected abstract void connectionResult(String result);
    protected abstract void messageReceived(byte[] pbyte);
//    protected abstract void recvPK_MSG_GET_FIRMWARE_VERSION(int result);
//    protected abstract void getToAPList(String szAPList);

    @Override
    public void showConnectionResult(String result){
        Log.d(TAG, "showConnectionResult: ");
        connectionResult(result);
//        if(result.equals("SUCCESS")) {
//            Log.d(TAG, "showConnectionResult: 성공");
//            connectionResult(result);
//
//        }else {
//            Log.d(TAG, "showConnectionResult: 실패");
//            socketClose();
//            showAlert("["+SSID+"]\n 디바이스 연결을 실패 하였습니다..\n[" + result + "]");
//        }
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showAlert(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    public void socketClose() {
        if(iSocket != null) {
            iSocket.disconnect();
            iSocket = null;
        }
    }
}
