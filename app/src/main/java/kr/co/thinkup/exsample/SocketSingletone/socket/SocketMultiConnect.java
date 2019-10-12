package kr.co.thinkup.exsample.SocketSingletone.socket;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 2019-06-02 create by CHOI
 */
public class SocketMultiConnect implements SocketConnection {

    private static final String TAG = "SocketMultiConnect";

    private AsyncMultiResponse      asyncResponse;
    private Semaphore               sendSemaphore;
    private SocketMultiConnectTask  socketMultiConnectTask;

    private boolean                 isSendToRecvOK;


    public SocketMultiConnect(String ipAddress, int port, AsyncMultiResponse asyncResponse) throws IOException{
        sendSemaphore = new Semaphore(0);

        socketMultiConnectTask = new SocketMultiConnectTask(ipAddress, port);
        socketMultiConnectTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // reference for UI updating from the caller class
        this.asyncResponse = asyncResponse;

        // Send를 보낸 이후에 recv가 정확히 들어왔는지를 확인하기 위한 방법으로 이를 통해 Socket이 연결이 되어 있는지 아닌지를 확인한다.
        // true : 끊어짐, false : 정상
        this.isSendToRecvOK = false;
    }

    public String getSocketIPAddress() {
        return socketMultiConnectTask.ipAddress;
    }

    public boolean isSendToRecvOK() {
        return isSendToRecvOK;
    }

    public void setSendToRecvOK(boolean sendToRecvOK) {
        isSendToRecvOK = sendToRecvOK;
    }

    @Override
    public boolean isConnected() {
        if(socketMultiConnectTask == null) return false;
        return socketMultiConnectTask.isConnected();
    }

    @Override
    public void disconnect() {
        if(socketMultiConnectTask != null) {
            socketMultiConnectTask.disconnect();
            socketMultiConnectTask = null;
        }
    }

    /**
     * Sends message to the server trough SocketConnect  and MessageSender
     * @param message
     */

    public void sendMessage(byte[] message) {
        if(socketMultiConnectTask!= null && isConnected())
            socketMultiConnectTask.sendMessage(message);
    }


    private class SocketMultiConnectTask extends AsyncTask<String, Void, String> implements SocketConnection {
        private static final String SUCCESS = "SUCCESS";
        private static final String FAIL = "FAIL";

        private Socket              iSocket;
        private SocketSender        socketSender;
        private SocketMultiRecvicer socketReceiver;
        private String              connectionResult;

        private String              ipAddress;
        private int                 port;
        private int                 timeout;

        public SocketMultiConnectTask(String ipAddress, int port) {
            this.ipAddress = ipAddress;
            this.port = port;
            this.timeout = 3000;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
                this.iSocket = new Socket();
                this.iSocket.connect(socketAddress, timeout);

                socketSender = new SocketSender(iSocket, sendSemaphore);
                socketReceiver = new SocketMultiRecvicer(iSocket, asyncResponse);

//                socketSender = new SocketSender(ipAddress, port, sendSemaphore, timeout);
//                socketReceiver = new SocketReceiver(ipAddress, port, asyncResponse, timeout);

                this.publishProgress();
                connectionResult = SUCCESS;
            }catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
                connectionResult = e.getMessage();
            }finally {
//                connectionSemaphore.availablePermits();
                Log.d(TAG, "doInBackground: finally ===============================================================================");
//                connectionSemaphore.release();
            }
            return "ERROR";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
            // Excutor is necessary to run more than on thread
            Executor executor = Executors.newFixedThreadPool(2);

//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                wifiReceiverTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//            }else {
//                // runs sender thread
//                socketSender.executeOnExecutor(executor);
//
//                // runs receiver thread
//                socketReceiver.executeOnExecutor(executor);
//            }

            // runs sender thread
            socketSender.executeOnExecutor(executor);

            // runs receiver thread
            socketReceiver.executeOnExecutor(executor);
        }

        @Override
        protected void onPostExecute(String s) {
//            String ip = "";
//            if(!s.equals("ERROR")) {
//                ip = iSocket.getInetAddress().toString();
//                ip = ip.replace("/", "");
//            }
            asyncResponse.showConnectionResult(ipAddress, connectionResult);
        }

        @Override
        public void disconnect() {
            if(socketSender != null) {
                socketSender.cancel(true);
                socketSender.disconnect();
                socketSender = null;
            }

            if(socketReceiver != null) {
//                socketMultiConnectTask.socketReceiver.setRunning(false);
                socketReceiver.cancel(true);
                socketReceiver.disconnect();
                socketReceiver = null;
            }
        }

        @Override
        public boolean isConnected() {
            if(socketSender != null && socketReceiver != null) {
                return socketSender.isConnected() && socketReceiver.isConnected();
            }
            return false;
        }

        void sendMessage(byte[] message) {
            socketSender.setMessage(message);
//            sendSemaphore.release();
        }
    }
}
