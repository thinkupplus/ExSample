package kr.co.thinkup.exsample.SocketSingletone.network;

/**
 * 18/04/2019 by yh.Choi
 */
public class Message {

    public static final int PK_MSG_NONE 								= 0x0000;

    public static final int PK_MSG_GET_DEVICE_ID						= 0x0001;
    public static final int PK_MSG_SET_DEVICE_ID						= 0x0002;
    public static final int PK_MSG_GET_FIRMWARE_VERSION					= 0x0003;

    public static final	int PK_MSG_GET_SSID								= 0x0010;
    public static final int PK_MSG_GET_PASSWORD							= 0x0011;
    public static final int PK_MSG_GET_SERVER_PORT						= 0x0012;

    /*
    * AP : 서버가 AP 모드 일경우 사용
    * AP가 없는 경우는 STA 모드에서 사용하면 된다.
    * */

    public static final int PK_MSG_GET_AP_SSID							= 0x0013;
    public static final int PK_MSG_GET_AP_PASSWORD						= 0x0014;
    public static final int PK_MSG_GET_AP_SERVER_PORT					= 0x0015;
    public static final int PK_MSG_GET_AP_STA_MODE						= 0x0016;

    public static final int PK_MSG_SET_SSID								= 0x0020;
    public static final int PK_MSG_SET_PASSWORD							= 0x0021;
    public static final int PK_MSG_SET_SERVER_PORT						= 0x0022;

    // AP MODE
    public static final int PK_MSG_SET_AP_SSID							= 0x0024;
    public static final int PK_MSG_SET_AP_PASSWORD						= 0x0025;
    public static final int PK_MSG_SET_AP_SERVER_PORT					= 0x0026;
    public static final int PK_MSG_SET_AP_STA_MODE						= 0x0027;

    public static final int PK_MSG_SET_WIFI_RESET						= 0x0030;

    public static final int PK_MSG_SET_DIMMER_VALUE						= 0x0040;
    public static final int PK_MSG_SET_POWER_RELAY_VALUE				= 0x0041;

    public static final int PK_MSG_GET_DEVICE_INFO						= 0x0042;
    public static final int PK_MSG_GET_SENSOR_INFO						= 0x0043;

    public static final int PK_MSG_GET_WIFI_CONFIG						= 0x0044;
    public static final int PK_MSG_SET_WIFI_CONFIG						= 0x0045;

    public static final int PK_MSG_GET_LIST_AP                          = 0x0046;
    public static final int PK_MSG_GET_LIST_JOIN_DEVICE                 = 0x0047;

    public static String getStringMessage(int message) {
        String ret = "";
        switch (message) {
            case PK_MSG_NONE :
                ret = "PK_MSG_NONE";
                break;
            case PK_MSG_GET_DEVICE_ID :
                ret = "PK_MSG_GET_DEVICE_ID";
                break;
            case PK_MSG_SET_DEVICE_ID:
                ret = "PK_MSG_SET_DEVICE_ID";
                break;
            case PK_MSG_GET_FIRMWARE_VERSION:
                ret = "PK_MSG_GET_FIRMWARE_VERSION";
                break;
            case PK_MSG_GET_SSID:
                ret = "PK_MSG_GET_SSID";
                break;
            case PK_MSG_GET_PASSWORD:
                ret = "PK_MSG_GET_PASSWORD";
                break;
            case PK_MSG_GET_SERVER_PORT:
                ret = "PK_MSG_GET_SERVER_PORT";
                break;
            case PK_MSG_GET_AP_SSID:
                ret = "PK_MSG_GET_AP_SSID";
                break;
            case PK_MSG_GET_AP_PASSWORD:
                ret = "PK_MSG_GET_AP_PASSWORD";
                break;
            case PK_MSG_GET_AP_SERVER_PORT:
                ret = "PK_MSG_GET_AP_SERVER_PORT";
                break;
            case PK_MSG_GET_AP_STA_MODE:
                ret = "PK_MSG_GET_AP_STA_MODE";
                break;
            case PK_MSG_SET_SSID:
                ret = "PK_MSG_SET_SSID";
                break;
            case PK_MSG_SET_PASSWORD:
                ret = "PK_MSG_SET_PASSWORD";
                break;
            case PK_MSG_SET_SERVER_PORT:
                ret = "PK_MSG_SET_SERVER_PORT";
                break;
            case PK_MSG_SET_AP_SSID:
                ret = "PK_MSG_SET_AP_SSID";
                break;
            case PK_MSG_SET_AP_PASSWORD:
                ret = "PK_MSG_SET_AP_PASSWORD";
                break;
            case PK_MSG_SET_AP_SERVER_PORT:
                ret = "PK_MSG_SET_AP_SERVER_PORT";
                break;
            case PK_MSG_SET_AP_STA_MODE:
                ret = "PK_MSG_SET_AP_STA_MODE";
                break;
            case PK_MSG_SET_WIFI_RESET:
                ret = "PK_MSG_SET_WIFI_RESET";
                break;
            case PK_MSG_SET_DIMMER_VALUE:
                ret = "PK_MSG_SET_DIMMER_VALUE";
                break;
            case PK_MSG_SET_POWER_RELAY_VALUE:
                ret = "PK_MSG_SET_POWER_RELAY_VALUE";
                break;
            case PK_MSG_GET_DEVICE_INFO:
                ret = "PK_MSG_GET_DEVICE_INFO";
                break;
            case PK_MSG_GET_SENSOR_INFO:
                ret = "PK_MSG_GET_SENSOR_INFO";
                break;
            case PK_MSG_GET_WIFI_CONFIG:
                ret = "PK_MSG_GET_WIFI_CONFIG";
                break;
            case PK_MSG_SET_WIFI_CONFIG:
                ret = "PK_MSG_SET_WIFI_CONFIG";
                break;
            case PK_MSG_GET_LIST_AP :
                ret = "PK_MSG_GET_LIST_AP";
                break;
            case PK_MSG_GET_LIST_JOIN_DEVICE :
                ret = "PK_MSG_GET_LIST_JOIN_DEVICE";
                break;
        }

        return ret;
    }

}
