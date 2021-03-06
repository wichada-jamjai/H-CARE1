package com.example.a401app_v1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fruit_Select_Activity extends AppCompatActivity {
    private TextView text;
    private LinearLayout amountEdt,amountCup,se_fruit;
    private Button addCal;
    private RadioButton rdo1, rdo2;
    private ArrayList<String> arrListName = new ArrayList<String>();
    private ArrayList<String> arrListCal = new ArrayList<String>();
    private ArrayList<String> arrListFat = new ArrayList<String>();
    private SharedPreferences nutrientsSP;
    private SharedPreferences.Editor nutrientsEditor;
    private ArrayList<String> arrListPro = new ArrayList<String>();
    String req;
    DatabaseReference reff;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_fruit);
        readData();
        getValue();
        addbu();
    }
    public void addbu() {
        View includedLayout = findViewById(R.id.amount);
        final TextView nameOfFood = (TextView) includedLayout.findViewById(R.id.Name_amount);
        amountEdt = (LinearLayout) includedLayout.findViewById(R.id.amountEdt);
        amountCup = (LinearLayout) includedLayout.findViewById(R.id.cup);
        addCal = (Button) includedLayout.findViewById(R.id.addcal);
        rdo2 = (RadioButton) includedLayout.findViewById(R.id.rdo2);
        rdo2.setChecked(true);
        se_fruit = findViewById(R.id.se_fruit);
        Button[] bu = new Button[19];
        bu[0] = (Button) findViewById(R.id.addbu1);
        bu[1] = (Button) findViewById(R.id.addbu2);
        bu[2] = (Button) findViewById(R.id.addbu3);
        bu[3] = (Button) findViewById(R.id.addbu4);
        bu[4] = (Button) findViewById(R.id.addbu5);
        bu[5] = (Button) findViewById(R.id.addbu6);
        bu[6] = (Button) findViewById(R.id.addbu7);
        bu[7] = (Button) findViewById(R.id.addbu8);
        bu[8] = (Button) findViewById(R.id.addbu9);
        bu[9] = (Button) findViewById(R.id.addbu10);
        bu[10] = (Button) findViewById(R.id.addbu11);
        bu[11] = (Button) findViewById(R.id.addbu12);
        bu[12] = (Button) findViewById(R.id.addbu13);
        bu[13] = (Button) findViewById(R.id.addbu14);
        bu[14] = (Button) findViewById(R.id.addbu15);
        bu[15] = (Button) findViewById(R.id.addbu16);
        bu[16] = (Button) findViewById(R.id.addbu17);
        bu[17] = (Button) findViewById(R.id.addbu18);
        bu[18] = (Button) findViewById(R.id.addbu19);
        for(int i=0;i<19;i++)
        {
            final int finalI = i;
            bu[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    se_fruit.setVisibility(View.GONE);
                    amountEdt.setVisibility(View.VISIBLE);
                    nameOfFood.setText("ระบุปริมาณ"+arrListName.get(finalI));
                    amountCup.setVisibility(View.GONE);
                    addCal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            calculator(finalI);
                        }});
                }
            });
        }
    }
    /*wichada*/
    public void readData() {
        final Button[] bu = new Button[19];
        bu[0] = (Button) findViewById(R.id.addbu1);
        bu[1] = (Button) findViewById(R.id.addbu2);
        bu[2] = (Button) findViewById(R.id.addbu3);
        bu[3] = (Button) findViewById(R.id.addbu4);
        bu[4] = (Button) findViewById(R.id.addbu5);
        bu[5] = (Button) findViewById(R.id.addbu6);
        bu[6] = (Button) findViewById(R.id.addbu7);
        bu[7] = (Button) findViewById(R.id.addbu8);
        bu[8] = (Button) findViewById(R.id.addbu9);
        bu[9] = (Button) findViewById(R.id.addbu10);
        bu[10] = (Button) findViewById(R.id.addbu11);
        bu[11] = (Button) findViewById(R.id.addbu12);
        bu[12] = (Button) findViewById(R.id.addbu13);
        bu[13] = (Button) findViewById(R.id.addbu14);
        bu[14] = (Button) findViewById(R.id.addbu15);
        bu[15] = (Button) findViewById(R.id.addbu16);
        bu[16] = (Button) findViewById(R.id.addbu17);
        bu[17] = (Button) findViewById(R.id.addbu18);
        bu[18] = (Button) findViewById(R.id.addbu19);
        for(int i = 0 ; i< 19 ; i++) {
            reff = FirebaseDatabase.getInstance().getReference().child("Fruit").child(i + "");
            final int finalI = i;
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String cal = dataSnapshot.child("cal").getValue().toString();
                    String protein = dataSnapshot.child("protein").getValue().toString();
                    String fat = dataSnapshot.child("fat").getValue().toString();

                    bu[finalI].setText(name);
                    arrListName.add(name);
                    arrListCal.add(cal);
                    arrListPro.add(protein);
                    arrListFat.add(fat);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    public void calculator(int index) {
        Intent i = new Intent(getApplicationContext(), Add_Ingredient_Activity.class);
        View includedLayout = findViewById(R.id.amount);
        rdo1 = (RadioButton) includedLayout.findViewById(R.id.rdo1);
        rdo2 = (RadioButton) includedLayout.findViewById(R.id.rdo2);
        addCal = (Button) includedLayout.findViewById(R.id.addcal);
        EditText _edt1 = (EditText) includedLayout.findViewById(R.id.edt1);
        EditText _edt2 = (EditText) includedLayout.findViewById(R.id.edt2);
        boolean digitsOnly2 = TextUtils.isDigitsOnly(_edt2.getText());
        if (rdo2.isChecked()) {
            if (_edt2.getText().length()==0){
                Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลให้ครบถ้วน",Toast.LENGTH_SHORT).show();
            }else if(digitsOnly2==false) {
                Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลเป็นตัวเลข",Toast.LENGTH_SHORT).show();
            }
            else if (digitsOnly2){
                    i.putExtra("rqCode2", req);
                    i.putExtra("msg1", gramCalculate(_edt2.getText().toString(), index)); //แคลอรี่
                    i.putExtra("msg2", arrListName.get(index)); //ชื่อ
                    i.putExtra("msg3", FatCalculate(_edt2.getText().toString(),index) +""); //ไขมัน
                    i.putExtra("msg4", ProteinCalculate(_edt2.getText().toString(),index) +""); //โปรตีน
                 //Toast.makeText(getApplicationContext(),(Float.parseFloat(arrListFat.get(index)))+"",Toast.LENGTH_SHORT).show();
                Fruit_Select_Activity.this.setResult(Add_Ingredient_Activity.RESULT_OK, i);
                Fruit_Select_Activity.this.finish();
                startActivity(i);
            }
        }
    }
    public String gramCalculate(String num, int index) {
        int numGram = Integer.parseInt(num);
        float cal =  numGram * Float.parseFloat(arrListCal.get(index)) / 100;
        String formattedValue = String.format("%.3f", cal);
        return formattedValue;
    }
    public String FatCalculate(String num, int index) {
        float valueOfFat = Float.parseFloat(num) * (Float.parseFloat(arrListFat.get(index)) / 100) ;
        String formattedValue = String.format("%.3f", valueOfFat);
        return formattedValue;
    }
    public String ProteinCalculate(String num, int index) {
        float valueOfFat = Float.parseFloat(num) * (Float.parseFloat(arrListPro.get(index)) / 100) ;
        String formattedValue = String.format("%.3f", valueOfFat);
        return formattedValue;
    }
    public void getValue() {
        Intent i = getIntent();
        req = i.getStringExtra("rqCode1");
    }
}
