package com.example.a401app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Recommend_Home_Activity extends AppCompatActivity {

    private Button back;
    private LinearLayout layoutFood1,layoutFood2,layoutFood3,layoutFood4,layoutFood5,layoutFood6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_home);
        layoutFood1 = findViewById(R.id.menu_1);
        layoutFood2 = findViewById(R.id.menu_2);
        layoutFood3 = findViewById(R.id.menu_3);
        layoutFood4 = findViewById(R.id.menu_4);
        layoutFood5 = findViewById(R.id.menu_5);
        layoutFood6 = findViewById(R.id.menu_6);

        layoutFood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLayoutClick(1);
            }
        });
        layoutFood2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLayoutClick(2);
            }
        });
        layoutFood3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLayoutClick(3);
            }
        });
        layoutFood4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLayoutClick(4);
            }
        });
        layoutFood5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLayoutClick(5);
            }
        });
        layoutFood6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLayoutClick(6);
            }
        });

        back = (Button) findViewById(R.id.backBtn2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
    }

    private void onLayoutClick(int i){

        Intent it = new Intent(getBaseContext(), Recommend_Activity.class);

        switch (i) {
            case 1: {
                it.putExtra("menuNo", "1");
                break;
            }
            case 2: {
                it.putExtra("menuNo", "2");
                break;
            }
            case 3: {
                it.putExtra("menuNo", "3");
                break;
            }
            case 4: {
                it.putExtra("menuNo", "4");
                break;
            }
            case 5: {
                it.putExtra("menuNo", "5");
                break;
            }
            case 6: {
                it.putExtra("menuNo", "6");
                break;
            }
        }
        startActivity(it);
    }


}
