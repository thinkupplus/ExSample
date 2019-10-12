package kr.co.thinkup.exsample.SocketSingletone.network.struct;


import kr.co.thinkup.exsample.SocketSingletone.network.NetCommand;
import kr.co.thinkup.exsample.util.ByteUtils;

/**
 * 22/04/2019 by yh.Choi
 */
public class PSensorData {

    private int         deviceID;       // 1byte
    private int         deviceCode;     // 1byte
//    private int []      deviceData;     // 1byte * 6 array = 6byte

    private int         deviceTemp;
    private int         deviceHumi;
    private int         deviceGas;

    public PSensorData() {
        this.deviceID = 0;
        this.deviceCode = 0;

//        this.deviceData = new int[] {
//                0, 0, 0, 0, 0, 0
//        };
        deviceTemp = 0;
        deviceHumi = 0;
        deviceGas = 0;
    }



    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public int getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(int deviceCode) {
        this.deviceCode = deviceCode;
    }

//    public int[] getDeviceData() {
//        return deviceData;
//    }
//
//    public void setDeviceData(int[] deviceData) {
//        this.deviceData = deviceData;
//    }
//
//    public int getDeviceData(int idx) {
//        return this.deviceData[idx];
//    }
//
//    public void setDeviceData(int idx, int val) {
//        this.deviceData[idx] = val;
//    }


    public int getDeviceTemp() {
        return deviceTemp;
    }

    public void setDeviceTemp(int deviceTemp) {
        this.deviceTemp = deviceTemp;
    }

    public int getDeviceHumi() {
        return deviceHumi;
    }

    public void setDeviceHumi(int deviceHumi) {
        this.deviceHumi = deviceHumi;
    }

    public int getDeviceGas() {
        return deviceGas;
    }

    public void setDeviceGas(int deviceGas) {
        this.deviceGas = deviceGas;
    }

    public String toString() {
        return ByteUtils.byteArrayToHex(toBytes());
    }

    public byte[] toBytes() {

        byte [] bDeviceID = new byte[1];
        bDeviceID[0] = (byte) getDeviceID();

        byte [] bDeviceCode = new byte[1];
        bDeviceCode[0] = (byte) getDeviceCode();

        byte [] bDeviceTemp = new byte[2];
        byte [] bDeviceHumi = new byte[2];
        byte [] bDeviceGas = new byte[2];

//        byte [] bDeviceData = ByteUtils.intArrayToByteArray(getDeviceData(), 1);

        bDeviceTemp = ByteUtils.intToByteArray(getDeviceTemp(), NetCommand.UINT16_T);
        bDeviceHumi = ByteUtils.intToByteArray(getDeviceHumi(), NetCommand.UINT16_T);
        bDeviceGas = ByteUtils.intToByteArray(getDeviceGas(), NetCommand.UINT16_T);

//        return ByteUtils.concat(bDeviceID, bDeviceCode, bDeviceData);
        return ByteUtils.concat(bDeviceID, bDeviceCode, bDeviceTemp, bDeviceHumi, bDeviceGas);
    }
}
