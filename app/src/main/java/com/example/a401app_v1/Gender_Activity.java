package com.example.a401app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class Gender_Activity extends AppCompatActivity {


    private RadioButton rMale, rFemale;
    private ImageButton buttonNext1;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_register01);

        SharedPreferences myPrefs = this.getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
        String name = myPrefs.getString("name",null);

        if(name != null) {
            Intent intent = new Intent(getBaseContext(), Mainpage_Handling_Activity.class);
            startActivity(intent);
            finish();
        } else {
            rFemale = findViewById(R.id.rdoFemale);
            rMale = findViewById(R.id.rdoMale);
            buttonNext1 = findViewById(R.id.buttonNext1);

            buttonNext1.setEnabled(false);
            buttonNext1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getBaseContext(), Info_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("stuffs", gender);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
        }

    }

    public void radioCheck(View view){

        if(rFemale.isChecked() || rMale.isChecked()){
            buttonNext1.setBackgroundResource(R.drawable.button_noclick_next);
            buttonNext1.setEnabled(true);
            if(rFemale.isChecked()){
                this.gender = "f";
            } else if(rMale.isChecked()){
                this.gender = "m";
            }
        }
    }

}
