package com.karrel.realmsample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.karrel.realmsample.databinding.ActivityMainBinding;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    private Realm realm;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Realm.init(this);
        setupRealm();
        setupEvent();
    }

    private void setupEvent() {
        // 단순한 추가
        binding.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                Protocol protocol = realm.createObject(Protocol.class);
                protocol.name = "test";
                protocol.hexCode = "111111";
                realm.commitTransaction();
            }
        });

        // 추가된 값들을 조회
        binding.getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmResults<Protocol> protocols = realm.where(Protocol.class).findAll();
                for (int i = 0; i < protocols.size(); i++) {
                    Protocol protocol = protocols.get(i);
                    Log.d(TAG, "protocol : " + protocol);
                }
            }
        });

        // 오브젝트를 삭제해봅시당
        binding.deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 특정 값에 대한 객체를 가져온다
                RealmResults<Protocol> protocols = realm.where(Protocol.class).equalTo("hexCode", "111111").findAll();

                // 사용하기전에 아래의 메소드를 호출해야한다
                realm.beginTransaction();
                protocols.deleteAllFromRealm();
                // 커밋
                realm.commitTransaction();
            }
        });
    }

    private void setupRealm() {
        realm = Realm.getDefaultInstance();
        // 변경된 사항들에 대한 감시
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Log.e(TAG, "onChange");

                RealmResults<Protocol> protocols = realm.where(Protocol.class).findAll();
                for (int i = 0; i < protocols.size(); i++) {
                    Protocol protocol = protocols.get(i);
                    Log.d(TAG, "protocol : " + protocol);
                }
            }
        });
    }
}
