package com.example.a401app_v1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.telecom.Call;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Notification_Activity extends Activity {

    private LinearLayout noNoti, caloriesNoti, fatNoti, protNoti1, protNoti2, proteinLO1, proteinLO2, fatLO;
    private Button close;
    private TextView calTxt, protTxt1, protTxt2, fatTxt;
    Bundle extras;
    String toNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        getWindow().setLayout((int)width, WindowManager.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        proteinLO1 = findViewById(R.id.protein_Layout1);
        proteinLO2 = findViewById(R.id.protein_Layout2);
        fatLO =findViewById(R.id.fat_Layout);

        noNoti = findViewById(R.id.noNoti);
        caloriesNoti = findViewById(R.id.caloriesNoti);
        fatNoti= findViewById(R.id.fatNoti);
        protNoti1 = findViewById(R.id.protNoti1);
        protNoti2 = findViewById(R.id.protNoti2);

        calTxt  = findViewById(R.id.calText);
        protTxt1 = findViewById(R.id.proteinText1);
        protTxt2 = findViewById(R.id.proteinText2);
        fatTxt= findViewById(R.id.fatText);


        close = findViewById(R.id.closeNoti);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fadeout, R.anim.fadeout);
            }
        });

        extras = getIntent().getExtras();
        toNoti = extras.getString("noti");

        String notiList[] = toNoti.split("=");

        for(int i = 0; i < notiList.length; i++){

            String notiArr[] = notiList[i].split(",");

                if(notiArr[0].equals("cal")){
                    calTxt.setText(notiArr[1] + " / " + notiArr[2]);
                    if(notiArr[3].equalsIgnoreCase("Y")){
                        calTxt.setTextColor(Color.RED);
                        caloriesNoti.setVisibility(View.VISIBLE);
                        noNoti.setVisibility(View.GONE);
                    }
                }
                if(notiArr[0].equals("fat")){
                    fatLO.setVisibility(View.VISIBLE);
                    fatTxt.setText(notiArr[1] + " / " + notiArr[2]);
                    if(notiArr[3].equalsIgnoreCase("Y")){
                        fatTxt.setTextColor(Color.RED);
                        fatNoti.setVisibility(View.VISIBLE);
                        noNoti.setVisibility(View.GONE);
                    }
                }
                if(notiArr[0].equals("prot1")){
                    proteinLO1.setVisibility(View.VISIBLE);
                    protTxt1.setText(notiArr[1] + " / " + notiArr[2]);
                    if(notiArr[3].equalsIgnoreCase("Y")){
                        protTxt1.setTextColor(Color.RED);
                        protNoti1.setVisibility(View.VISIBLE);
                        noNoti.setVisibility(View.GONE);
                    }
                }
                if(notiArr[0].equals("prot2")){
                    proteinLO2.setVisibility(View.VISIBLE);
                    protTxt2.setText(notiArr[1] + " / " + notiArr[2]);
                    if(notiArr[3].equalsIgnoreCase("Y")){
                        protTxt2.setTextColor(Color.RED);
                        protNoti2.setVisibility(View.VISIBLE);
                        noNoti.setVisibility(View.GONE);
                    }
                }
            }
        }
}
