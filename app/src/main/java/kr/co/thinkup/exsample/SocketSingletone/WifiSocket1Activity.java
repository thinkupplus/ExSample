package kr.co.thinkup.exsample.SocketSingletone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import kr.co.thinkup.exsample.R;
import kr.co.thinkup.exsample.SocketSingletone.network.Message;
import kr.co.thinkup.exsample.SocketSingletone.network.NetworkUtils;
import kr.co.thinkup.exsample.SocketSingletone.network.struct.PData;
import kr.co.thinkup.exsample.SocketSingletone.network.struct.PValue;
import kr.co.thinkup.exsample.SocketSingletone.socket.AsyncResponse;

/**
 * 2019-10-04 by yh.Choi
 */
public class WifiSocket1Activity extends WifiBaseActivity {

    private static final String TAG = "WifiSocket1Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wifisocket1);

        Button btn_wifi2 = findViewById(R.id.btn_wifi2);

        btn_wifi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApList();
            }
        });


    }

    @Override
    protected void connectionResult(String result) {
        Log.d(TAG, "connectionResult: ");
    }

    @Override
    protected void messageReceived(byte[] pbyte) {
        Log.d(TAG, "messageReceived: ");
        int message = NetworkUtils.getMessage(pbyte);

        if(message == Message.PK_MSG_GET_LIST_AP) {
            PData pData = NetworkUtils.getPData(pbyte);
            byte[] bAPList = pData.getData();
            String szAPList = new String(bAPList);
            Log.d(TAG, "setMessageReceived: " + szAPList);
            getToAPList(szAPList);
        }else if(message == Message.PK_MSG_SET_WIFI_CONFIG) {

            //// 1,ThinKF,12345678,1,2,SNDSYS,super1004~001,15830,192.168.0.65,84:f3:eb:a7:be:16
            PValue pValue = NetworkUtils.getPValue(pbyte);

            int nRet = pValue.getValue();
            String szResult = "";
            if(nRet == 1) {
                szResult = "성공";
            }else {
                szResult = "실패";
            }

            Log.d(TAG, "setMessageReceived: PK_MSG_SET_WIFI_CONFIG = " + szResult);
        }
    }

//    @Override
//    public void setMessageReceived(byte[] pbyte) {
//        Log.d(TAG, "setMessageReceived: ");
//        int message = NetworkUtils.getMessage(pbyte);
//
//        if(message == Message.PK_MSG_GET_LIST_AP) {
//            PData pData = NetworkUtils.getPData(pbyte);
//            byte[] bAPList = pData.getData();
//            String szAPList = new String(bAPList);
//            Log.d(TAG, "setMessageReceived: " + szAPList);
//            getToAPList(szAPList);
//        }else if(message == Message.PK_MSG_SET_WIFI_CONFIG) {
//
//            //// 1,ThinKF,12345678,1,2,SNDSYS,super1004~001,15830,192.168.0.65,84:f3:eb:a7:be:16
//            PValue pValue = NetworkUtils.getPValue(pbyte);
//
//            int nRet = pValue.getValue();
//            String szResult = "";
//            if(nRet == 1) {
//                szResult = "성공";
//            }else {
//                szResult = "실패";
//            }
//
//            Log.d(TAG, "setMessageReceived: PK_MSG_SET_WIFI_CONFIG = " + szResult);
//        }
//    }

    public void sendToApList() {

        PValue get_version = new PValue();

        get_version.setSize(10);
        get_version.setMessage(Message.PK_MSG_GET_LIST_AP);
        get_version.setValue(1);

        iSocket.sendMessage(get_version.toBytes());

    }

    public void getToAPList(String value) {

//        String tmp = "+CWLAP:(4,\"TP-Link_Outdoor_SMI\",-68,\"70:4f:57:60:df:42\",1,1,0)\n" +
//                "+CWLAP:(3,\"smi_fac\",-91,\"88:36:6c:94:27:08\",2,3,0)\n" +
//                "+CWLAP:(3,\"DIRECT-80-HP M426 LaserJet\",-64,\"aa:6b:ad:7a:a3:80\",3,13,0)\n" +
//                "+CWLAP:(3,\"kwea_2.4G\",-61,\"88:36:6c:e4:3e:da\",3,1,0)\n" +
//                "+CWLAP:(4,\"system\",-88,\"00:08:9f:75:f2:1a\",6,-2,0)\n" +
//                "+CWLAP:(0,\"BKT7F_2\",-82,\"10:7b:ef:d3:f8:bd\",6,-29,0)\n" +
//                "+CWLAP:(3,\"akc3f2.4GHz\",-89,\"88:36:6c:11:60:5e\",7,11,0)\n" +
//                "+CWLAP:(3,\"SNDSYS\",-65,\"90:9f:33:d7:91:9c\",8,3,0)\n" +
//                "+CWLAP:(3,\"SNDG\",-57,\"92:9f:33:d0:91:9c\",8,3,0)\n" +
//                "+CWLAP:(3,\"smi_lab\",-89,\"e4:f4:c6:00:75:ca\",1,-4,0)\n" +
//                "+CWLAP:(3,\"SHTECH_2G\",-70,\"88:36:6c:b1:bf:c8\",9,6,0)\n" +
//                "+CWLAP:(3,\"dmx\",-63,\"90:9f:33:bd:65:ac\",9,-27,0)";


        String [] dumy = value.split("\n");

        for(int i=0; i < dumy.length; i++) {
            Log.d(TAG, "getToAPList: = " + dumy[i]);
        }
    }
}
