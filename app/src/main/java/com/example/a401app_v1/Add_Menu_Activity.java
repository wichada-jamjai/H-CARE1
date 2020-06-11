package com.example.a401app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_Menu_Activity extends AppCompatActivity {

    private Button back;
    private LinearLayout addBereakfast, addLunch, addDinner;
    private String myDate;
    private TextView caloriesToday, fatToday, protToday, bfTXT, lTXT, dnTXT;
    private ImageView bfPIC, lPIC, dnPIC, cf;
    private SharedPreferences caloriesSP;
    private SharedPreferences.Editor caloriesEditor;
    private Date myDate_formatted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu);
        getRecommend();
        addBereakfast = (LinearLayout) findViewById(R.id.addBreakfast);
        addLunch = (LinearLayout) findViewById(R.id.addLunch);
        addDinner = (LinearLayout) findViewById(R.id.addDinner);
        bfPIC = (ImageView) findViewById(R.id.bfpic);
        bfTXT = (TextView) findViewById(R.id.bftxt);
        lPIC = (ImageView) findViewById(R.id.lpic);
        lTXT = (TextView) findViewById(R.id.ltxt);
        dnPIC = (ImageView) findViewById(R.id.dnpic);
        dnTXT = (TextView) findViewById(R.id.dntxt);

        caloriesToday = (TextView) findViewById(R.id.caloriesToday);
        fatToday = (TextView) findViewById(R.id.fatToday);
        protToday =  (TextView) findViewById(R.id.proteinToday);

        caloriesSP = this.getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
        caloriesEditor = caloriesSP.edit();
        myDate = caloriesSP.getString("today", "00000000");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        try {
            myDate_formatted = format.parse(myDate);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        Date today = Calendar.getInstance().getTime();
        String today_String = format.format(today);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(myDate_formatted);
        cal2.setTime(today);
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);


        // dateTime = dateTime.substring(0, Math.min(dateTime.length(), 10));

        if( sameDay) {
            int total = caloriesSP.getInt("total", 0);
            float totalFat = caloriesSP.getFloat("totalFat", 0);
            float totalProt = caloriesSP.getFloat("totalProt", 0);

            caloriesToday.setText(total + " แคลอรี่");

            DecimalFormat dFormat = new DecimalFormat("0.#");
            String formattedFat = dFormat.format(totalFat);
            String fatString = formattedFat;
            fatToday.setText(fatString + " กรัม");

            String formattedProt = dFormat.format(totalProt);
            String protString = formattedProt;
            protToday.setText(protString + " กรัม");

            checkSP();
        } else {
            myDate = today_String;
            resetSP();
        }

        if( caloriesSP.getString("breakfastname", null) != null )
            bfTXT.setText(caloriesSP.getString("breakfastname", null));

        cf = (ImageView) findViewById(R.id.clearFileBtn2);
        cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caloriesEditor.putInt("total", 0);
                caloriesEditor.putFloat("totalFat", 0);
                caloriesEditor.putFloat("totalProt", 0);
                caloriesEditor.putInt("breakfast", 0);
                caloriesEditor.putInt("lunch", 0);
                caloriesEditor.putInt("dinner", 0);
                caloriesEditor.putString("breakfastname", null);
                caloriesEditor.putString("lunchname", null);
                caloriesEditor.putString("dinnername", null);
                caloriesEditor.commit();
                caloriesToday.setText(caloriesSP.getInt("total", 0) + " แคลอรี่");
                caloriesToday.setText("0 แคลอรี่");
                fatToday.setText("0 กรัม");
                protToday.setText("0 กรัม");
                bfTXT.setText("เพิ่มเมนูอาหารเช้า");
                lTXT.setText("เพิ่มเมนูอาหารกลางวัน");
                dnTXT.setText("เพิ่มเมนูอาหารเย็น");
                bfPIC.setImageResource(R.drawable.addbreakfast);
                lPIC.setImageResource(R.drawable.addlunch);
                dnPIC.setImageResource(R.drawable.adddinner);
                addBereakfast.setEnabled(true);
                addLunch.setEnabled(true);
                addDinner.setEnabled(true);
            }
        });


        addBereakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setImg(1);
                Intent it = new Intent(getBaseContext(), Food_Name_Popup.class);
                it.putExtra("meal", "1");
                startActivity(it);
            }
        });
        addLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setImg(2);
                Intent it = new Intent(getBaseContext(), Food_Name_Popup.class);
                it.putExtra("meal", "2");
                startActivity(it);
            }
        });
        addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setImg(3);
                Intent it = new Intent(getBaseContext(), Food_Name_Popup.class);
                it.putExtra("meal", "3");
                startActivity(it);
            }
        });

        back = (Button) findViewById(R.id.backBtn1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), Mainpage_Handling_Activity.class);
                startActivity(it);
                finish();
            }
        });
    }
    public void resetSP(){

        caloriesSP = this.getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
        caloriesEditor = caloriesSP.edit();
        caloriesEditor.putString("today", myDate);
        caloriesEditor.putInt("breakfast", 0);
        caloriesEditor.putInt("lunch", 0);
        caloriesEditor.putInt("dinner", 0);
        caloriesEditor.putInt("total", 0);
        caloriesEditor.putFloat("totalFat", 0);
        caloriesEditor.putFloat("totalProt", 0);
        caloriesEditor.putString("breakfastname", null);
        caloriesEditor.putString("lunchname", null);
        caloriesEditor.putString("dinnername", null);
        caloriesEditor.commit();
        caloriesToday.setText("0 แคลอรี่");
        fatToday.setText("0 กรัม");
        protToday.setText("0 กรัม");
        bfTXT.setText("เพิ่มเมนูอาหารเช้า");
        lTXT.setText("เพิ่มเมนูอาหารกลางวัน");
        dnTXT.setText("เพิ่มเมนูอาหารเย็น");

    }


    public void checkSP(){
        caloriesSP = this.getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
        int i = caloriesSP.getInt("breakfast",0);
        int j = caloriesSP.getInt("lunch",0);
        int k = caloriesSP.getInt("dinner",0);
        //caloriesEditor = caloriesSP.edit();

        if(i == 1){
            bfPIC.setImageResource(R.drawable.breakfast);
            bfTXT.setText(caloriesSP.getString("breakfastname", null));
            addBereakfast.setEnabled(false);
        }
        if(j == 1){
            lPIC.setImageResource(R.drawable.lunch);
            lTXT.setText(caloriesSP.getString("lunchname", null));
            addLunch.setEnabled(false);
        }
        if(k == 1){
            dnPIC.setImageResource(R.drawable.dinner);
            dnTXT.setText(caloriesSP.getString("dinnername", null));
            addDinner.setEnabled(false);
        }

    }
    public  void  getRecommend(){
        Intent i = getIntent();
        String _name = i.getStringExtra("namerec");
        String _Cal = i.getStringExtra("calrec");
        //Toast.makeText(getApplicationContext(), "name : " + _name + " cal : " + _Cal, Toast.LENGTH_SHORT).show();
    }

}
