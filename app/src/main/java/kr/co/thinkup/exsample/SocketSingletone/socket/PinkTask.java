package kr.co.thinkup.exsample.SocketSingletone.socket;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 2019-05-24 create by CHOI
 *
 * mTask = new PingTask();
 *         // Ping the host "android.com"
 *         mTask.execute("android.com");
 *
 * http://gonacon.blogspot.com/2016/05/android-ping-asynctask.html
 */
public class PinkTask extends AsyncTask<String, Void, Void> {
    private static final String TAG = "PinkTask";

    PipedInputStream        pipedInputStream;
    PipedOutputStream       pipedOutputStream;
    LineNumberReader        lineNumberReader;

    Process                 process;
    String                  geteway;


    public PinkTask(String gateway) {
        this.geteway = gateway;
    }

    @Override
    protected void onPreExecute() {
        pipedOutputStream = new PipedOutputStream();
        try {
            pipedInputStream = new PipedInputStream(pipedOutputStream);
            lineNumberReader = new LineNumberReader(new InputStreamReader(pipedInputStream));
        }catch (IOException e) {
            Log.e(TAG, "onPreExecute: ", e);
            cancel(true);
        }
    }

    public void stop() {
        if(process != null) {
            process.destroy();
        }
        cancel(true);
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            process = new ProcessBuilder()
                    .command("/system/bin/ping", strings[0])
                    .redirectErrorStream(true)
                    .start();

            try {
                InputStream in = process.getInputStream();
                OutputStream out = process.getOutputStream();
                byte[] buffer = new byte[1024];
                int count;

                // in -> buffer -> mPOut -> mReader -> 1 line of ping information to parse
                while ((count = in.read(buffer)) != -1) {
                    pipedOutputStream.write(buffer, 0, count);
                    publishProgress();
                }
                out.close();
                in.close();
                pipedOutputStream.close();
                pipedInputStream.close();
            } finally {
                process.destroy();
                process = null;
            }
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: ", e);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        try {
            // Is a line ready to read from the "ping" command?
            while (lineNumberReader.ready()) {
                // This just displays the output, you should typically parse it I guess.
//                mText.setText(lineNumberReader.readLine());
                String tmp = lineNumberReader.readLine();
                Log.d(TAG, "onProgressUpdate: " + tmp);
            }
        } catch (IOException t) {
            Log.e(TAG, "onProgressUpdate: ", t);
        }
    }
}
