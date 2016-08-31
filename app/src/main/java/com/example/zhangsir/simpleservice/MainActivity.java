package com.example.zhangsir.simpleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    Intent serviceIntent;
    boolean bIsDoubleClick=false;
    boolean bIsPlaying=false;
    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        serviceIntent=new Intent(this,BackgroundAudioService.class);
        view=(ImageView)findViewById(R.id.view);
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(isDoubleClick()){    //如果是双击，则进行相应的操作
                   if(BackgroundAudioService.isPlaying()){
                       stopService(serviceIntent);
                   }
                   else{
                       startService(serviceIntent);
                   }
                    finish();
                }
                break;
        }
        return false;
    }

    private boolean isDoubleClick(){
        if(bIsDoubleClick){
            return true;
        }
        else{
            new Thread(){
                @Override
                public void run(){
                    bIsDoubleClick=true;
                    try {
                        //睡眠170ms，等待第二次单击
                        Thread.sleep(170);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bIsDoubleClick=false;
                }
            }.start();
            return false;
        }
    }
}
