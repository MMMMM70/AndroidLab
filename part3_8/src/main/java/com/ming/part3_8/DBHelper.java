package com.ming.part3_8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, "memodb", null, DATABASE_VERSION);
    }
    // SQLiteOpenHelper 작성하려면 onCreate()와 onUpgrade() 함수 재정의 필요
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 앱이 설치된 후 SQLiteOpenHelper가 최초로 이용되는 순간 한 번 호출
        // 대부분 테이블 생성하는 코드 작성
        String memoSQL = "create table tb_memo" +
                "(_id integer primary key autoincrement," +
                "title,"+
                "content)";
        db.execSQL(memoSQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // 데이터베이스 버전이 변경될 때마다 호출
        // 생성자에 전달되는 데이터베이스 버전 정보가 변경될 떄마다 반복해서 호출
        // 테이블의 스키마 부분을 변경하기 위한 용도로 사용
        db.execSQL("drop table tb_memo");
        onCreate(db);
    }
}