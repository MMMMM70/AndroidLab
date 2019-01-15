package com.ming.part3_8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;

public class Lab8_2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText titleView;
    EditText contentView;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab8_2);

        titleView = (EditText) findViewById(R.id.realm_add_title);
        contentView = (EditText) findViewById(R.id.realm_add_content);
        addBtn = (Button) findViewById(R.id.realm_add_btn);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        final String title = titleView.getText().toString();
        final String content = contentView.getText().toString();

        Realm.init(this);
        Realm mRealm = Realm.getDefaultInstance(); // Realm 객체 획득
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){ // vo에 데이터 저장
                MemoVO vo = realm.createObject(MemoVO.class); //Relam과 연결된 VO 객체 획득
                vo.title = title;
                vo.content = content;
            }
        });

        Intent intent = new Intent(this, RealmReadActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}
