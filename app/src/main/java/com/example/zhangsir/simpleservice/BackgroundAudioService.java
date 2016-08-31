package com.example.zhangsir.simpleservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ZhangSir on 2016/8/31.
 */
public class BackgroundAudioService extends Service implements MediaPlayer.OnCompletionListener{
    final static String TAG="BackgroundAudioService";
    private static MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Log.v(TAG,"onCreate()");
        mediaPlayer=MediaPlayer.create(this,R.raw.crazy_for_love);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.v(TAG,"onStartCommand()");
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer=null;//如果没有这条语句，当第一次启动service并关闭后，mediaPlayer就不是null了，
                        //但此时如果再次启动程序并在主界面调用 BackgroundAudioService.isPlaying(),
                        //会判断 mediaPlayer!=null,然后执行 return mediaPlayer.isPlaying(),但此时mediaPlay
                        //的引用时无效的，所以程序会崩溃
        Log.v(TAG,"onDestroy()");
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopSelf();
    }

    public static boolean isPlaying(){
       if(mediaPlayer==null){
           return false;
       }
        else{
           return mediaPlayer.isPlaying();
       }
    }
}
