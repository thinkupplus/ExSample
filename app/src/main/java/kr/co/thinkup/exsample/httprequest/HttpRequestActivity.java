package kr.co.thinkup.exsample.httprequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.co.thinkup.exsample.R;
import kr.co.thinkup.exsample.util.WaitingDialog;

/**
 * 2019-09-04 by yh.Choi
 * mysql data load 방법 php를 이용해서 db 불러오기
 */
public class HttpRequestActivity extends AppCompatActivity implements TUHttpResponse {

    private static String TAG = "HttpRequestActivity";

    private TextView textView;

    private static int  ASYNC_TASK = 1000;

    //    List<Business>      items = new ArrayList<>();
    List<String> items = new ArrayList<>();
    ListView lv_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actity_httprequest);

        textView = findViewById(R.id.tv_httprequest);
        lv_listView = findViewById(R.id.lv_httprequest);

        TUHttpResponse activityResponse = this;

        TUHttpRequestTask cur = null;

        String uri = "http://112.218.116.51:7788/select_test.php";

        try {
            cur = new TUHttpRequestTask(uri, activityResponse);
            WaitingDialog.showWaitingDialog(this, "조회중 입니다.\n잠시만 기다려주세요", ASYNC_TASK);
        }catch (IOException e) {
            Log.e(TAG, "onCreate: ", e);

        }

    }


    @Override
    public void httpRequestResult(String result) {
        WaitingDialog.cancelWaitingDialog(ASYNC_TASK);
        Log.d(TAG, "httpRequestResult: " + result);
        try {
            JSONObject root = new JSONObject(result);
            JSONArray jsonArray = root.getJSONArray("result");

            for(int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Business item = new Business();
//                item.id = jsonObject.getInt("id");
//                item.name = jsonObject.getString("name");
//                item.office_no_1 = jsonObject.getString("office_tel1");
//                item.office_no_2 = jsonObject.getString("office_tel2");
//                item.office_no_3 = jsonObject.getString("office_tel3");
//                item.address = jsonObject.getString("address");
//                item.join_date = jsonObject.getString("join_date");
//                items.add(item);

                String name = jsonObject.getString("name");
                items.add(name);

            }
        }catch (JSONException e) {
            Log.e(TAG, "httpRequestResult: ", e);
        }

//        textView.setText(result);

        listview();
    }

    public void listview() {
        lv_listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
    }

    public class Business {
        public int id;
        public String name;
        public String office_no_1;
        public String office_no_2;
        public String office_no_3;
        public String address;
        public String join_date;

        public Business() {
            this.id = 0;
            this.name = "";
            this.office_no_1 = "";
            this.office_no_2 = "";
            this.office_no_3 = "";
            this.address = "";
            this.join_date = "";
        }
    }
}
