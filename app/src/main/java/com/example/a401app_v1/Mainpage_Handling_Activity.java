package com.example.a401app_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseRegistrar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Mainpage_Handling_Activity extends AppCompatActivity {

    private TextView name , calories, caloriesLeft;
    private String[] information;
    private LinearLayout createMenu, recognizeActiv, recommendMenu;
    private  double BMR;
    private String BMRString, getDisease, getGender,getAge;
    private ImageView cf;
    private Button noti, edt;
    private SharedPreferences caloriesSP;
    private SharedPreferences.Editor caloriesEditor;
    private String myDate;
    private int getWeight, getHeight, idealWeight;
    private boolean diabetes = false, kidney = false;
    Date myDate_formatted;
    private String toNoti = "";
    DatabaseReference reff;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        name= findViewById(R.id.nameTextView);
        calories = findViewById(R.id.caloriesNeedTextView);
        caloriesLeft  = findViewById(R.id.caloriesLeftTextView);
        noti = findViewById(R.id.notiBtn);
        edt = findViewById(R.id.infoEdit);
        recognizeActiv = findViewById(R.id.layoutActivityRec);
        recommendMenu = findViewById(R.id.layoutReccom);

        SharedPreferences sp = this.getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String getName = sp.getString("name",null);

        member = new Member();
        reff = FirebaseDatabase.getInstance().getReference().child("member");

        if(getName != null) {
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            this.getGender = sp.getString("gender", null);
            this.getAge = sp.getString("birth", null);
            this.getWeight = sp.getInt("weight", 0);
            this.getHeight = sp.getInt("height", 0);
            this.getDisease = sp.getString("disease", null);

            String[] birthDate = getAge.split("-");
            int age = getAge(Integer.parseInt(birthDate[0]),Integer.parseInt(birthDate[1]) ,Integer.parseInt(birthDate[2]));

            name.setText("สวัสดี " + getName);
            double ageF = (double) age;
            double weightF = (double) getWeight;
            double heightF = (double) getHeight;

            calculateCalories(getGender, weightF, heightF, ageF);

        } else {
            Bundle bundle = getIntent().getExtras();
            information = bundle.getString("stuffs").split("=");
            String[] birthDate = information[2].split("-");
            int age = getAge(Integer.parseInt(birthDate[0]),Integer.parseInt(birthDate[1]) ,Integer.parseInt(birthDate[2]));
            double ageF =  (double) age;
            int weight = Integer.parseInt(information[3]);
            double weightF = (double) weight;
            int height = Integer.parseInt(information[4]);
            double heightF = (double) height;
            this.getGender = information[0];
            this.getDisease = information[6];

            name.setText("สวัสดี คุณ" + information[1]);
            calculateCalories(information[0], weightF, heightF, ageF);
            member.setName(information[1]);
            member.setBirth(information[2]);
            member.setGender(information[0]);
            member.setHeight(Integer.parseInt(information[4]));
            member.setWeight(Integer.parseInt(information[3]));
            member.setPhone(information[5]);
            member.setDisease(information[6]);
            member.setBmr(BMRString);
            reff.push().setValue(member);

            editor.putString("gender", information[0]);
            editor.putString("name", information[1]);
            editor.putString("birth", information[2]);
            editor.putInt("weight", Integer.parseInt(information[3]));
            editor.putInt("height", Integer.parseInt(information[4]));
            editor.putString("phone", information[5]);
            editor.putString("disease", information[6]);
            editor.putString("bmr", BMRString);
            editor.commit();
        }

        createMenu = (LinearLayout) findViewById(R.id.layoutFoodcal);
        createMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), Add_Menu_Activity.class);
                startActivity(it);
            }
        });

        recommendMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), Recommend_Home_Activity.class);
                startActivity(it);
            }
        });

        recognizeActiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(),activity_recognize_home.class);
                startActivity(it);
            }
        });

        cf = (ImageView) findViewById(R.id.clearFileBtn);
        cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Mainpage_Handling_Activity.this);
                alertDialogBuilder.setMessage("ต้องการล้างข้อมูพื้นฐานหรือไม่? \nเมื่อล้างข้อมูลพื้นฐาน ข้อมูลทั้งหมดจะถูกลบ และจะกลับไปยังหน้ากรอกข้อมูลอีกครั้ง");
                        alertDialogBuilder.setPositiveButton("ล้างข้อมูล",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        SharedPreferences sp = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("gender", null);
                                        editor.putString("name", null);
                                        editor.putInt("age", 0);
                                        editor.putInt("weight", 0);
                                        editor.putInt("height", 0);
                                        editor.putString("phone", null);
                                        editor.commit();
                                        SharedPreferences caloriesSP = getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor caloriesEditor = caloriesSP.edit();
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
                                        Intent it = new Intent(getBaseContext(), Gender_Activity.class);
                                        startActivity(it);
                                        finish();
                                    }
                                });

                alertDialogBuilder.setNegativeButton("ยกเลิก",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), Notification_Activity.class);
                it.putExtra("noti", toNoti);
                startActivity(it);
            }
        });

        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), Edit_Info_Activity.class);
                startActivity(it);
            }
        });

    }

    private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);

        return ageInt;
    }

    private void calculateCalories(String gender, double weightF, double heightF, double ageF){

        if (gender.equalsIgnoreCase("m")) {
            BMR = 66 + (13.7 * weightF) + (5 * heightF) - (6.8 * ageF);
            DecimalFormat dFormat = new DecimalFormat("0.#");
            String formattedValue = dFormat.format(BMR);
            BMRString = formattedValue;
            calories.setText(formattedValue);
            setCaloriesLeft();
        } else if (gender.equalsIgnoreCase("f")) {
            BMR = 665 + (9.6 * weightF) + (1.8 * heightF) - (4.7 * ageF);
            DecimalFormat dFormat = new DecimalFormat("0.#");
            String formattedValue = dFormat.format(BMR);
            BMRString = formattedValue;
            calories.setText(formattedValue);
            setCaloriesLeft();
        }

    }

    private void setCaloriesLeft(){

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

        if( sameDay) {
            double total = (double) caloriesSP.getInt("total", 0);
            double BMRLeft = BMR - total;

            if(BMRLeft < 0){
                caloriesLeft.setText("0");
                noti.setText("สารอาหารเกิน⚠");
                noti.setTextColor(Color.RED);
                toNoti += "=cal," + total + "," + BMRString +",Y";
            } else {
                DecimalFormat dFormat = new DecimalFormat("0.#");
                String formattedValue2 =  dFormat.format(BMRLeft);
                caloriesLeft.setText(formattedValue2 + "");
                toNoti += "=cal," + total + "," + BMRString +",N";
            }

            for(int i = 0 ; i < this.getDisease.length(); i++){
                //ถ้าเป็นโรคเบาหวาน
                if(this.getDisease.charAt(i) == '1'){
                    this.diabetes = true;
                }
                //ถ้าเป็นโรคไตเรื้อรัง
                if(this.getDisease.charAt(i) == '4'){
                    this.kidney = true;
                }
            }
            setNotificationValues(diabetes, kidney);
        } else {
            caloriesEditor.putInt("total", 0);
            caloriesEditor.putFloat("totalFat", 0);
            caloriesEditor.putFloat("totalProt", 0);
            caloriesEditor.commit();
            caloriesLeft.setText(BMRString);
            myDate = today_String;
        }

    }

    public void setNotificationValues(boolean isDiabetes, boolean isKidney){

        caloriesSP = this.getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
        float totalFat = (float) caloriesSP.getFloat("totalFat", 0);
        float totalProt = (float) caloriesSP.getFloat("totalProt", 0);

        DecimalFormat dFormat = new DecimalFormat("0.#");

        if(isDiabetes || isKidney) {
            if(!isDiabetes){
                if(this.getGender.equalsIgnoreCase("m")){
                    this.idealWeight = getHeight  - 100;
                } else if (this.getGender.equalsIgnoreCase("f")){
                    this.idealWeight = getHeight  - 105;
                }
                float idealProtein = (float)this.idealWeight * 60/100;
                String formattedTotalProt =  dFormat.format(totalProt);
                String formattedIdealProt =  dFormat.format(idealProtein);
                toNoti += "=prot1,"+formattedTotalProt +","+formattedIdealProt;
                if(totalProt > idealProtein){
                    noti.setText("สารอาหารเกิน⚠");
                    noti.setTextColor(Color.RED);
                    toNoti+=",Y";
                } else {
                    toNoti+=",N";
                }
            } else if(isKidney){
                if(this.getGender.equalsIgnoreCase("m")){
                    this.idealWeight = getHeight  - 100;
                } else if (this.getGender.equalsIgnoreCase("f")){
                    this.idealWeight = getHeight  - 105;
                }
                float idealProtein = (float)this.idealWeight * 60/100;
                String formattedTotalProt =  dFormat.format(totalProt);
                String formattedIdealProt =  dFormat.format(idealProtein);
                toNoti += "=prot1,"+formattedTotalProt+","+formattedIdealProt;
                if(totalProt > idealProtein){
                    noti.setText("สารอาหารเกิน⚠");
                    noti.setTextColor(Color.RED);
                    toNoti+=",Y";
                } else {
                    toNoti+=",N";
                }
                float idealFat = Float.parseFloat(BMRString) * 35/100;
                float currentFatCal = totalFat * 9;
                String formattedIdCurrentFatCal =  dFormat.format(currentFatCal);
                String formattedIdealFat =  dFormat.format(idealFat);
                toNoti += "=fat,"+formattedIdCurrentFatCal+","+formattedIdealFat;
                if(currentFatCal > idealFat){
                    noti.setText("สารอาหารเกิน⚠");
                    noti.setTextColor(Color.RED);
                    toNoti+=",Y";
                } else {
                    toNoti+=",N";
                }
            } else {
                float idealProteinCal = Float.parseFloat(BMRString) * 20/100;
                float currentProteinCal = totalProt * 4;
                String formattedCurrentProtCal =  dFormat.format(currentProteinCal);
                String formattedIdealProtCal =  dFormat.format(idealProteinCal);
                toNoti += "=prot2,"+formattedCurrentProtCal+","+formattedIdealProtCal;
                if(currentProteinCal > idealProteinCal){
                    noti.setText("สารอาหารเกิน⚠");
                    noti.setTextColor(Color.RED);
                    toNoti+=",Y";
                } else {
                    toNoti+=",N";
                }
                float idealFat = Float.parseFloat(BMRString) * 35/100;
                float currentFatCal = totalFat * 9;
                String formattedIdCurrentFatCal =  dFormat.format(currentFatCal);
                String formattedIdealFat =  dFormat.format(idealFat);
                toNoti += "=fat,"+formattedIdCurrentFatCal+","+formattedIdealFat;
                if(currentFatCal > idealFat){
                    noti.setText("สารอาหารเกิน⚠");
                    noti.setTextColor(Color.RED);
                    toNoti+=",Y";
                } else {
                    toNoti+=",N";
                }
            }
        }
    }
}
