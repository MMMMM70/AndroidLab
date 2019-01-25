package com.ming.part5_16;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    ImageView mainStartView;
    ImageView mainPauseView;

    boolean loopFlag=true;
    boolean isFirst=true;
    boolean isRun;

    MyThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.main_textView);
        mainStartView=(ImageView)findViewById(R.id.main_startBtn);
        mainPauseView=(ImageView)findViewById(R.id.main_pauseBtn);

        mainStartView.setOnClickListener(this);
        mainPauseView.setOnClickListener(this);

        thread = new MyThread();
    }
    @Override
    public void onClick(View v){

        if(v==mainStartView){
            if(isFirst){
                isFirst = false;
                isRun = true;
                thread.start();
            }
            else{
                isRun = true;
            }
        }
        else if(v==mainPauseView){
            isRun = false;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                textView.setText(String.valueOf(msg.arg1));
            } else if(msg.what==2){
                textView.setText((String)msg.obj);
            }
        }
    };

    class MyThread extends Thread {
        public void run(){
            try{
                int count=10;
                while(loopFlag){
                    Thread.sleep(1000);
                    if(isRun){
                        count--;
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = count;
                        handler.sendMessage(message);
                        if(count == 0){
                            //종료
                            message = new Message();
                            message.what = 2;
                            message.obj = "finish!!";
                            handler.sendMessage(message);
                            //thread 종료되게 설정
                            loopFlag = false;
                        }
                    }
                }
            } catch (Exception e){

            }
        }
    }
}
