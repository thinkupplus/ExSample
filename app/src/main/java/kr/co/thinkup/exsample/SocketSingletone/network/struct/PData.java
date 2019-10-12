package kr.co.thinkup.exsample.SocketSingletone.network.struct;

import kr.co.thinkup.exsample.SocketSingletone.network.Message;
import kr.co.thinkup.exsample.SocketSingletone.network.NetCommand;
import kr.co.thinkup.exsample.util.ByteUtils;


/**
 * 18/04/2019 by yh.Choi
 */
public class PData {

    private int         size;       // 4 byte
    private int         message;    // 2 byte
    private int         dataSize;   // 2 byte
    private byte []     data;       // 1024 byte


    public PData() {
        this.size = 1032;
        this.message = Message.PK_MSG_NONE;
        this.dataSize = 0;
        this.data = new byte[NetCommand.MAX_PACKET_DATA_SIZE];
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

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String toString() {
        return ByteUtils.byteArrayToHex(toBytes());
    }

    public byte[] toBytes() {

        byte [] bSize = ByteUtils.intToByteArray(getSize(), NetCommand.UINT32_T);
        byte [] bMessage = ByteUtils.intToByteArray(getMessage(), NetCommand.UINT16_T);
        byte [] bDataSize = ByteUtils.intToByteArray(getDataSize(), NetCommand.UINT16_T);

        return ByteUtils.concat(bSize, bMessage, bDataSize, getData());
    }
}
