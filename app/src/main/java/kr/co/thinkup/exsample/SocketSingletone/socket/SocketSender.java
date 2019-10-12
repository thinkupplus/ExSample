package kr.co.thinkup.exsample.SocketSingletone.socket;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Semaphore;

/**
 * 2019-05-22 create by CHOI
 */
public class SocketSender extends AsyncTask<Void, Void, Void> implements SocketConnection {
    private static final String TAG = "SocketSender";

    private Socket                  socket;
    private BufferedOutputStream    bufferedOutputStream;
    private Semaphore               semaphore;
    private byte []                 message;

    public SocketSender(Socket socket, Semaphore semaphore) throws IOException {
        this.socket = socket;
        this.bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        this.semaphore = semaphore;
        message = new byte[1024];
    }

    public SocketSender(String ipAddress, int port, Semaphore semaphore, int timeout) throws IOException {
        SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
        this.socket = new Socket(ipAddress, port);
        this.socket.connect(socketAddress, timeout);
        this.bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        this.semaphore = semaphore;
        message = new byte[1024];
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while(!isCancelled()) {
            try {
                int n = semaphore.availablePermits();
                if (isCancelled()) {
                    break;
                }
                semaphore.acquire();
                if(bufferedOutputStream != null && this.message.length > 0) {
                    bufferedOutputStream.write(message);
                    bufferedOutputStream.flush();
                }
            }catch (IOException | InterruptedException e) {
                disconnect();
                Log.e(TAG, "doInBackground: ", e);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        message = null;
    }

    @Override
    public void disconnect() {
        try{
            if(socket != null) {
                socket.close();
                socket = null;
            }

            if(bufferedOutputStream != null) {
                bufferedOutputStream.close();
                bufferedOutputStream = null;
            }
        }catch (IOException e) {
            Log.e(TAG, "disconnect: ", e);
        }
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
    }


    void setMessage(byte[] message) {
        this.message= message;
        semaphore.release();
    }
}
