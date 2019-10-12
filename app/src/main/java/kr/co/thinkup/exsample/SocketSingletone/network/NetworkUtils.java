package kr.co.thinkup.exsample.SocketSingletone.network;

import kr.co.thinkup.exsample.SocketSingletone.network.struct.PData;
import kr.co.thinkup.exsample.SocketSingletone.network.struct.PValue;
import kr.co.thinkup.exsample.util.ByteUtils;


/**
 * 2019-05-22 create by CHOI
 */
public class NetworkUtils {

    private static String TAG = "NetworkUtils";

    public static int getHeader(byte[] pbyte) {
        byte[] bHeader_size = new byte[NetCommand.HEADER_SIZE];
        System.arraycopy(pbyte, NetCommand.HEADER_POS, bHeader_size, 0, NetCommand.HEADER_SIZE);
        return ByteUtils.byteArrayToInt(bHeader_size, NetCommand.UINT32_T);
    }

    public static int getMessage(byte[] pbyte) {
        byte[] bHeader_Message = new byte[NetCommand.MESSAGE_SIZE];
        System.arraycopy(pbyte, NetCommand.MESSAGE_POS, bHeader_Message, 0, NetCommand.MESSAGE_SIZE);
        return ByteUtils.byteArrayToInt(bHeader_Message, NetCommand.UINT16_T);
    }

    public static PValue getPValue(byte[] pbyte) {
        PValue pValue = new PValue();
        pValue.setSize(getHeader(pbyte));
        pValue.setMessage(getMessage(pbyte));

        int nDataSize = pValue.getSize() - 6;

        byte[] bData = new byte[nDataSize];
        System.arraycopy(pbyte, 6, bData, 0, nDataSize);

        pValue.setValue(ByteUtils.byteArrayToInt(bData, NetCommand.UINT32_T));

        return pValue;
    }

    public static PValue setPValue(int message) {
        PValue pValue = new PValue();
        pValue.setSize(10);
        pValue.setMessage(message);
        pValue.setValue(0);

        return pValue;
    }

    public static PData getPData(byte[] pbyte) {
        PData pData = new PData();
        pData.setSize(getHeader(pbyte));
//        pData.setSize(pbyte.length);
        pData.setMessage(getMessage(pbyte));

        byte[] bDataSize = new byte[NetCommand.UINT16_T];

        System.arraycopy(pbyte, 6, bDataSize, 0, NetCommand.UINT16_T);

        int nDataSize = ByteUtils.byteArrayToInt(bDataSize, NetCommand.UINT16_T);

        byte[] bData = new byte[nDataSize];

        System.arraycopy(pbyte, 8, bData, 0, nDataSize);
        pData.setDataSize(nDataSize);
        pData.setData(bData);

        return pData;
    }

    public static PData setPData(byte[] pbyte, int message) {
        PData pData = new PData();

        pData.setData(pbyte);
        pData.setDataSize(pbyte.length);
        pData.setMessage(message);
        pData.setSize(pbyte.length + 8);

        return pData;
    }

//    public static PLED getPLED(byte[] pbyte) {
//        PLED pled = new PLED();
//
//        pled.setSize(getHeader(pbyte));
//        pled.setMessage(getMessage(pbyte));
//
//        String msg1 = "";
//        // 0부터 인식을 하자
//        for(int i=0; i < 4; i++) {
//            PLED_Dimmer led = new PLED_Dimmer();
//
//            LedList item = realmService.getLedDataFromDB(i).first();
//
//            int nBufferPos = (7 * i);
//
//            byte devicePower = pbyte[nBufferPos];
//            byte [] deviceValue = new byte[2];
//
//            System.arraycopy(pbyte, 1 + nBufferPos, deviceValue, 0, 2);
//
//            byte bTimeMode = pbyte[3 + nBufferPos];
//            byte bTimeHour = pbyte[4 + nBufferPos];
//            byte bTimeMinute = pbyte[5 + nBufferPos];
//            byte bTimeSecond = pbyte[6 + nBufferPos];
//
//
//            led.setDevicePower(devicePower);                                                // 1 byte
//            led.setDeviceValue(ByteUtils.byteArrayToInt(deviceValue, NetCommand.UINT16_T)); // 2 byte
//            led.setTimeMode(bTimeMode);    // 1 byte
//            led.setTimeHour(bTimeHour);    // 1 byte
//            led.setTimeMinute(bTimeMinute);  // 1 byte
//            led.setTimeSecond(bTimeSecond);  // 1 byte
//            msg1 = String.format(Locale.getDefault(), "LED Number: [%d], POWER [%d], BRIGHTNESS [%d], T_MODE [%d], T_HOUR [%d], T_MINUTES [%d], T_SECONDS [%d]",
//                    i,
//                    led.getDevicePower(),
//                    led.getDeviceValue(),
//                    led.getTimeMode(),
//                    led.getTimeHour(),
//                    led.getTimeMinute(),
//                    led.getTimeSecond());
//
//            Log.d(TAG, "receivefromServe: LED STATUS = " + msg1);
//
//            int nSetupMode = (int) bTimeMode;
//
//            int nSetupHour = (int) bTimeHour;
//            int nSetupMinute = (int) bTimeMinute;
//            int nSetupSecond = (int) bTimeSecond;
//
//
//            if(nSetupMode == NetCommand.DF_DIMMER_TIMER_MODE_TURN_ON) {
//                long lAfterTime = ThinkUPTimeUtils.getSetupTimeMillisTolong(nSetupHour, nSetupMinute, nSetupSecond);
//                String szSetupTime = ThinkUPTimeUtils.getCurrentTimeAftertoString(lAfterTime);
//
//                realmService.updateLedRiseSetupTimeToDB(item, szSetupTime, LedType.ALARM_ING);
//            }
//
//        }
//    }
}
