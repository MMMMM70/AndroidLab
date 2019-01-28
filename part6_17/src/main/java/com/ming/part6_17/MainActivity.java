package com.ming.part6_17;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1;
    Button btn2;
    Button btn3;

    FragmentManager manager;
    OneFragment oneFragment;
    TwoFragment twoFragment;
    ThreeFragment threeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1=(Button)findViewById(R.id.main_btn1);
        btn2=(Button)findViewById(R.id.main_btn2);
        btn3=(Button)findViewById(R.id.main_btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        manager = getSupportFragmentManager();

        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();

        FragmentTransaction fragmentTransaction = manager.beginTransaction();
    }

    @Override
    public void onClick(View v){
        if(v==btn1){
            if(!oneFragment.isVisible()){
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_container, oneFragment);
                fragmentTransaction.commit();
            }
        }
        else if(v==btn2){
            if(!twoFragment.isVisible()){
                twoFragment.show(manager, null);
            }
        }
        else if(v==btn3){
            if(!threeFragment.isVisible()){
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_container, threeFragment);
                fragmentTransaction.commit();
            }
        }
    }
}
