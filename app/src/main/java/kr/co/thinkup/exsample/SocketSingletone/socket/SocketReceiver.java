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
import kr.co.thinkup.exsample.util.ByteUtils;

/**
 * 2019-05-22 create by CHOI
 * Receives message from the server and update UI while connection is on.
 */
public class SocketReceiver extends AsyncTask<Void, String, String> implements SocketConnection {
    private static final String TAG = "SocketReceiver";

    private static final String SERVER_DOWN = "SERVER_DOWN";

    private Socket                  socket;
    private BufferedInputStream     bufferedInputStream;
    private AsyncResponse           asyncResponse;
    private byte []                 pbyte;

    public SocketReceiver(Socket socket, AsyncResponse asyncResponse) throws IOException {
        this.socket = socket;
        this.bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        this.asyncResponse = asyncResponse;
//        pbyte = new byte[1024];
    }

    public SocketReceiver(String ipAddress, int port, AsyncResponse asyncResponse, int timeout) throws IOException {
        SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
        this.socket = new Socket();
        this.socket.connect(socketAddress, timeout);
        bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        this.asyncResponse = asyncResponse;
    }

    public void changeAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String message = "";
        byte[] buffer = new byte[1024];
        int bytes;
        while (socket != null && bufferedInputStream != null && !isCancelled()) {
            try {
                if(!isCancelled() &&  bufferedInputStream.available() > 0) {
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
                message = SERVER_DOWN;
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
            asyncResponse.setMessageReceived(pbyte);
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
