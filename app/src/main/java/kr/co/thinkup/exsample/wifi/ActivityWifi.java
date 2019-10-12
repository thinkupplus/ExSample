package kr.co.thinkup.exsample.wifi;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.thinkup.exsample.R;

/**
 * 2019-10-03 by yh.Choi
 */
public class ActivityWifi extends AppCompatActivity {

    private static final String TAG = "ActivityWifi";
    private final int                   REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;

    private WifiUtil        wifiUtil;
    private TextView        wifi_name;

    private int             select_mode = 0;
    String szwifi_Name = "", wifi_pass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wifi);

        initWifiPermission();

        wifiUtil = WifiUtil.getInstance();

        Button btn_wifi1 = findViewById(R.id.wifi_1);
        Button btn_wifi2 = findViewById(R.id.wifi_2);
        Button btn_curre = findViewById(R.id.btn_current_wifi);
        wifi_name = findViewById(R.id.tv_wifi_Name);

        initWiriBroadcastFilter();


        btn_wifi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToRouter(1);
            }
        });

        btn_wifi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToRouter(2);
            }
        });

        btn_curre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSSID();
            }
        });
    }

    private void getSSID() {
        String ssid = wifiUtil.getSSID(ActivityWifi.this);
        wifi_name.setText(ssid);
    }

    private void connectToRouter(int nMode) {

        if(!wifiUtil.isEnableWIFI(this)) {
            Toast.makeText(this, "WIFI Disable", Toast.LENGTH_SHORT).show();
            return;
        }

//        if(!wifiUtil.isConnectWifi(this)) {
//            Toast.makeText(this, "Current is LTE", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        String ssid = wifiUtil.getSSID(this);
//        String bssid = wifiUtil.getBSSID(this);


//
        if(nMode == 1) {
//            szwifi_Name = "iptime_11";  // olleh_WiFi_CAD1
//            wifi_pass = "ucore2017";  // 0000007251
            szwifi_Name = "olleh_WiFi_CAD1";
            wifi_pass = "0000007251";
//            szwifi_Name = "Yong is iPhone";  // olleh_WiFi_CAD1
//            wifi_pass = "123456789a";  // 0000007251
        }else {
            szwifi_Name = "ThinKF_LED";
            wifi_pass = "1234567890";
        }
        select_mode = nMode;
//
//        wifiUtil.setSSIDandWPA(ap,pass);
////        wifiUtil.setSSIDandWPA("Yong is iPhone","123456789a");
//
////        if(ssid.equals("iptime_11")) {
//        Log.d(TAG, "connectToRouter: ssid  = " + ap);
//        Log.d(TAG, "connectToRouter: pass = " + pass);
////            return;
////        }

        this.wifi_name.setText(szwifi_Name);
        wifiUtil.setSSIDandWPA(szwifi_Name, wifi_pass);

        Log.d(TAG, "connectToRouter: WIFI_NAME = " + szwifi_Name);
        Log.d(TAG, "connectToRouter: WIFI_PASS = " + wifi_pass);

//        boolean ret = wifiUtil.connectWifi(this, InfWifiConsts.WIFI_CONFIG_WPA2);

        WifiConfiguration wifiConfig = new WifiConfiguration();

        wifiConfig.SSID = String.format("\"%s\"", szwifi_Name);
//        wifiConfig.priority = 40;
//
//        wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
//        wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
//        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//        wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
//        wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
//        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

        wifiConfig.preSharedKey = String.format("\"%s\"", wifi_pass);

        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        int netId = wifiManager.addNetwork(wifiConfig);

//        Log.d(TAG, "connectToRouter: networkid = " + netId);

//        if(netId != -1) {
//            Log.d(TAG, "connectToRouter: Network " + netId);
//        }else {
//            Log.d(TAG, "connectToRouter: Network -1 이다");
//        }

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + szwifi_Name + "\"")) {
                Log.d(TAG, "connectToRouter: list ssid = " + i.SSID);
                Log.d(TAG, "connectToRouter: list networkId = " + i.networkId);
                wifiManager.disconnect();
                boolean bEnableNetwork = wifiManager.enableNetwork(i.networkId, true);
                if(bEnableNetwork) {
                    Log.d(TAG, "Wifi Connected!!");
                }else {
                    Log.d(TAG, "Wifi Disconnected!!");
                }
                wifiManager.reconnect();

                break;
            }
        }



//        wifiManager.disconnect();
//
//        boolean bEnableNetwork  = wifiManager.enableNetwork(netId, true);
//
//        if(bEnableNetwork) {
//            Log.d(TAG, "Wifi Connected!!");
//
//        }else {
//            Log.d(TAG, "Wifi Disconnected!!");
//        }
//
//        boolean isReconnected = wifiManager.reconnect();
//        // 여기서  true가 나와야 한다.
//        Log.d(TAG, "connectWifi: isReconnected = " + isReconnected);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiBroadCastReceiver);
    }

    /**
     * BroadCast Filter Init
     */
    public void initWiriBroadcastFilter() {
        IntentFilter intentFilter = new IntentFilter();

//        intentFilter.addAction(WifiManager.NETWORK_IDS_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION); // 와이파이상태
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION); //Wifi On/Off 상태
//        intentFilter.addAction(getResources().getString(R.string.action_connectivity_change));
        registerReceiver(wifiBroadCastReceiver, intentFilter);
    }

    /**
     * BroadcastReceiver Receive
     */
    private final BroadcastReceiver wifiBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(TextUtils.isEmpty(action) || action.length() < 1) {
                return;
            }
            if(action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int wifiSate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
                switch (wifiSate) {
                    case WifiManager.WIFI_STATE_DISABLING :
                        Log.d(TAG, "onReceive: Wifi 비활성화중");
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        Log.d(TAG, "onReceive: Wifi 비활성화");
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        Log.d(TAG, "onReceive: WIfi 활성화중");
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        Log.d(TAG, "onReceive: Wifi 활성화");
                        break;
                    default :
                        Log.d(TAG, "onReceive: Unknown");
                        break;
                }
            }else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = (NetworkInfo)intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                // Network State Get
                NetworkInfo.DetailedState state = info.getDetailedState();

                String typeName = info.getTypeName();

                switch (state) {
                    case CONNECTED :
//                        if(checkRouter()) {
                        Log.d(TAG, "onReceive: CONNECTED ");
//                        }else {
//                            appendWifiStatus("연결에 실패하였습니다.");
//                            appendWifiStatus("공유기 정보를 다시 확인하여 주세요.");
//
//                        }
                        // WIFI 최종 연결시점

//                        String ssid = wifiUtil.getSSID(ActivityWifi.this);
//
//                        if (!szwifi_Name.equals(ssid)) {
//                            try {
//                                Thread.sleep(1500);
//
//                                connectToRouter(select_mode);
//
//                            }catch (InterruptedException e) {
//
//                            }
//
//                        }

                        break;
                    case DISCONNECTED:
                        Log.d(TAG, "onReceive: DISCONNECTED ");
                        break;
                    case IDLE:
                        Log.d(TAG, "onReceive: IDLE ");
                        break;
                    case FAILED:
                        Log.d(TAG, "onReceive: FAILED ");
                        break;
                    case BLOCKED:
                        Log.d(TAG, "onReceive: BLOCKED ");
                        break;
                    case SCANNING:
                        Log.d(TAG, "onReceive: SCANNING");
                        break;
                    case SUSPENDED:
                        Log.d(TAG, "onReceive: SUSPENDED");
                        break;
                    case CONNECTING:
                        Log.d(TAG, "onReceive: CONNECTING");
                        break;
                    case DISCONNECTING:
                        Log.d(TAG, "onReceive: DISCONNECTING");
                        break;
                    case AUTHENTICATING:
                        Log.d(TAG, "onReceive: AUTHENTICATING");
                        break;
                    case OBTAINING_IPADDR:
                        Log.d(TAG, "onReceive: OBTAINING_IPADDR ");
                        break;
                    case VERIFYING_POOR_LINK:
                        Log.d(TAG, "onReceive: VERIFYING_POOR_LINK ");
                        break;
                    case CAPTIVE_PORTAL_CHECK:
                        Log.d(TAG, "onReceive: CAPTIVE_PORTAL_CHECK");
                        break;

                    default:
                        Log.d(TAG, "onReceive: default = " + state);
                        break;
                }
            }else {
                Log.d(TAG, "onReceive:  action = " + action);
            }
        }
    };


    public void initWifiPermission() {
        // Wifi
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            checkandAskPermission();
        }
    }

    /**
     * Permissions Chage Result
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                for(int i=0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }
                if(perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    wifiManager.startScan();
                }else {
                    // Permission Denied
                    Log.d(TAG, "onRequestPermissionsResult: Some Permission is Denied");
                }
                break;
        }
    }

    /**
     * Check Permission
     * TODO : 권한 요청 작업 이쁘게 하여 주기
     * google에서 검색하여 이쁘게 만들어 주자. android custom permission request dialog
     *
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkandAskPermission() {
        List<String> permissionNeeded = new ArrayList<>();

        final List<String> permissionsList = new ArrayList<String>();
        if(!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            permissionNeeded.add("Network");
        }

        if(permissionsList.size() > 0 ) {
            if(permissionNeeded.size() > 0 ) {
                // Need Rationale
//                String message; //= "You need to grant access to %s";
                //+ permissionNeeded.get(0);
                for (int i = 0; i < permissionNeeded.size(); i++) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }

    /**
     * Permission Add
     * @param permissionList
     * @param permission
     * @return
     */
    private boolean addPermission(List<String> permissionList, String permission) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
                if(!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

}
