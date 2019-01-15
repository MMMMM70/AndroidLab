package com.ming.part3_8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import io.realm.Realm;

public class RealmReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_read);

        TextView titleView = (TextView)findViewById(R.id.realm_read_title);
        TextView contentview = (TextView) findViewById(R.id.realm_read_content);

        //넘어온 title 문자열 추출
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        Realm mRealm = Realm.getDefaultInstance(); // Relam 객체 획득
        MemoVO vo = mRealm.where(MemoVO.class).equalTo("Title", title).findFirst(); //저장된 데이터 획득
        titleView.setText(vo.title);
        contentview.setText(vo.content);
    }
}
