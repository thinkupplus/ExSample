package kr.co.thinkup.exsample.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class WifiUtil implements InfWifiConsts {

    protected final static String TAG = "WifiUtil";

    private static WifiUtil _sharedWifiUtil = null;

    private String mSSID = null;
    private String mPWD = null;

    public static WifiUtil getInstance() {
        if(_sharedWifiUtil == null) {
            _sharedWifiUtil = new WifiUtil();
        }
        return _sharedWifiUtil;
    }

    private WifiUtil() {
        super();
    }

    /**
     * Set SSID, WPA
     * @param ssid
     * @param pwd
     */
    public void setSSIDandWPA(String ssid, String pwd) {
        mSSID = ssid;
        mPWD  = pwd;
    }

    /**
     * 현재 연결된 Wifi BSSID 확인
     * @param context
     * @return
     */
    public String getBSSID(Context context) {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager == null) {
            return "";
        }
        WifiInfo wifiInfo =  wifiManager.getConnectionInfo();
        String bssid = null;

        if(wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            bssid = wifiInfo.getBSSID().replace("\"", "");
        }

        Log.d(TAG, "BSSID = " + bssid);

        if(bssid == null) {
            bssid = "";
        }

        return bssid;
    }

    /**
     * 현재 연결된 Wifi SSID 확인
     * @param context
     * @return
     */
    public String getSSID(Context context) {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager == null) {
            return "";
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = null;

        if(wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            ssid = wifiInfo.getSSID().replace("\"", "");
        }else if(wifiInfo.getSupplicantState() == SupplicantState.SCANNING) {
            ssid = wifiInfo.getSSID().replace("\"", "");
        }

        Log.d(TAG, "SSID = " + ssid);

        if(ssid == null) {
            ssid = "";
        }

        return ssid;
    }

    /**
     * Wifi 사용가능 확인
     * @param context
     * @return
     */
    public boolean isEnableWIFI(Context context) {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(wifiManager == null) {
            return false;
        }
        return wifiManager.isWifiEnabled();
    }

    /**
     * Wifi ON/OFF
     * @param context
     * @param bMode
     */
    public void setWifiEnabled(Context context, boolean bMode) {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager == null) {
            return;
        }
        wifiManager.setWifiEnabled(bMode);
    }

    /**
     * Wifi 연결 확인
     * @param context
     * @return
     */
    public boolean isConnectWifi(Context context) {
        boolean bIsWifiConnect = false;
        ConnectivityManager oManaget = (ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if(oManaget == null) {
            return false;
        }

        NetworkInfo oInfo = oManaget.getActiveNetworkInfo();

        if(oInfo != null) {
            NetworkInfo.State oState = oInfo.getState();
            if(oState == NetworkInfo.State.CONNECTED) {
                switch (oInfo.getType()) {
                    case ConnectivityManager.TYPE_WIFI :
                        bIsWifiConnect = true;
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        break;
                    default:
                        break;
                }
            }
        }
        return bIsWifiConnect;
    }

    /**
     * 접속한 기기 Mac Address 받아오기
     * @param context
     * @return
     */
    public String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager == null) {
            return "";
        }
        String macAddress = wifiManager.getConnectionInfo().getMacAddress();
        Log.d(TAG, "MAC = " + macAddress);
        if(macAddress == null) {
            macAddress = "";
        }
        return macAddress;
    }

    /**
     * 접속한 기기 IP Address 받아오기
     * @param context
     * @return
     */
    public String getIPAddress(Context context) {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager == null) {
            return "";
        }
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String IP = String.format(Locale.getDefault(),"%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
        Log.d(TAG, "IP = " + IP);
        return IP;
    }

    public String getWifiAPInfo(Context context) {
        ConnectivityManager oManaget = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!isConnectWifi(context)) {
            return "";
        }

        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager == null) {
            return "";
        }
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();

//        int serverAddress = dhcpInfo.serverAddress;
//        String wServerAddress = String.format(Locale.getDefault(), "%d.%d.%d.%d", (serverAddress & 0xff), (serverAddress >> 8 & 0xff), (serverAddress >> 16 & 0xff), (serverAddress >> 24 & 0xff));

        int wIp = dhcpInfo.ipAddress;
        int wNetmask = dhcpInfo.netmask;
        int wGateway = dhcpInfo.gateway;
        int wDns1 = dhcpInfo.dns1;

        String ip = String.format(Locale.getDefault(), "%d.%d.%d.%d", (wIp & 0xff), (wIp >> 8 & 0xff), (wIp >> 16 & 0xff), (wIp >> 24 & 0xff));
        String subnet = String.format(Locale.getDefault(),"%d.%d.%d.%d", (wNetmask & 0xff), (wNetmask >> 8 & 0xff), (wNetmask >> 16 & 0xff), (wNetmask >> 24 & 0xff));
        String gateway = String.format(Locale.getDefault(),"%d.%d.%d.%d", (wGateway & 0xff), (wGateway >> 8 & 0xff), (wGateway >> 16 & 0xff), (wGateway >> 24 & 0xff));
        String dns = String.format(Locale.getDefault(),"%d.%d.%d.%d", (wDns1 & 0xff), (wDns1 >> 8 & 0xff), (wDns1 >> 16 & 0xff), (wDns1 >> 24 & 0xff));

        Log.d(TAG, "getWifiAPInfo: IP = " + ip);
        Log.d(TAG, "getWifiAPInfo: SUBNET = " + subnet);
        Log.d(TAG, "getWifiAPInfo: GATEWAY = " + gateway);
        Log.d(TAG, "getWifiAPInfo: DNS = " + dns);

        return gateway;
    }

    /**
     * Wifi Delete
     * @param context
     */
    public void removeWifiSpecialSSID(Context context) {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(isConnectWifi(context)) {
            if(mSSID != null && getSSID(context).equalsIgnoreCase(mSSID) && wifiManager != null) {
                int networkid = wifiManager.getConnectionInfo().getNetworkId();
                wifiManager.removeNetwork(networkid);
                wifiManager.saveConfiguration();
                wifiManager.disconnect();
                wifiManager.disableNetwork(networkid);
            }
        }
    }

    /**
     * Wifi 연결
     * @param context
     * @param wifiConfig
     */
    public boolean connectWifi(Context context, int wifiConfig) {

        boolean bRet = false;

        // 공통
        WifiConfiguration wfc = new WifiConfiguration();
        wfc.SSID    = String.format("\"%s\"", mSSID);
        wfc.status  = WifiConfiguration.Status.DISABLED;
        wfc.priority = 40;

        // 보안 방식에 따른 설정
        if(wifiConfig == WIFI_CONFIG_OPEN) {
            wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wfc.allowedAuthAlgorithms.clear();
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

        }else if(wifiConfig == WIFI_CONFIG_WEP) {
            // WEP
            wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wfc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wfc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);

            int lengrh = mPWD.length();
            if((lengrh == 10 || lengrh == 26 || lengrh == 58) && mPWD.matches("[0-9A-Fa-f]*]")) {
                wfc.wepKeys[0] = mPWD;
            }else {
                wfc.wepKeys[0] = String.format("\"%s\"", mPWD);
            }
            wfc.wepTxKeyIndex = 0;
        }else if(wifiConfig == WIFI_CONFIG_WPA2) {
            // WPA, WPA2
//            wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
//            wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
//            wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
//            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
//            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
//            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//            wfc.preSharedKey = String.format("\"%s\"", mPWD);

            wfc.status = WifiConfiguration.Status.ENABLED;

//            wfc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wfc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
//            wfc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wfc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wfc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wfc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wfc.preSharedKey = String.format("\"%s\"", mPWD);

        }
        // 보안 방식에 따른 설정
//        wfc.status = WifiConfiguration.Status.ENABLED;
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        int networkID = 0;
        boolean isSameSSID = false;
        int tempID = 0;
//        String tempSSID ;


        if(wifiManager == null) {
            return false;
        }

        List<WifiConfiguration> wifiConfigurationList;
        wifiConfigurationList = wifiManager.getConfiguredNetworks();
        for(WifiConfiguration w:wifiConfigurationList) {
            if(w.SSID.equals(String.format("\"%s\"", mSSID))) {
                isSameSSID = true;
                tempID = w.networkId;
//                tempSSID = w.SSID;
                break;
            }
        }

//        wifiManager.disableNetwork(wifiManager.getConnectionInfo().getNetworkId());

//        networkID = wifiManager.addNetwork(wfc);

        Log.d(TAG, "connectWifi: before networkID = " + networkID);

        if(tempID < 0) {
            if(isSameSSID) {
                networkID = tempID;
            }else {
                networkID = wifiManager.addNetwork(wfc);
            }
        }

        Log.d(TAG, "connectWifi: after networkID = " + networkID);

        boolean isDisconnected = wifiManager.disconnect();
        Log.d(TAG, "connectWifi: isDisconnected : " + isDisconnected);

        boolean bEnableNetwork = wifiManager.enableNetwork(networkID, true);

        if(bEnableNetwork) {
            Log.d(TAG, "Wifi Connected!!");
            bRet = true;
        }else {
            Log.d(TAG, "Wifi Disconnected!!");
            bRet = false;
        }
        boolean isReconnected = wifiManager.reconnect();
        // 여기서  true가 나와야 한다.
        Log.d(TAG, "connectWifi: isReconnected = " + isReconnected);

        return bRet;
    }
}
