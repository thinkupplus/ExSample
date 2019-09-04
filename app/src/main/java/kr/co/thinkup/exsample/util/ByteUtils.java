package kr.co.thinkup.exsample.util;

import java.nio.ByteBuffer;

/**
 * 18/04/2019 by yh.Choi
 */
public class ByteUtils {

    /*bytearray to 이진수 변환*/
    public static String byteArrayToBinaryString(byte[] b){
        StringBuilder sb=new StringBuilder();
        for(int i=0; i<b.length; ++i){
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }


    /*byte to 이진수 변환*/
    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    /*byte array to String*/
    public static String byteArrayToString(byte[] ba) {
        return new String(ba);
    }


    /* long to 8bytearray 변환*/
    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }



    /* 8bytearray to long 변환*/
    public static long bytesToLong(byte[] b) {
        long result = 0;
        for (int i = 7; i >= 0; i--) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }
        return result;
    }

    // 변환이 잘못되면 여기를 참조하여 수정하자.
    // https://javadeveloperzone.com/java-basic/java-convert-int-to-byte-array/
    /* byte array to int array value*/
    public static int[] byteArrayTointArray(byte[] values) {
        int [] intArray = new int[values.length];
        for(int i=0; i<values.length; i++) {
            intArray[i] = (int)values[i];
        }
        return intArray;
    }

    /*int array to byte array*/
    public static byte[] intArrayToByteArray(int[] values, int lengthDiv) {
        byte[] byteArray = new byte[values.length];

        for(int i=0; i< values.length; i++) {
            byteArray[i] = (byte)values[i];
        }
        return byteArray;
    }


    /*int to 4or2 bytearray 변환*/
    public static byte[] intToByteArray(int value, int lengthDiv) {
        byte[] byteArray = new byte[lengthDiv];
        if (lengthDiv == 2){
            byteArray[0] = (byte) value;
            byteArray[1] = (byte) (value >> 8);
        }else if (lengthDiv == 4){
            byteArray[0] = (byte)((value) & 0xff);
            byteArray[1] = (byte)((value >> 8) & 0xff);
            byteArray[2] = (byte)((value >> 16) & 0xff);
            byteArray[3] = (byte)((value >> 24) & 0xff);
        }
        return byteArray;
    }

    /* 4or2 bytearray  to int 변환*/
    public static int byteArrayToInt(byte[] b, int lengthDiv){
        int byteInt = 0;
        if (lengthDiv==2){
            byteInt = ((b[1] & 0xFF) << 8) | (b[0] & 0xFF);
        }else if (lengthDiv==4){
            byteInt = (b[0] & 0xFF) |
                    (b[1] & 0xFF) << 8 |
                    (b[2] & 0xFF) << 16 |
                    (b[3] & 0xFF) << 24;
        }
        return byteInt;
    }

    public static double byteArrayToDouble(byte[] b) {
        return ByteBuffer.wrap(b).getDouble();
    }

    /* byte array to float */
    public static float byteArrayToFloat(byte[]b, int lengthDiv) {
        int value = byteArrayToInt(b, lengthDiv);
        return Float.intBitsToFloat(value);
    }

    /* shot to byte 변환 */
    public static byte[] shortToBytes(short Value, int Order){
        byte[] temp;
        temp = new byte[]{ (byte)((Value & 0xFF00) >> 8), (byte)(Value & 0x00FF) };
        temp = ChangeByteOrder(temp,Order);
        return temp;
    }


    /* 상위, 하위 변환 (내부적으로 사용하는 함수)*/
    public static byte[] ChangeByteOrder(byte[] value,int Order){
        int idx = value.length;
        byte[] Temp = new byte[idx];
        //BIG_EDIAN
        if(Order == 1){
            Temp = value;
        }else if(Order == 0){
            for(int i=0;i<idx;i++) {
                Temp[i] = value[idx-(i+1)];
            }
        }
        return Temp;
    }


    /* 2진수String to byte 변환*/
    public static byte binaryStringToByte(String s){
        byte ret=0, total=0;
        for(int i=0; i<8; ++i){
            ret = (s.charAt(7-i)=='1') ? (byte)(1 << i) : 0;
            total = (byte) (ret|total);
        }
        return total;
    }

    /*2진수String to bytearray  변환*/
    public static byte[] binaryStringToByteArray(String s){
        int count=s.length()/8;
        byte[] b=new byte[count];
        for(int i=1; i<count; ++i){
            String t=s.substring((i-1)*8, i*8);
            b[i-1]=binaryStringToByte(t);
        }
        return b;
    }

    public static String byteArrayToIntString(byte buf[]) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            sb.append((int) buf[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    /*bytearray to 16진수String 변환*/
    public static String bytearraytoHexString(byte buf[]){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            sb.append(Integer.toHexString(0x0100 + (buf[i] & 0x00FF)).substring(1));
        }
        return sb.toString();
    }

    /*16진수String to bytearray 변환*/
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        hex = hex.replace("0x", "");

        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }

    // assertArrayEquals(new byte[] { 63, -116, -52, -51}, floatToByteArray(1.1f));
    public static byte[] floatToByteArray(float value) {
        int intBits =  Float.floatToIntBits(value);
        return new byte[] {
                (byte) (intBits >> 24), (byte) (intBits >> 16), (byte) (intBits >> 8), (byte) (intBits) };
    }

    //assertEquals(1.1f, byteArrayToFloat(new byte[] { 63, -116, -52, -51}), 0);
    public static float byteArrayToFloat(byte[] bytes) {
        int intBits =
                bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
        return Float.intBitsToFloat(intBits);
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(final byte b: a)
            sb.append(String.format("0x%02X ", b&0xff));
        return sb.toString();
    }

    /*byte concat*/
    public static byte[] concat(byte[]...arrays) {
        // Determine the length of the result array
        int totalLength = 0;
        for (int i = 0; i < arrays.length; i++)
        {
            totalLength += arrays[i].length;
        }

        // create the result array
        byte[] result = new byte[totalLength];

        // copy the source arrays into the result array
        int currentIndex = 0;
        for (int i = 0; i < arrays.length; i++)
        {
            System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
            currentIndex += arrays[i].length;
        }

        return result;
    }

    public static Byte DEFAULT_BYTE = new Byte((byte) 0);

    /**
     * <p>문자열을 바이트로 변환한다.</p>
     *
     * <pre>
     * ByteUtils.toByte("1", *)    = 0x01
     * ByteUtils.toByte("-1", *)   = 0xff
     * ByteUtils.toByte("a", 0x00) = 0x00
     * </pre>
     *
     * @param value 10진수 문자열 값
     * @param defaultValue
     * @return
     */
    public static byte toByte(String value, byte defaultValue) {
        try {
            return Byte.parseByte(value);
        } catch(Exception e) {
            return defaultValue;
        }
    }

    /**
     * <p>문자열을 바이트로 변환한다.</p>
     *
     * <pre>
     * ByteUtils.toByteObject("1", *)    = 0x01
     * ByteUtils.toByteObject("-1", *)   = 0xff
     * ByteUtils.toByteObject("a", 0x00) = 0x00
     * </pre>
     *
     * @param value 10진수 문자열 값
     * @param defaultValue
     * @return
     */
    public static Byte toByteObject(String value, Byte defaultValue) {
        try {
            return new Byte(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * <p>singed byte를 unsinged byte로 변환한다.</p>
     * <p>Java에는 unsinged 타입이 없기때문에, int로 반환한다.(b & 0xff)</p>
     *
     * @param b singed byte
     * @return unsinged byte
     */
    public static int unsignedByte(byte b) {
        return  b & 0xFF;
    }

    /**
     * <p>입력한 바이트 배열(4바이트)을 int 형으로 변환한다.</p>
     *
     * @param src
     * @param srcPos
     * @return
     */
    public static int toInt(byte[] src, int srcPos) {
        int dword = 0;
        for (int i = 0; i < 4; i++) {
            dword = (dword << 8) + (src[i + srcPos] & 0xFF);
        }
        return dword;
    }

    /**
     * <p>입력한 바이트 배열(4바이트)을 int 형으로 변환한다.</p>
     *
     * @param src
     * @return
     */
    public static int toInt(byte[] src) {
        return toInt(src, 0);
    }

    /**
     * <p>입력한 바이트 배열(8바이트)을 long 형으로 변환한다.</p>
     *
     * @param src
     * @param srcPos
     * @return
     */
    public static long toLong(byte[] src, int srcPos) {
        long qword = 0;
        for (int i = 0; i < 8; i++) {
            qword = (qword << 8) + (src[i + srcPos] & 0xFF);
        }
        return qword;
    }

    /**
     * <p>입력한 바이트 배열(8바이트)을 long 형으로 변환한다.</p>
     *
     * @param src
     * @return
     */
    public static long toLong(byte[] src) {
        return toLong(src, 0);
    }

    /**
     * <p>int 형의 값을 바이트 배열(4바이트)로 변환한다.</p>
     *
     * @param value
     * @param dest
     * @param destPos
     */
    public static void toBytes(int value, byte[] dest, int destPos) {
        for (int i = 0; i < 4; i++) {
            dest[i + destPos] = (byte)(value >> ((7 - i) * 8));
        }
    }

    /**
     * <p>int 형의 값을 바이트 배열(4바이트)로 변환한다.</p>
     *
     * @param value
     * @return
     */
    public static byte[] toBytes(int value) {
        byte[] dest = new byte[4];
        toBytes(value, dest, 0);
        return dest;
    }

    /**
     * <p>long 형의 값을 바이트 배열(8바이트)로 변환한다.</p>
     *
     * @param value
     * @param dest
     * @param destPos
     */
    public static void toBytes(long value, byte[] dest, int destPos) {
        for (int i = 0; i < 8; i++) {
            dest[i + destPos] = (byte)(value >> ((7 - i) * 8));
        }
    }

    /**
     * <p>long 형의 값을 바이트 배열(8바이트)로 변환한다.</p>
     *
     * @param value
     * @return
     */
    public static byte[] toBytes(long value) {
        byte[] dest = new byte[8];
        toBytes(value, dest, 0);
        return dest;
    }

    /**
     * <p>8, 10, 16진수 문자열을 바이트 배열로 변환한다.</p>
     * <p>8, 10진수인 경우는 문자열의 3자리가, 16진수인 경우는 2자리가, 하나의 byte로 바뀐다.</p>
     *
     * <pre>
     * ByteUtils.toBytes(null)     = null
     * ByteUtils.toBytes("0E1F4E", 16) = [0x0e, 0xf4, 0x4e]
     * ByteUtils.toBytes("48414e", 16) = [0x48, 0x41, 0x4e]
     * </pre>
     *
     * @param digits 문자열
     * @param radix 진수(8, 10, 16만 가능)
     * @return
     * @throws NumberFormatException
     */
    public static byte[] toBytes(String digits, int radix) throws IllegalArgumentException, NumberFormatException {
        if (digits == null) {
            return null;
        }
        if (radix != 16 && radix != 10 && radix != 8) {
            throw new IllegalArgumentException("For input radix: \"" + radix + "\"");
        }
        int divLen = (radix == 16) ? 2 : 3;
        int length = digits.length();
        if (length % divLen == 1) {
            throw new IllegalArgumentException("For input string: \"" + digits + "\"");
        }
        length = length / divLen;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int index = i * divLen;
            bytes[i] = (byte)(Short.parseShort(digits.substring(index, index+divLen), radix));
        }
        return bytes;
    }

    /**
     * <p>16진수 문자열을 바이트 배열로 변환한다.</p>
     * <p>문자열의 2자리가 하나의 byte로 바뀐다.</p>
     *
     * <pre>
     * ByteUtils.toBytesFromHexString(null)     = null
     * ByteUtils.toBytesFromHexString("0E1F4E") = [0x0e, 0xf4, 0x4e]
     * ByteUtils.toBytesFromHexString("48414e") = [0x48, 0x41, 0x4e]
     * </pre>
     *
     * @param digits 16진수 문자열
     * @return
     * @throws NumberFormatException
     */
    public static byte[] toBytesFromHexString(String digits) throws IllegalArgumentException, NumberFormatException {
        if (digits == null) {
            return null;
        }
        int length = digits.length();
        if (length % 2 == 1) {
            throw new IllegalArgumentException("For input string: \"" + digits + "\"");
        }
        length = length / 2;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int index = i * 2;
            bytes[i] = (byte)(Short.parseShort(digits.substring(index, index+2), 16));
        }
        return bytes;
    }

    /**
     * <p>unsigned byte(바이트)를 16진수 문자열로 바꾼다.</p>
     *
     * ByteUtils.toHexString((byte)1)   = "01"
     * ByteUtils.toHexString((byte)255) = "ff"
     *
     * @param b unsigned byte
     * @return
     */
    public static String toHexString(byte b) {
        StringBuffer result = new StringBuffer(3);
        result.append(Integer.toString((b & 0xF0) >> 4, 16));
        result.append(Integer.toString(b & 0x0F, 16));
        return result.toString();
    }

    /**
     * <p>unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.</p>
     *
     * <pre>
     * ByteUtils.toHexString(null)                   = null
     * ByteUtils.toHexString([(byte)1, (byte)255])   = "01ff"
     * </pre>
     *
     * @param bytes unsigned byte's array
     * @return
     */
    public static String toHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        StringBuffer result = new StringBuffer();
        for (byte b : bytes) {
            result.append(Integer.toString((b & 0xF0) >> 4, 16));
            result.append(Integer.toString(b & 0x0F, 16));
        }
        return result.toString();
    }

    /**
     * <p>unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.</p>
     *
     * <pre>
     * ByteUtils.toHexString(null, *, *)                   = null
     * ByteUtils.toHexString([(byte)1, (byte)255], 0, 2)   = "01ff"
     * ByteUtils.toHexString([(byte)1, (byte)255], 0, 1)   = "01"
     * ByteUtils.toHexString([(byte)1, (byte)255], 1, 2)   = "ff"
     * </pre>
     *
     * @param bytes unsigned byte's array
     * @return
     */
    public static String toHexString(byte[] bytes, int offset, int length) {
        if (bytes == null) {
            return null;
        }

        StringBuffer result = new StringBuffer();
        for (int i = offset; i < offset + length; i++) {
            result.append(Integer.toString((bytes[i] & 0xF0) >> 4, 16));
            result.append(Integer.toString(bytes[i] & 0x0F, 16));
        }
        return result.toString();
    }

    /**
     * <p>두 배열의 값이 동일한지 비교한다.</p>
     *
     * <pre>
     * ArrayUtils.equals(null, null)                        = true
     * ArrayUtils.equals(["one", "two"], ["one", "two"])    = true
     * ArrayUtils.equals(["one", "two"], ["three", "four"]) = false
     * </pre>
     *
     * @param array1
     * @param array2
     * @return 동일하면 <code>true</code>, 아니면 <code>false</code>
     */
    public static boolean equals(byte[] array1, byte[] array2) {
        if (array1 == array2) {
            return true;
        }

        if (array1 == null || array2 == null) {
            return false;
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
    }

    public static String getByteStr(byte [] data) {

        byte [] tmp = new byte[data.length];
        int idx = 0, count = 0;
        do {
            if(data[idx] != 0x00) {
                System.arraycopy(data, idx, tmp, idx, 2);
                count = idx;
            }
            idx++;
        }while (idx <= data.length - 1);

        // 0번부터 시작하기 때문에 개수는 1을 더한다.
        count += 1;
        byte [] dumy = new byte[count];
        System.arraycopy(tmp, 0, dumy, 0, count);

        return new String(dumy);
    }
}
