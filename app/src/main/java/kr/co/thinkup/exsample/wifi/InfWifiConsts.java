package kr.co.thinkup.exsample.wifi;

public interface InfWifiConsts {

    /**
     * Wifi Not Enable
     */
    final int STATUS_WIFI_NOT_ENABLE                = 0x01;

    /**
     * Wifi Enabled, Not Same SSID
     */
    final int STATUS_WIFI_NOT_SAME_SSID             = STATUS_WIFI_NOT_ENABLE << 1;

    /**
     * WIFI Enabled, Same SSID
     */
    final int STATUS_WIFI_CONNECTED_SSID     = STATUS_WIFI_NOT_SAME_SSID << 1;


    /**
     * Wifi 상태
     */
    final int WIFI_STATE_DISABLED            = 0x00;
    final int WIFI_STATE_DISABLING           = WIFI_STATE_DISABLED          + 0x01;
    final int WIFI_STATE_ENABLED             = WIFI_STATE_DISABLING         + 0x01;
    final int WIFI_STATE_ENABLING            = WIFI_STATE_ENABLED           + 0x01;
    final int WIFI_STATE_UNKNOWN             = WIFI_STATE_ENABLING          + 0x01;

    /**
     * 네트워크 상태
     */
    final int NETWORK_STATE_CONNECTED        = WIFI_STATE_UNKNOWN           + 0x01;
    final int NETWORK_STATE_CONNECTING       = NETWORK_STATE_CONNECTED      + 0x01;
    final int NETWORK_STATE_DISCONNECTED     = NETWORK_STATE_CONNECTING     + 0x01;
    final int NETWORK_STATE_DISCONNECTING    = NETWORK_STATE_DISCONNECTED   + 0x01;
    final int NETWORK_STATE_SUSPENDED        = NETWORK_STATE_DISCONNECTING  + 0x01;
    final int NETWORK_STATE_UNKNOWN          = NETWORK_STATE_SUSPENDED      + 0x01;

    /**
     * Wifi 연결 방식
     */
    final int WIFI_CONFIG_OPEN               = 0x01;
    final int WIFI_CONFIG_WEP                = 0X02;
    final int WIFI_CONFIG_WPA2               = 0X03;

    /**
     * Wifi RSSI Level
     */
    final int RSSI_MAX        = 0x04;
    final int RSSI_HIGH       = 0x03;
    final int RSSI_MIDDLE     = 0x02;
    final int RSSI_LOW        = 0x01;
    final int RSSI_MIN        = 0x00;

}
