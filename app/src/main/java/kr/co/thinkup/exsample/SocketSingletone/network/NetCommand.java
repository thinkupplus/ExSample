package kr.co.thinkup.exsample.SocketSingletone.network;

/**
 * 18/04/2019 by yh.Choi
 */
public class NetCommand {

    public static final int ON                                          = 1;
    public static final int OFF                                         = 0;

    public static final int UINT32_T                                    = 4;
    public static final int UINT16_T                                    = 2;
    public static final int UINT8_T                                     = 1;

    public static final int HEADER_POS                                  = 0;
    public static final int HEADER_SIZE                                 = UINT32_T;

    public static final int MESSAGE_POS                                 = 4;
    public static final int MESSAGE_SIZE                                = UINT16_T;

    public static final int MAX_NETWORK_RECV_BUFFER_SIZE                = 5120;
    public static final int MAX_PACKET_DATA_SIZE                        = 1024;

    public static final int DF_DIMMER_TIMER_MODE_NONE                   = 0;
    public static final int DF_DIMMER_TIMER_MODE_TURN_ON                = 1;
    public static final int DF_DIMMER_TIMER_MODE_TURN_OFF               = 2;

    public static final int MASTER                                      = 1;
    public static final int SLAVE                                       = 2;

    //** 여기는 나중에 지우도록 하자
    public static final int STA_MODE                                    = 1;
    public static final int AP_MODE                                     = 2;
    public static final int AP_STA_MODE                                 = 3;


    public static final int DEVICE_ROUTER                               = 0;
    public static final int DEVICE_THINK_F                              = 1;
    public static final int DEVICE_DEMO                                 = 3;

}
