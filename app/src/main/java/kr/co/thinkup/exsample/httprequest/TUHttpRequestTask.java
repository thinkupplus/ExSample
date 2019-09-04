package kr.co.thinkup.exsample.httprequest;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 2019-09-04 by yh.Choi
 */
public class TUHttpRequestTask {

    private TUHttpResponse asyncResponse;

    private static TUHttpRequestTask singleton;
    private HttpRequestTask httpRequestTask;




    public TUHttpRequestTask(String uri, TUHttpResponse tuHttpResponse) throws IOException {

        httpRequestTask = new HttpRequestTask(uri);
        httpRequestTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        this.asyncResponse = tuHttpResponse;

    }



    public static TUHttpRequestTask request(String uri, TUHttpResponse asyncResponse) throws IOException {
        if(singleton == null) {
            singleton = new TUHttpRequestTask(uri, asyncResponse);
        }
        return singleton;
    }



    private class HttpRequestTask extends AsyncTask<String, String, String> {

        private String          uri;
        private String          result;

        public HttpRequestTask(String uri) {
            this.uri = uri;
        }

        @Override
        protected String doInBackground(String... strings) {
            String message = "";
            StringBuilder jsonHtml = new StringBuilder();
            try{
                // 연결 url 설정
                URL url = new URL(uri);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                // 연결되었으면.
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;){
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if(line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
                result = jsonHtml.toString();
            } catch(Exception ex){
                message = ex.getMessage();
                ex.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            asyncResponse.httpRequestResult(result);

        }
    }

}
