package com.example.a401app_v1;
import android.content.BroadcastReceiver;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a401app_v1.services.ActivityDetectionService;
import com.example.a401app_v1.utils.Constant;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

public class Recognize_Activity extends AppCompatActivity {
    public static final String TAG =Recognize_Activity.class.getSimpleName();
    private TextView mTextARLabel;
    private TextView mTextConfidence;
    private ImageView showImg;
    private  Button stop;
   private Chronometer chronometer_walk , chronometer_run,chronometer_bike;
    private long pauseOffset_walk , pauseOffset_run , pauseOffset_bike ;
    private boolean walking , bikeing ,running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);
        mTextARLabel = findViewById(R.id.text_label);
        mTextConfidence = findViewById(R.id.text_confidence);
        mTextConfidence.setVisibility(View.GONE);
        showImg = findViewById(R.id.showActivity);
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recognize_Activity.this,activity_recognize_home.class);
               // stopService(intent);
                pausewalk();
                pauserun();

                intent.putExtra("timeWalk",pauseOffset_walk+"");
                intent.putExtra("timeRun",pauseOffset_run+"");
               /* Toast.makeText(getApplicationContext(), "เดินไปทั้งหมด : "+pauseOffset_walk+" :  ms" , Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "วิ่งไปทั้งหมด : "+pauseOffset_run+" :  ms" , Toast.LENGTH_SHORT).show();   */

                resetwalk();
                resetrun();
                Toast.makeText(getApplicationContext(), "หยุดตรวจจับ" , Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });

        chronometer_walk = findViewById(R.id.ch_walk);
        chronometer_walk.setFormat("เวลาการเดิน : %s   นาที");
        chronometer_walk.setBase(SystemClock.elapsedRealtime());
        chronometer_walk.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 7200000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(getApplicationContext(), " คุณออกกำลังกายไป 2 ชั่วโมงแล้ว", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**************************************************************************************************/
        chronometer_run = findViewById(R.id.ch_run);
        chronometer_run.setFormat("เวลาการวิ่ง : %s   นาที");
        chronometer_run.setBase(SystemClock.elapsedRealtime());
        chronometer_run.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 1799999) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(getApplicationContext(), " ครบ 30 นาทีแล้ว ", Toast.LENGTH_SHORT).show();
                }
            }
        });

           }
    /*********************************************************จับเวลาการเดิน***************************************************************************/
   public void startwalk() {
        if (!walking) {
            chronometer_walk.setBase(SystemClock.elapsedRealtime() - pauseOffset_walk);
            chronometer_walk.setVisibility(View.VISIBLE);
            chronometer_walk.start();
            walking = true;
        }
    }
      public void pausewalk() {
         if (walking) {
             chronometer_walk.stop();
             pauseOffset_walk = SystemClock.elapsedRealtime() - chronometer_walk.getBase();
             walking = false;
         }
     }
     public void resetwalk() {
         chronometer_walk.setBase(SystemClock.elapsedRealtime());
         pauseOffset_walk = 0;
     }
     /************************************************************จับเวลาการวิ่ง****************************************************************/
     public void startrun() {
         if (!running) {
             chronometer_walk.setVisibility(View.VISIBLE);
             chronometer_run.setBase(SystemClock.elapsedRealtime() - pauseOffset_run);
             chronometer_run.start();
             running = true;
         }
     }
    public void pauserun() {
        if (running) {
            chronometer_run.stop();
            pauseOffset_run = SystemClock.elapsedRealtime() - chronometer_run.getBase();
            running = false;
        }
    }
    public void resetrun() {
        chronometer_run.setBase(SystemClock.elapsedRealtime());
        pauseOffset_run = 0;
    }
/***********************************************************conect API***********************************************************************/
     @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart():start ActivityDetectionService");
        LocalBroadcastManager.getInstance(this).registerReceiver(mActivityBroadcastReceiver,
                new IntentFilter(Constant.BROADCAST_DETECTED_ACTIVITY));
        startService(new Intent(this, ActivityDetectionService.class));
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause():stop ActivityDetectionService");
       // Toast.makeText(getApplicationContext(), "หยุดตรวจจับ" , Toast.LENGTH_SHORT).show();
        if(mActivityBroadcastReceiver != null){
            stopService(new Intent(this, ActivityDetectionService.class));
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mActivityBroadcastReceiver);
        }
    }
    BroadcastReceiver mActivityBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Log.d(TAG, "onReceive()");
            if (intent.getAction().equals(Constant.BROADCAST_DETECTED_ACTIVITY)) {
                int type = intent.getIntExtra("type", -1);
                int confidence = intent.getIntExtra("confidence", 0);
               handleUserActivity(type,confidence);
               //Toast.makeText(getApplicationContext(),"data add",Toast.LENGTH_SHORT).show();
            }else{
                //do something//
            }
        }
    };
    private void handleUserActivity(int type, int confidence) {
        String label ="ไม่ทราบกิจกรรม";
        switch (type) {

            case DetectedActivity.RUNNING: {
                label = "วิ่ง";
                break;
            }
            case DetectedActivity.WALKING: {
                label = "เดิน";
                break;
            }
        }
        Log.d(TAG, "broadcast:onReceive(): Activity is    " + label
                + " and confidence level is: " + confidence);
      /*  Toast.makeText(getApplicationContext(),  label
                + "   " + confidence + "  %",Toast.LENGTH_SHORT).show();*/
        if (confidence > Constant.CONFIDENCE && type == DetectedActivity.WALKING ) {
                mTextARLabel.setText(label);
              //  mTextConfidence.setText(confidence + "  %");
                setImg(label);
                countTime(type);
                onPause();
        }else if (confidence > Constant.CONFIDENCE && type == DetectedActivity.WALKING ){
                mTextARLabel.setText(label);
            //  mTextConfidence.setText(confidence + "  %");
                setImg(label);
                countTime(type);
                onPause();
        }
    }
    public void countTime( int type ){
        switch (type) {
            case DetectedActivity.RUNNING: {
                startrun();
                break;
            }
            case DetectedActivity.WALKING: {
                startwalk();
                break;
            }
        }
    }
    public void setImg(String labelActivity){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(280, 200);
        switch (labelActivity) {
            case"เดิน": {
                showImg.setImageResource(R.drawable.walking);
                break;
            }
            case "วิ่ง": {
                showImg.setImageResource(R.drawable.running);
                break;
            }
        }
    }
}
