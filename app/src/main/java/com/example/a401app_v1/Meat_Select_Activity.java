package com.example.a401app_v1;

import android.content.Intent;
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

public class Meat_Select_Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reff;
    private TextView text;
    private LinearLayout amountEdt , se_meat;
    private Button btnAdd1 , btnAdd2,btnAdd3 , btnAdd4,addCal;
    private RadioButton rdo1, rdo2;
    private  ArrayList<String> arrListName = new ArrayList<String>();
    private  ArrayList<String> arrListCal = new ArrayList<String>();
    private  ArrayList<String> arrListPro = new ArrayList<String>();
    private  ArrayList<String> arrListFat = new ArrayList<String>();
    String req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_meat);
        readData();
        getValue();
        addbu();
    }
    public void addbu(){
        View includedLayout = findViewById(R.id.amount);
        final TextView nameOfFood = (TextView) includedLayout.findViewById(R.id.Name_amount);
        amountEdt = (LinearLayout)includedLayout.findViewById(R.id.amountEdt);
        addCal = (Button)includedLayout.findViewById(R.id.addcal);
        rdo1 = (RadioButton) includedLayout.findViewById(R.id.rdo1);
        rdo1.setChecked(true);
        se_meat = findViewById(R.id.se_meat);
        Button[]bu = new Button[4];
        bu[0]=(Button)findViewById(R.id.addbu1);
        bu[1]=(Button)findViewById(R.id.addbu2);
        bu[2]=(Button)findViewById(R.id.addbu3);
        bu[3]=(Button)findViewById(R.id.addbu4);
        for(int i=0;i<4;i++)
        {
            final int finalI = i;
            bu[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    se_meat.setVisibility(View.GONE);
                    amountEdt.setVisibility(View.VISIBLE);
                    nameOfFood.setText("ระบุปริมาณ"+arrListName.get(finalI));
                    setRadio();
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
        final Button[]bu = new Button[4];
        bu[0]=(Button)findViewById(R.id.addbu1);
        bu[1]=(Button)findViewById(R.id.addbu2);
        bu[2]=(Button)findViewById(R.id.addbu3);
        bu[3]=(Button)findViewById(R.id.addbu4);
        for(int i = 0 ; i< 4 ; i++) {
            reff = FirebaseDatabase.getInstance().getReference().child("meat").child(i+"");
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
        /*for(int i=0;i<4;i++)
        {
            bu[0].setText(arrListName.get(0));
        }*/

        /*String data = " ";
        StringBuffer sbuffer = new StringBuffer();
        InputStream is = this.getResources().openRawResource(R.raw.meat);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Button[]bu = new Button[4];
        if (is != null) {
            try {
                while ((data = reader.readLine()) != null) {
                    sbuffer.append(data + "\n");
                    String textName[] = data.toString().split(" ");
                    arrListName.add(textName[0]);
                    arrListCal.add(textName[1]);
                }
                bu[0]=(Button)findViewById(R.id.addbu1);
                bu[1]=(Button)findViewById(R.id.addbu2);
                bu[2]=(Button)findViewById(R.id.addbu3);
                bu[3]=(Button)findViewById(R.id.addbu4);

                for(int i=0;i<arrListName.size();i++)
                {
                    bu[i].setText(arrListName.get(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
   public void calculator(int index){
        Intent i = new Intent(getApplicationContext(), Add_Ingredient_Activity.class);
       View includedLayout = findViewById(R.id.amount);
       rdo1 = (RadioButton) includedLayout.findViewById(R.id.rdo1);
       rdo2 = (RadioButton) includedLayout.findViewById(R.id.rdo2);
       addCal = (Button)includedLayout.findViewById(R.id.addcal);
       EditText _edt1 = (EditText )includedLayout.findViewById(R.id.edt1);
       EditText _edt2 = (EditText )includedLayout.findViewById(R.id.edt2);
       boolean digitsOnly1 = TextUtils.isDigitsOnly(_edt1.getText());
       boolean digitsOnly2 = TextUtils.isDigitsOnly(_edt2.getText());

                if(rdo1.isChecked()&&rdo2.isChecked()==false){
                    if (_edt1.getText().length()==0){
                        Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลให้ครบถ้วน",Toast.LENGTH_SHORT).show();
                    }else if(digitsOnly1==false) {
                        Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลเป็นตัวเลข",Toast.LENGTH_SHORT).show();
                    }
                    else if (digitsOnly1){
                        i.putExtra("rqCode2", req);
                        i.putExtra("msg1", cupCalculate(_edt1.getText().toString(), index));
                        i.putExtra("msg2", arrListName.get(index).toString());
                        i.putExtra("msg3",cupFatCalculate(_edt1.getText().toString(),index));
                        i.putExtra("msg4",cupProCalculate(_edt1.getText().toString(),index));
                        Meat_Select_Activity.this.setResult(Add_Ingredient_Activity.RESULT_OK, i);
                        Meat_Select_Activity.this.finish();
                        startActivity(i);
                    }
                }else if(rdo2.isChecked()&&rdo1.isChecked()==false&&_edt1.getText()!=null){
                    if (_edt2.getText().length()==0){
                        Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลให้ครบถ้วน",Toast.LENGTH_SHORT).show();
                    }else if(digitsOnly2==false) {
                        Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลเป็นตัวเลข",Toast.LENGTH_SHORT).show();
                    }
                    else if (digitsOnly2){
                        i.putExtra("rqCode2",req);
                        i.putExtra("msg1",gramCalculate(_edt2.getText().toString(),index));
                        i.putExtra("msg2",arrListName.get(index).toString());
                        i.putExtra("msg3",gramFatCalculate(_edt2.getText().toString(),index));
                        i.putExtra("msg4",gramProCalculate(_edt2.getText().toString(),index));
                        Meat_Select_Activity.this.setResult(Add_Ingredient_Activity.RESULT_OK,i);
                        Meat_Select_Activity.this.finish();
                        startActivity(i);
                    }
                }
    }
    /***********************************คำนวณแบบถ้วย***********************************/
    public String cupCalculate(String num ,int index) {
        int numCup = Integer.parseInt(num);
        int calCup = numCup * 240;
        float cal = calCup * (Float.parseFloat(arrListCal.get(index)) / 100);
        String formattedValue = String.format("%.3f", cal);
        return formattedValue;
    }
    public String cupFatCalculate(String num ,int index){
        int numCup = Integer.parseInt(num);
        float calCup = numCup*240;
        calCup = calCup*(Float.parseFloat(arrListFat.get(index)) / 100);
        return calCup + "";
    }
    public String cupProCalculate(String num ,int index){
        int numCup = Integer.parseInt(num);
        float calCup = numCup*240;
        calCup = calCup*(Float.parseFloat(arrListPro.get(index)) / 100);
        return calCup + "";
    }
    /***********************************คำนวณแบบกรัม**************************************/
    public String gramCalculate(String num, int index) {
        int numGram = Integer.parseInt(num);
        float cal =  numGram * Float.parseFloat(arrListCal.get(index)) / 100;
        String formattedValue = String.format("%.3f", cal);
        return formattedValue;
    }
    public String gramFatCalculate(String num ,int index){
        float fat = Float.parseFloat(num);
        fat = fat*(Float.parseFloat(arrListFat.get(index)) / 100);
        String formattedValue = String.format("%.3f", fat);
        return formattedValue;
    }
    public String gramProCalculate(String num ,int index){
        float pro = Float.parseFloat(num);
        pro  =  pro *(Float.parseFloat(arrListPro.get(index)) / 100);
        String formattedValue = String.format("%.3f", pro);
        return  formattedValue;
    }

    /***************************************************************************************/
    public void getValue(){
        Intent i = getIntent();
        req = i.getStringExtra("rqCode1");

    }
public void setRadio(){
    View includedLayout = findViewById(R.id.amount);
    final EditText edt1 = (EditText) includedLayout.findViewById(R.id.edt1);
    final EditText edt2 = (EditText )includedLayout.findViewById(R.id.edt2);
    rdo1 = (RadioButton) includedLayout.findViewById(R.id.rdo1);
    rdo2 = (RadioButton) includedLayout.findViewById(R.id.rdo2);
    edt2.setEnabled(false);
    rdo1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rdo1.setChecked(true);
            rdo2.setChecked(false);
            edt2.setEnabled(false);
            edt2.setText("");
            edt1.setEnabled(true);
        }
    });
    rdo2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rdo2.setChecked(true);
            rdo1.setChecked(false);
            edt1.setEnabled(false);
            edt1.setText("");
            edt2.setEnabled(true);

        }
    });
}
}
