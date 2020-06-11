package com.example.a401app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Add_Recommend extends AppCompatActivity {

    private SharedPreferences caloriesSP;
    private SharedPreferences.Editor caloriesEditor;
    private String lm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recommend);
    }
    public void addReMenu(View v){
        Intent intent = new Intent(v.getContext(), Add_Menu_Activity.class);

        caloriesSP = getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
        caloriesEditor = caloriesSP.edit();
        int currentTotal = caloriesSP.getInt("total", 0);

        caloriesEditor.commit();


        switch (v.getId()){
            case R.id.rec1:
                intent.putExtra("namerec" ,"มัจฉาแต่งองค์");
                intent.putExtra("calrec" ,"175");
                caloriesEditor.putInt("total", (int)175+currentTotal);
                caloriesEditor.commit();
                setLatestMeal("มัจฉาแต่งองค์");
                startActivityForResult(intent, 0);
                break;
            case R.id.rec2:
                intent.putExtra("namerec" ,"เถาเครือม้วนคำ");
                intent.putExtra("calrec" ,"177");
                caloriesEditor.putInt("total", (int)177+currentTotal);
                caloriesEditor.commit();
                setLatestMeal("เถาเครือม้วนคำ");
                startActivityForResult(intent, 0);
                break;
            case R.id.rec3:
                intent.putExtra("namerec" ,"บัวบกคลุกฝุ่น");
                intent.putExtra("calrec" ,"49");
                caloriesEditor.putInt("total", (int)49+currentTotal);
                caloriesEditor.commit();
                setLatestMeal("บัวบกคลุกฝุ่น");
                startActivityForResult(intent, 0);
                break;
            case R.id.rec4:
                intent.putExtra("namerec" ,"ข้าวผัดหมู");
                intent.putExtra("calrec" ,"412");
                caloriesEditor.putInt("total", (int)412+currentTotal);
                caloriesEditor.commit();
                setLatestMeal("ข้าวผัดหมู");
                startActivityForResult(intent, 0);
                break;
            case R.id.rec5:
                intent.putExtra("namerec" ,"ข้าวต้มไก่");
                intent.putExtra("calrec" ,"327");
                caloriesEditor.putInt("total", (int)327+currentTotal);
                caloriesEditor.commit();
                setLatestMeal("ข้าวต้มไก่");
                startActivityForResult(intent, 0);
                break;
            case R.id.rec6:
                intent.putExtra("namerec" ,"ยำไข่ขาว");
                intent.putExtra("calrec" ,"150");
                caloriesEditor.putInt("total", (int)150+currentTotal);
                caloriesEditor.commit();
                setLatestMeal("ยำไข่ขาว");
                startActivityForResult(intent, 0);
                break;
        }

    }

    public void setLatestMeal(String mealName){
        caloriesSP = getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
        caloriesEditor = caloriesSP.edit();
        this.lm = caloriesSP.getString("latestMeal", null);

       if(this.lm.equalsIgnoreCase("breakfast")){
           caloriesEditor.putString("breakfastname", mealName);
       }
       if(this.lm.equalsIgnoreCase("lunch")){
           caloriesEditor.putString("lunchname", mealName);
       }
       if(this.lm.equalsIgnoreCase("dinner")){
           caloriesEditor.putString("dinnername", mealName);
       }
        caloriesEditor.commit();
    }

}
