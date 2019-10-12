package kr.co.thinkup.exsample.SocketSingletone.network.struct;


import kr.co.thinkup.exsample.SocketSingletone.network.Message;
import kr.co.thinkup.exsample.SocketSingletone.network.NetCommand;
import kr.co.thinkup.exsample.util.ByteUtils;

/**
 * 19/04/2019 by yh.Choi
 */
public class PDeviceInfo {

    private int             size;           // 4 byte
    private int             message;        // 2 byte

    private int  []         adcValue;       // uint : 2byte, array : 3 = 6 byte
    // 0: Water level, 1: 입력 전압, 2: 소모 전

    private int             voltageValue;   // 2 byte
    // 전압
    private int             currnetValue;   // 2 byte
    // 전류
    private int             waterLevel;     // 1 byte
    // Water level

    private PLED_Dimmer []  ledDevice;     // LED   // 7byte * 4 array = 28 byte

    private int []          acPower;    // 1: ON, 0: OFF    // 2 byte
    private int []          dcPower;    // 1: ON, 0: OFF    // 7 byte

    public PDeviceInfo() {

        this.size = 54;
        this.message = Message.PK_MSG_NONE;

        this.adcValue = new int[] {0, 0, 0};

        this.voltageValue = 0;
        this.currnetValue = 0;
        this.waterLevel = 0;

        ledDevice = new PLED_Dimmer[4];

        this.acPower = new int[]{
                NetCommand.OFF,
                NetCommand.OFF};
        this.dcPower = new int[]{
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF};
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int[] getAdcValue() {
        return adcValue;
    }

    public int getAdcValue(int idx) {
        return adcValue[idx];
    }

    public void setAdcValue(int idx, int value) {
        this.adcValue[idx] = value;
    }

    public int getWaterLevelIndex(int waterLevel) {
        int nLevel = 0;
        if (waterLevel < 782)           nLevel = 9;
        else if (waterLevel < 1043)     nLevel = 8;
        else if (waterLevel < 1216)     nLevel = 7;
        else if (waterLevel < 1415)     nLevel = 6;
        else if (waterLevel < 1614)     nLevel = 5;
        else if (waterLevel < 1862)     nLevel = 4;
        else if (waterLevel < 2160)     nLevel = 3;
        else if (waterLevel < 2830)     nLevel = 2;
        else if (waterLevel < 3525)     nLevel = 1;
        else                            nLevel = 0;

        return nLevel;
    }

//    public int getWaterLevelIndex(int waterLevel) {
//        int nLevel = 0;
//        if (waterLevel < 495)           nLevel = 10;
//        else if (waterLevel < 825)      nLevel = 9;
//        else if (waterLevel < 1158)     nLevel = 8;
//        else if (waterLevel < 1490)     nLevel = 7;
//        else if (waterLevel < 1823)     nLevel = 6;
//        else if (waterLevel < 2155)     nLevel = 5;
//        else if (waterLevel < 2488)     nLevel = 4;
//        else if (waterLevel < 2820)     nLevel = 3;
//        else if (waterLevel < 3153)     nLevel = 2;
//        else if (waterLevel < 3485)     nLevel = 1;
//
//        return nLevel;
//    }

    public int getVoltageValue() {
        return voltageValue;
    }

    public void setVoltageValue(int voltageValue) {
        this.voltageValue = voltageValue;
    }

    public int getCurrnetValue() {
        return currnetValue;
    }

    public void setCurrnetValue(int currnetValue) {
        this.currnetValue = currnetValue;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public PLED_Dimmer getLEDDevice(int idx) {
        return ledDevice[idx];
    }

    public void setLEDDevice(int idx, PLED_Dimmer device) {
        this.ledDevice[idx] = device;
    }

    public int[] getAcPower() {
        return acPower;
    }

    public int getAcPower(int idx) {
        return acPower[idx];
    }

    public void setAcPower(int idx, int acPower) {
        this.acPower[idx] = acPower;
    }

    public int[] getDcPower() {
        return dcPower;
    }

    public int getDcPower(int idx) {
        return dcPower[idx];
    }

    public void setDcPower(int idx, int dcPower) {
        this.dcPower[idx] = dcPower;
    }

    public String toString() {
        return ByteUtils.byteArrayToHex(toBytes());
    }

    public byte[] toBytes() {

        byte [] bSize = ByteUtils.intToByteArray(getSize(), NetCommand.UINT32_T);
        byte [] bMessage = ByteUtils.intToByteArray(getMessage(), NetCommand.UINT16_T);
        byte [] badcValue1 = ByteUtils.intToByteArray(getAdcValue(0), NetCommand.UINT16_T);
        byte [] badcValue2 = ByteUtils.intToByteArray(getAdcValue(1), NetCommand.UINT16_T);
        byte [] badcValue3 = ByteUtils.intToByteArray(getAdcValue(2), NetCommand.UINT16_T);

        byte [] bVoltage = ByteUtils.intToByteArray(getVoltageValue(), NetCommand.UINT16_T);
        byte [] bCurrentValue = ByteUtils.intToByteArray(getCurrnetValue(), NetCommand.UINT16_T);
        byte [] bWaterValue = new byte[1];
        bWaterValue[0] = (byte)getWaterLevel();

        byte [] dimmer = new byte[28];
        byte [] temp = new byte[7];
        for (int i = 0; i < ledDevice.length; i++) {
            temp = getLEDDevice(i).toBytes();
            System.arraycopy(temp, 0, dimmer, i*7, 7);
        }

        byte [] bACPower = ByteUtils.intArrayToByteArray(getAcPower(), 2);
        byte [] bDCPower = ByteUtils.intArrayToByteArray(getDcPower(), 7);

        return  ByteUtils.concat(bSize, bMessage, badcValue1, badcValue2, badcValue3, bVoltage, bCurrentValue, bWaterValue, dimmer, bACPower, bDCPower);

    }
}
