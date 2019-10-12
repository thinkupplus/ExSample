package kr.co.thinkup.exsample.SocketSingletone.socket;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import kr.co.thinkup.exsample.SocketSingletone.network.Message;
import kr.co.thinkup.exsample.SocketSingletone.network.NetCommand;
import kr.co.thinkup.exsample.SocketSingletone.network.struct.PData;
import kr.co.thinkup.exsample.util.ByteUtils;

/**
 * 2019-06-02 create by CHOI
 */
public class SocketMultiRecvicer extends AsyncTask<Void, String, String> implements SocketConnection {
    private static final String TAG = "SocketReceiver";

    private static final String SERVER_DOWN = "SERVER_DOWN";

    private Socket socket;
    private BufferedInputStream bufferedInputStream;
    private AsyncMultiResponse           asyncResponse;
    private PData pData;
    private byte []                 pbyte;
//    private boolean                 isRunning;

    public SocketMultiRecvicer(Socket socket, AsyncMultiResponse asyncResponse) throws IOException {
        this.socket = socket;
        this.bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        this.asyncResponse = asyncResponse;
//        this.isRunning = true;
//        pbyte = new byte[1024];
    }

    public SocketMultiRecvicer(String ipAddress, int port, AsyncMultiResponse asyncResponse, int timeout) throws IOException {
        SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
        this.socket = new Socket();
        this.socket.connect(socketAddress, timeout);
        bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        this.asyncResponse = asyncResponse;
    }

    public void changeAsyncResponse(AsyncMultiResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

//    public void setRunning(boolean isRunning) {
//        this.isRunning = isRunning;
//    }

    @Override
    protected String doInBackground(Void... voids) {
        String message = "";
        byte[] buffer = new byte[1024];
        int bytes;
        while (socket != null && bufferedInputStream != null) {
            try {
                if(bufferedInputStream.available() > 0 ) {
                    bytes = bufferedInputStream.read(buffer, 0, 1024);

                    pbyte = new byte[bytes];

                    byte[] bHeader_size = new byte[NetCommand.HEADER_SIZE];
                    byte[] bHeader_Message = new byte[NetCommand.MESSAGE_SIZE];

                    System.arraycopy(buffer, NetCommand.HEADER_POS, bHeader_size, 0, NetCommand.HEADER_SIZE);
                    System.arraycopy(buffer, NetCommand.MESSAGE_POS, bHeader_Message, 0, NetCommand.MESSAGE_SIZE);

                    int nHeaderSize = ByteUtils.byteArrayToInt(bHeader_size, NetCommand.UINT32_T);
                    int nHeaderMessage = ByteUtils.byteArrayToInt(bHeader_Message, NetCommand.UINT16_T);


                    String szMessage = Message.getStringMessage(nHeaderMessage);
                    if(bytes != nHeaderSize) {
                        Log.d(TAG, "doInBackground: " + szMessage + "수신 데이터 사이즈가 다릅니다.");
                        continue;
                    }

                    System.arraycopy(buffer, 0, pbyte, 0, bytes);

                    message = "OK";
                    publishProgress(message);

                }
            }catch (EOFException e) {
                message = SERVER_DOWN;
                publishProgress(message);
                disconnect();
            }catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
                disconnect();
            }
        }
        return message;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        int error = 0;
        if(values[0].equals(SERVER_DOWN)) {
            onPostExecute(SERVER_DOWN);
        }else {
            String ip = socket.getInetAddress().toString();
            ip = ip.replace("/", "");
            asyncResponse.setMessageReceived(ip, pbyte);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        // updates UI after server down
        asyncResponse.alertServerDown(s);
    }

    @Override
    public void disconnect() {
        try {
            if(socket != null) {
                socket.close();
                socket = null;
            }
            if(bufferedInputStream != null) {
                bufferedInputStream.close();
                bufferedInputStream = null;
            }
        }catch (IOException e) {
            Log.e(TAG, "disconnect: " , e);
        }
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
    }
}
