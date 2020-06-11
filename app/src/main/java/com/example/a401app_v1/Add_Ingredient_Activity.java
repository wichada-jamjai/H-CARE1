package com.example.a401app_v1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Add_Ingredient_Activity extends AppCompatActivity {
    private Button  finishBtn ,add01, add02, add03, add04, add05, add06, add07 ,add08,add09,add10, newBtn;
    private String _Cal,_Name,req ,_protein,_fat;
    private float totalCal;
    private float totalFat, totalProtein;
    private  TextView  detail_add;
    SharedPreferences  shared;
    SharedPreferences.Editor editor;
    private SharedPreferences caloriesSP;
    private SharedPreferences.Editor caloriesEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient);
        shared = getApplicationContext().getSharedPreferences("mytext", Context.MODE_PRIVATE);
        finishBtn = findViewById(R.id.finishBtn);
        detail_add = findViewById(R.id.detail);
        getValueActivity();
        this.addBtn();
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caloriesSP = getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
                caloriesEditor = caloriesSP.edit();
                int currentTotal = caloriesSP.getInt("total", 0);
                caloriesEditor.putInt("total", (int)getTotalCal()+currentTotal);
                caloriesEditor.commit();
                Intent it = new Intent(getBaseContext(), Add_Menu_Activity.class);
                startActivity(it);
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode,intent);
        TextView tw1 = (TextView)findViewById(R.id.add01);
        TextView tw2 = (TextView)findViewById(R.id.add02);
        TextView tw3 = (TextView)findViewById(R.id.add03);
        TextView t = (TextView)findViewById(R.id.cal1);
        _Cal = intent.getStringExtra("msg1");
        _Name = intent.getStringExtra("msg2");
        tw1.setText(_Cal+"");

    }

    public void addBtn(){
        /******************************add on click Listener on button****************************************/
        Button[]bu = new Button[10];
        bu[0] = findViewById(R.id.add01);
        bu[1] = findViewById(R.id.add02);
        bu[2] = findViewById(R.id.add03);
        bu[3] = findViewById(R.id.add04);
        bu[4] = findViewById(R.id.add05);
        bu[5] = findViewById(R.id.add06);
        bu[6] = findViewById(R.id.add07);
        bu[7] = findViewById(R.id.add08);
        bu[8] = findViewById(R.id.add09);
        bu[9] = findViewById(R.id.add10);
        for(int i=0;i<9;i++)
        {
            final int finalI = i;
            bu[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*************************************go to next page******************************************/
                    Intent intent = new Intent(Add_Ingredient_Activity.this, Select_Activity.class);
                    intent.putExtra("rqCode",(finalI +1)+"");
                    Add_Ingredient_Activity.this.startActivityForResult(intent, 0);
                }
            });
        }
    }
    public void getValueActivity(){
        /*********************************รับค่าปริมาณวัตุดิบ + ชื่อ + จำนวนแคล*************************************/
        Intent i = getIntent();
        req = i.getStringExtra("rqCode2");
        _Cal = i.getStringExtra("msg1");
        _Name = i.getStringExtra("msg2");
        _fat = i.getStringExtra("msg3");
        _protein = i.getStringExtra("msg4");
      //  Toast.makeText(getApplicationContext(),"Fat: " + (_fat)+"    " + "Protein: "+_protein,Toast.LENGTH_SHORT).show();
        String rqCode = i.getStringExtra("rqCode");
        TextView t = (TextView)findViewById(R.id.cal1);

        if(_Cal == null || _Name ==null){
            //do some thing
        } else {
            totalCal =Float.parseFloat(_Cal);
            setTotalCal(totalCal);
            sharedPreference(req,_Name,totalCal);
            setVisbilityButton(Integer.parseInt(req));

            totalFat = Float.parseFloat(_fat);
            setTotalFat(totalFat);

            totalProtein = Float.parseFloat(_protein);
            setTotalProtein(totalProtein);
        }
    }
    public void setTotalCal(float total){
        String formattedValue = String.format("%.3f", total);
        this.totalCal = total;
    }
    public void setTotalFat(float fat) {
        caloriesSP = getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
        caloriesEditor = caloriesSP.edit();
        float currentFat = caloriesSP.getFloat("totalFat", 0);
        currentFat += fat;
        caloriesEditor.putFloat("totalFat", currentFat);
        caloriesEditor.commit();
    };
    public void setTotalProtein(float prot) {
        caloriesSP = getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
        caloriesEditor = caloriesSP.edit();
        float currentProtein = caloriesSP.getFloat("totalProt", 0);
        currentProtein += prot;
        caloriesEditor.putFloat("totalProt", currentProtein);
        caloriesEditor.commit();
    };
    public float getTotalCal(){
        return totalCal;
    }
    public  void sharedPreference(String i , String name,float totalcal){
        // Save SharedPreferences
        String formattedValue = String.format("%.3f", totalcal);
        editor = shared.edit();
        editor.putString("name"+i,name+" ");
        editor.putFloat("cal"+i,Float.parseFloat(formattedValue));
        editor.putBoolean("booleanKey", true);
        editor.commit();
    }
    public String getNameAmount(String index){
        //getName
        String nameofbtn = shared.getString("name" + index, "not found!");
        boolean booleanValue = shared.getBoolean("booleanKey", false);
        return nameofbtn;
    }
    public  float getCalAmount(String index){
        //getCal
        float calofbtn = shared.getFloat("cal" + index, 0);
        boolean booleanValue = shared.getBoolean("booleanKey", false);
        return calofbtn;
    }
    public  void setVisbilityButton(int numberBtn){
        TextView t = (TextView)findViewById(R.id.cal1);
        float total = 0;
        Button[]bu = new Button[10];
        bu[0] = findViewById(R.id.add01);
        bu[1] = findViewById(R.id.add02);
        bu[2] = findViewById(R.id.add03);
        bu[3] = findViewById(R.id.add04);
        bu[4] = findViewById(R.id.add05);
        bu[5] = findViewById(R.id.add06);
        bu[6] = findViewById(R.id.add07);
        bu[7] = findViewById(R.id.add08);
        bu[8] = findViewById(R.id.add09);
        bu[9] = findViewById(R.id.add10);
        /*********************  ทำปุ่มแสดง  *************************/
        for(int i=0;i<numberBtn+1;i++) {
            if(i>0){
                bu[i].setVisibility(View.VISIBLE);
                //bu[i-1].setEnabled(false);
                detail_add.setVisibility(View.GONE);
                finishBtn.setEnabled(true);
            }else{
                detail_add.setVisibility(View.GONE);
                bu[i].setVisibility(View.VISIBLE);
                finishBtn.setEnabled(true);
            }
        }
        /********************แสดงปริมาณวัตถุดิบ + คำนวณ ********************/
        for(int i=0;i<numberBtn;i++) {
            bu[i].setText(getNameAmount((i+1)+"")+" : "+getCalAmount((i+1)+"")+"  แคลอรี่");
            total = total + getCalAmount((i+1)+"");
            setTotalCal(total);
        }
        t.setText(total +"  แคลอรี่");
    }
}
