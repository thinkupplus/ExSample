package kr.co.thinkup.exsample.SocketSingletone.network.struct;


import kr.co.thinkup.exsample.SocketSingletone.network.Message;
import kr.co.thinkup.exsample.SocketSingletone.network.NetCommand;
import kr.co.thinkup.exsample.util.ByteUtils;

/**
 * 19/04/2019 by yh.Choi
 */
public class PLED {

    private int         size;       // 4byte
    private int         message;    // 2byte

    PLED_Dimmer []      device;     // 7byte * 4array = 28byte


    public PLED() {
        this.size = 34;
        this.message = Message.PK_MSG_NONE;
        this.device = new PLED_Dimmer[4];
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

    public PLED_Dimmer getDevice(int idx) {
        return device[idx];
    }

    public void setDevice(int idx, PLED_Dimmer device) {
        this.device[idx] = device;
    }

    public String toString() {
        return ByteUtils.byteArrayToHex(toBytes());
    }

    public byte[] toBytes() {

        byte [] bSize = ByteUtils.intToByteArray(getSize(), NetCommand.UINT32_T);
        byte [] bMessage = ByteUtils.intToByteArray(getMessage(), NetCommand.UINT16_T);

        byte [] dimmer = new byte[28];
        byte [] temp = new byte[7];
        for (int i = 0; i < device.length; i++) {
            temp = getDevice(i).toBytes();
            System.arraycopy(temp, 0, dimmer, i*7, 7);
        }

        return ByteUtils.concat(bSize, bMessage, dimmer);
    }


}
