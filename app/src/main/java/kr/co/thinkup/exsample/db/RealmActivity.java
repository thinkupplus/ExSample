package kr.co.thinkup.exsample.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import kr.co.thinkup.exsample.R;
import kr.co.thinkup.exsample.db.wifi.CandyInfo;

/**
 * 2019-10-04 by yh.Choi
 */
public class RealmActivity extends AppCompatActivity {

    private static final String TAG = "RealmActivity";

    private Realm           realm;

    private RealmResults<CandyInfo>     candyItems;

    private String          mID = "";
    private TextView        realm_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_realm);

        Dog dog = new Dog();
        dog.setName("Rex");
        dog.setAge(1);

        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("exsample.realm")
                .schemaVersion(0)
                .build();
//                .encryptionKey(getKey())

        Realm.setDefaultConfiguration(realmConfiguration);


        realm = Realm.getDefaultInstance();

        candyItems = realm.where(CandyInfo.class).findAll();
//        final RealmResults<Dog> puppies = realm.where(Dog.class).lessThan("age", 2).findAll();
//        puppies.size();
//
//        realm.beginTransaction();
//        final Dog managedDog = realm.copyToRealm(dog);
//        Person person = realm.createObject(Person.class, 3);
//
//        person.getDogs().add(managedDog);
//        realm.commitTransaction();
//
//        puppies.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Dog>>() {
//            @Override
//            public void onChange(RealmResults<Dog> dogs, @javax.annotation.Nullable OrderedCollectionChangeSet changeSet) {
//                Log.d(TAG, "onChange: " + dogs.size());
//                changeSet.getInsertions();
//            }
//        });
//
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm berealm) {
//                Log.d(TAG, "execute: ");
//                Dog dog1 = berealm.where(Dog.class).equalTo("age", 1).findFirst();
//                dog1.setAge(3);
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                Log.d(TAG, "onSuccess: " + puppies.size());
//                puppies.size();
//                managedDog.getAge();
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                Log.e(TAG, "onError: ", error);
//            }
//        });

        List<String> list = new ArrayList<>();

        for(int i=0; i < candyItems.size(); i++) {
            long id = candyItems.get(i).getId();

            String szID = String.valueOf(id);
            list.add(szID);
        }

        ListView listView = findViewById(R.id.realm_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        realm_tv = findViewById(R.id.realm_tv);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mID = (String)adapterView.getItemAtPosition(i);
                realm_tv.setText(mID);
                Log.d(TAG, "onItemClick: ID = " + mID);
            }
        });

        Button power = findViewById(R.id.btn_realm_power);
        Button use = findViewById(R.id.btn_realm_use);
        Button brightness = findViewById(R.id.btn_realm_brightness);

        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = realm_tv.getText().toString();
                long lid = Long.parseLong(id);

                CandyInfo info = realm.where(CandyInfo.class).equalTo("id", lid).findFirst();

                realm.beginTransaction();

                int status_power = info.getPower();

                if(status_power == 100) {
                    info.setPower(101);
                }else {
                    info.setPower(100);
                }
//                realmToExecute(info);
                realm.commitTransaction();
            }
        });

        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = realm_tv.getText().toString();
                long lid = Long.parseLong(id);

                CandyInfo info = candyItems.where().equalTo("id", lid).findFirst();

                int status_use = info.getUse();
                realm.beginTransaction();

                if(status_use == 200) {
                    info.setPower(201);
                }else {
                    info.setPower(200);
                }
                realm.commitTransaction();
//                realmToExecute(info);
            }
        });

        brightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = realm_tv.getText().toString();
                long lid = Long.parseLong(id);

                CandyInfo info = realm.where(CandyInfo.class).equalTo("id", lid).findFirst();

                CandyInfo copy = realm.copyToRealm(info);
                realm.beginTransaction();
                int brightness = copy.getBrightness();
                brightness += 10;
                copy.setBrightness(brightness);

                realm.commitTransaction();
//                realmToExecute(copy);
            }
        });

        candyItems.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<CandyInfo>>() {
            @Override
            public void onChange(RealmResults<CandyInfo> candyInfos, @javax.annotation.Nullable OrderedCollectionChangeSet changeSet) {
                int size = candyItems.size();
                Log.d(TAG, "onChange: size = " + size);
            }
        });

        wifiInfo();
    }

    private void wifiInfo() {
        createDemo();
        createMaster();
        createSlave();

        long maxid = getMaxID();

        Log.d(TAG, "wifiInfo: = " + maxid);
    }

    private long getMaxID() {
        return realm.where(CandyInfo.class).max("id").longValue();
    }

    private void createDemo() {

        CandyInfo demo = new CandyInfo();

        demo.setId(99);
        demo.setAlias("DEMO");
        demo.setPower(100);
        demo.setUse(200);
        demo.setBrightness(100);

        demo.setAp_ssid("ThinKF_LED");

        realmToExecute(demo);
    }

    private void createMaster() {
        CandyInfo master = new CandyInfo();

        master.setId(0);

        master.setAlias("LED1");

        master.setDevice_mode(2);

        master.setAp_ssid("ThinKF_LED");
        master.setAp_bssid("aaaaaa");
        master.setAp_password("1234567890");
        master.setAp_capabilities(3);
        master.setAp_ip("192.168.0.1");

        realmToExecute(master);
    }


    private void createSlave() {
        CandyInfo slave = new CandyInfo();

        slave.setId(1);
        slave.setAlias("LED_SLAVE");

        slave.setDevice_mode(1);

        slave.setSta_ssid("ThinKF_LED");
        slave.setSta_bssid("bbbbb");
        slave.setSta_password("1234567890");
        slave.setSta_capabilities(3);
        slave.setSta_ip("192.168.0.11");

        slave.setAp_ssid("iptime_11");
        slave.setAp_bssid("cccccccc");
        slave.setAp_password("ucore2017");
        slave.setAp_capabilities(3);
        slave.setAp_ip("192.168.0.1");

        realmToExecute(slave);
    }


    private void realmToExecute(final CandyInfo info) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.copyToRealmOrUpdate(info);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ID = " + info.getId());
                Log.d(TAG, "onSuccess: Count =  " + candyItems.size());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "onError: ID = " + info.getId(), error);
            }
        });
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
