package com.ming.part2_3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Lab3_3Activity extends AppCompatActivity implements View.OnClickListener {

    Button trueBtn;
    TextView targetTextView;
    Button falseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_3);
        //View 객체 회득
        trueBtn = (Button)findViewById(R.id.btn_visible_true);
        targetTextView = (TextView)findViewById(R.id.text_visible_target);
        falseBtn = (Button)findViewById(R.id.btn_visible_false);
        //Button 이벤트 등록
        trueBtn.setOnClickListener(this);
        falseBtn.setOnClickListener(this);
    }

    //버튼 이벤트 콜백함수
    @Override
    public void onClick(View v){
        if(v==trueBtn){
            //trueBtn이 눌리면 targetTextView를 visible 상태로 변경
            targetTextView.setVisibility(View.VISIBLE);
        }
        else if(v==falseBtn){
            //falseBtn 눌리면 targetTextView를 invisible 상태로 변경
            targetTextView.setVisibility(View.INVISIBLE);
        }
    }
}
