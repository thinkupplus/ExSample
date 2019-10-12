package kr.co.thinkup.exsample.SocketSingletone.network.struct;


import kr.co.thinkup.exsample.SocketSingletone.network.Message;
import kr.co.thinkup.exsample.SocketSingletone.network.NetCommand;
import kr.co.thinkup.exsample.util.ByteUtils;

/**
 * 19/04/2019 by yh.Choi
 */
public class PRelay {

    private int         size;           // 4byte
    private int         message;        // 2byte

    private int []     acPower;         // 2byte
    private int []     dcPower;         // 7byte

    public PRelay() {
        this.size = 15;
        this.message = Message.PK_MSG_NONE;
        this.acPower = new int[] {
                NetCommand.OFF,
                NetCommand.OFF
        };

        this.dcPower = new int[] {
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF,
                NetCommand.OFF
        };
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

    public int[] getAcPower() {
        return acPower;
    }

    public int[] getDcPower() {
        return dcPower;
    }

    public void setACPower(int deviceNO, int power) {
        this.acPower[deviceNO] = power;
    }

    public void setDCPower(int deviceNO, int power) {
        this.dcPower[deviceNO] = power;
    }


    public int getACPower(int deviceNO) {
        int ret = this.acPower[deviceNO];

        if(ret == 0) 	return NetCommand.OFF;
        else 			return NetCommand.ON;
    }

    public int getDCPower(int deviceNO) {
        int ret = this.dcPower[deviceNO];

        if(ret == 0) 	return NetCommand.OFF;
        else 			return NetCommand.ON;
    }

    public String toString() {
        return ByteUtils.byteArrayToHex(toBytes());
    }

    public byte[] toBytes() {

        byte [] bSize = ByteUtils.intToByteArray(getSize(), NetCommand.UINT32_T);
        byte [] bMessage = ByteUtils.intToByteArray(getMessage(), NetCommand.UINT16_T);
        byte [] bACPower = ByteUtils.intArrayToByteArray(getAcPower(), 2);
        byte [] bDCPower = ByteUtils.intArrayToByteArray(getDcPower(), 7);

        return ByteUtils.concat(bSize, bMessage, bACPower, bDCPower);
    }
}
