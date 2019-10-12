package kr.co.thinkup.exsample.SocketSingletone.socket;

/**
 * 2019-05-22 create by CHOI
 */
public interface AsyncMultiResponse {

    void setMessageReceived(String ip, byte[] pbyte);

    /**
     * Updates UI with received message from server
     * @param pData
     */
//    void setMessageReceived(PData pData);

    /**
     * Updates UI with received message from server
     * @param message
     */
//    void setMessageReceived(String message);

    /**
     * Shows a message with the result of a connection attempting
     * @param result
     */
    void showConnectionResult(String ip, String result);

    /**
     * Alerts thw user with a message when main extrnal server is closed,
     * which finishes all clients connections.
     * @param message
     */
    void alertServerDown(String message);
}
