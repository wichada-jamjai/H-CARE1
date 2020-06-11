package com.example.a401app_v1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class activity_recognize_home extends AppCompatActivity {
    private Button back , startTrack;

    private TextView totalBurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize_home);
        back = (Button) findViewById(R.id.backBtn3);
        startTrack =(Button) findViewById(R.id.startTrack);
        totalBurn = findViewById(R.id.totalBurn);

        getValueActivity();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), Mainpage_Handling_Activity.class);
                startActivity(it);
                finish();
            }
        });
        startTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), Recognize_Activity.class);
                startActivity(it);
            }
        });
    }
    public void getValueActivity(){
        /*********************************รับค่าเวลา Activity************************************/
        Intent i = getIntent();
        String  timeWalk  = i.getStringExtra("timeWalk");
        String  timeRun = i.getStringExtra("timeRun");

        if(timeWalk!=null&&timeRun!=null){
           /* Toast.makeText(getApplicationContext(), "เดินไปทั้งหมด : "+timeWalk+"  ms" , Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "วิ่งไปทั้งหมด : "+timeRun+" ms" , Toast.LENGTH_SHORT).show();*/

            long walk = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(timeWalk));
            long run = TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(timeRun));
/*
                Toast.makeText(getApplicationContext(), "เดินไปทั้งหมด : "+walk+"  นาที" , Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "วิ่งไปทั้งหมด : "+run+"  นาที" , Toast.LENGTH_SHORT).show();*/

            float walkburn = walkCalculate(walk);
            float runburn = runCalculate(run);
            float totalburn =  walkburn + runburn  ;
            String formattedValue = String.format("%.3f", totalburn);

            totalBurn.setText(formattedValue + " แคลอรี");
            //Toast.makeText(getApplicationContext(), "เผาผลาญรวม : "+formattedValue+"  แคลอรี" , Toast.LENGTH_SHORT).show();
        }
    }
    public  float walkCalculate(long walkTime){
        float burnWalk = (float) (walkTime * ((167.33 / 30 )/60));
        // Toast.makeText(getApplicationContext(), "เผาผลาญด้วยการเดิน : "+ burnWalk+ "  แคลอรี" , Toast.LENGTH_SHORT).show();
        return  burnWalk;
    }
    public  float runCalculate(long runTime){
        float burnRun = (float) (runTime * ((372 / 30 ))/60);
        //Toast.makeText(getApplicationContext(), "เผาผลาญด้วยการวิ่ง : "+ burnRun + "  แคลอรี" , Toast.LENGTH_SHORT).show();
        return  burnRun;
    }


}
