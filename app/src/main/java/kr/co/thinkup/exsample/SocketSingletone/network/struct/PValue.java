package kr.co.thinkup.exsample.SocketSingletone.network.struct;


import kr.co.thinkup.exsample.SocketSingletone.network.Message;
import kr.co.thinkup.exsample.SocketSingletone.network.NetCommand;
import kr.co.thinkup.exsample.util.ByteUtils;

/**
 * 18/04/2019 by yh.Choi
 */
public class PValue {

    private static final String TAG = "PValue";

    private int         size;           // 4 byte
    private int         message;        // 2 byte
    private int         value;          // 4 byte

    public PValue() {
        this.size = 10;
        this.message = Message.PK_MSG_NONE;
        this.value = 0;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString() {
        return ByteUtils.byteArrayToHex(toBytes());
    }

    public byte[] toBytes() {

        byte[] bSize = ByteUtils.intToByteArray(getSize(), NetCommand.UINT32_T);
        byte[] bMessage = ByteUtils.intToByteArray(getMessage(), NetCommand.UINT16_T);
        byte[] bValue = ByteUtils.intToByteArray(getValue(), NetCommand.UINT32_T);

        return ByteUtils.concat(bSize, bMessage, bValue);
    }
}
