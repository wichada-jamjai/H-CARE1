package com.example.a401app_v1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;


public class Disease_Activity extends AppCompatActivity {

    private CheckBox cb1, cb2, cb3, cb4, cb5;
    private ImageButton buttonNext3;
    private String disease = "", toSend = "";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_register03);

        Bundle bundle = getIntent().getExtras();
        toSend = bundle.getString("stuffs");

        cb1 = findViewById(R.id.check1);
        cb2 = findViewById(R.id.check2);
        cb3 = findViewById(R.id.check3);
        cb4 = findViewById(R.id.check4);
        cb5 = findViewById(R.id.check5);

        buttonNext3 = findViewById(R.id.buttonNext3);
        buttonNext3.setEnabled(false);
        buttonNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheck();
                toSend = toSend + disease;
                Intent intent = new Intent(getBaseContext(), Mainpage_Handling_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stuffs", toSend);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onNonMedicalChecked(View view) {

        if (cb5.isChecked()){
            cb1.setChecked(false);
            cb2.setChecked(false);
            cb3.setChecked(false);
            cb4.setChecked(false);
            buttonNext3.setEnabled(true);
            buttonNext3.setBackgroundResource(R.drawable.button_noclick_next);
        } else {
            buttonNext3.setEnabled(false);
            buttonNext3.setBackgroundResource(R.drawable.button_noclickable_next);
        }

    }

    public void onMedicalChecked(View view) {
        if( cb1.isChecked() || cb2.isChecked() ||cb3.isChecked() ||cb4.isChecked() ){
            cb5.setChecked(false);
            buttonNext3.setEnabled(true);
            buttonNext3.setBackgroundResource(R.drawable.button_noclick_next);
        } else {
            buttonNext3.setEnabled(false);
            buttonNext3.setBackgroundResource(R.drawable.button_noclickable_next);
        }

    }

    public void getCheck() {

        if (cb1.isChecked()) disease = disease + "1"; //เบาหวาน
        if (cb2.isChecked()) disease = disease + "2"; //ค.ดัน
        if (cb3.isChecked()) disease = disease + "3"; //ไขมันในเลือด
        if (cb4.isChecked()) disease = disease + "4"; //ไตเรื้อรัง
        if (cb5.isChecked()) disease = disease + "5"; //ไม่มีโรค

    }

}
