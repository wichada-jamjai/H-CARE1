package com.example.a401app_v1;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class Check_Select_Activity extends AppCompatActivity {
    private RadioButton rdo1, rdo2 ;
   // private int any = 0, rdo = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_amount);
        rdo1 = findViewById(R.id.rdo1);
        rdo2 = findViewById(R.id.rdo2);
        rdo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdo1.setChecked(true);
                rdo2.setChecked(false);
            }
        });
        rdo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdo2.setChecked(true);
                rdo1.setChecked(false);
            }
        });

    }
    }




