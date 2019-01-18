package com.ming.part4_10;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] arrayDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView arrayView = (ListView)findViewById(R.id.main_listview_array);
        arrayView.setOnItemClickListener(this);
        ListView simpleView = (ListView)findViewById(R.id.main_listview_simple);
        ListView cursorView = (ListView)findViewById(R.id.main_listview_cursor);

        // 1) ArrayAdapter : 한 항목에 문자열 데이터 하나를 순서대로 나열할 때
        arrayDatas = getResources().getStringArray(R.array.location);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayDatas);
        //Context 객체, 항목 하나를 구성하기 위한 레이아웃 XML 파일 정보, 데이터
        arrayView.setAdapter(arrayAdapter);


        ArrayList<HashMap<String, String>> simpleDatas = new ArrayList<>();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_data", null);
        while(cursor.moveToNext()){
            HashMap<String, String> map = new HashMap<>();
            map.put("name", cursor.getString(1));
            map.put("content", cursor.getString(2));
            simpleDatas.add(map);
        }


        // 2) SimpleAdapter : 한 항목에 문자열 데이터 여러 개 나열할 때
        SimpleAdapter adapter = new SimpleAdapter(this, simpleDatas,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "content"},
                new int[]{android.R.id.text1, android.R.id.text2});
        // Context 객체, 한 항목을 위한 레이아웃 XML,
        // 한 항목의 데이터를 가지는 HashMap에서 데이터를 추출하기 위한 키값, 화면에 데이터를 출력하기 위한 레이아웃 파일 내의 뷰 id 값
        simpleView.setAdapter(adapter);


        // 3) CursorAdapter : 안드로이드 DBMS 프로그램의 select 결과값을 그대로 이용해 항목을 구성해줌
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"name", "content"},
                new int[]{android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // Context 객체, 항목 구성 레아이웃 XML, 데이터 추출 시 이용할 칼러명
        // 레이아웃 XML의 데이터가 출력될 뷰의 id 값, 플래그
        cursorView.setAdapter(cursorAdapter);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Toast t = Toast.makeText(this, arrayDatas[position], Toast.LENGTH_SHORT);
        t.show();
    }
}
