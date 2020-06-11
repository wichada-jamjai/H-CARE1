package com.example.a401app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit_Info_Activity extends AppCompatActivity {
    private SharedPreferences userinfo;
    private SharedPreferences.Editor editor;
    private EditText edtWeight, edtHeight, edtNumber;
    private Button edtWBtn, edtHBtn, edtNBtn, cancel, submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);

        userinfo= this.getSharedPreferences("USERINFO", Context.MODE_PRIVATE);


        edtWeight = findViewById(R.id.editWeight2);
        edtHeight = findViewById(R.id.editHeight2);
        //edtNumber  = findViewById(R.id.editNumber2);

        edtWeight.setEnabled(false);
        edtHeight.setEnabled(false);
        //edtNumber.setEnabled(false);

        edtWBtn = findViewById(R.id.editWeight);
        edtHBtn  =  findViewById(R.id.editHeight);
        //edtNBtn  =  findViewById(R.id.editNumber);

        edtWeight.setText(userinfo.getInt("weight", 0)+"");
        edtHeight.setText(userinfo.getInt("height", 0)+"");
        //edtNumber.setText((String)userinfo.getString("phone", null)+"");

        //edtNumber.addTextChangedListener(formTextWatcher);
        edtWeight.addTextChangedListener(formTextWatcher);
        edtHeight.addTextChangedListener(formTextWatcher);

        edtWBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtWeight.isEnabled()){
                    edtWeight.setEnabled(false);
                } else {
                    edtWeight.setEnabled(true);
                }
                setSubmit();
            }
        });
        edtHBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtHeight.isEnabled()){
                    edtHeight.setEnabled(false);
                } else {
                    edtHeight.setEnabled(true);
                }
                setSubmit();
            }
        });
//        edtNBtn .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(edtNumber.isEnabled()){
//                    edtNumber.setEnabled(false);
//                } else {
//                    edtNumber.setEnabled(true);
//                }
//                setSubmit();
//            }
//        });

        cancel = findViewById(R.id.cancelEdit);
        submit = findViewById(R.id.submitEdit);
        submit.setEnabled(false);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = userinfo.edit();
                editor.putInt("weight", Integer.parseInt(edtWeight.getText().toString()));
                editor.putInt("height", Integer.parseInt(edtHeight.getText().toString()));
                //editor.putString("phone",edtNumber.getText().toString());
                editor.commit();
                Intent it = new Intent(getBaseContext(), Mainpage_Handling_Activity.class);
                startActivity(it);
                finish();
            }
        });
    }

    private TextWatcher formTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           //String phone = edtNumber.getText().toString().trim();
            String weight = edtWeight.getText().toString().trim();
            String height = edtHeight.getText().toString().trim();

            if(!weight.isEmpty() && !height.isEmpty()){
                submit.setEnabled(true);
            }

        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    public void setSubmit(){
        if(edtWeight.isEnabled() || edtHeight.isEnabled()){
            submit.setEnabled(true);
        }
    }

}
