package com.example.a401app_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

public class Info_Activity extends AppCompatActivity {

    private EditText editName1, editWeight1, editHeight1, editNumber1;

    private DatePicker datePick;
    private ImageButton buttonNext2;
    private String gender,inputName, inputWeight, inputHeight, inputPhone, inputBirthDate, toSend;
    private int age, weight, height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_register02);

        Bundle bundle = getIntent().getExtras();
        gender = bundle.getString("stuffs");


        editName1 = (EditText) findViewById(R.id.editName1);
        editWeight1 = (EditText)findViewById(R.id.editWeight1);
        editHeight1 = (EditText)findViewById(R.id.editHeight1);
        editNumber1 = (EditText) findViewById(R.id.editNumber1);
        datePick = (DatePicker)findViewById(R.id.datePicker1);

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day);

        editName1.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        editWeight1.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        editHeight1.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        editNumber1.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);


        editName1.addTextChangedListener(formTextWatcher);
        editNumber1.addTextChangedListener(formTextWatcher);
        editWeight1.addTextChangedListener(formTextWatcher);
        editHeight1.addTextChangedListener(formTextWatcher);


        buttonNext2 = findViewById(R.id.buttonNext2);
        buttonNext2.setEnabled(false);
        buttonNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePick.getDayOfMonth();
                int month = datePick.getMonth();
                int year =  datePick.getYear();
                inputBirthDate = year + "-" + month + "-" + day;
                Intent intent = new Intent(Info_Activity.this, Disease_Activity.class);
                Bundle bundle = new Bundle();
                inputName = "="+ editName1.getText().toString().trim();
                inputPhone =  "="+editNumber1.getText().toString().trim();
                inputWeight = "="+editWeight1.getText().toString().trim();
                inputHeight = "="+ editHeight1.getText().toString().trim();
                inputBirthDate = "=" + inputBirthDate;
                toSend = gender + inputName + inputBirthDate + inputWeight +  inputHeight+ inputPhone + "=";
                bundle.putString("stuffs", toSend);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    private TextWatcher formTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String name = editName1.getText().toString().trim();
            String phone = editNumber1.getText().toString().trim();
            String weight = editWeight1.getText().toString().trim();
            String height = editHeight1.getText().toString().trim();

            if(!name.isEmpty()  && !phone.isEmpty()
                    && !weight.isEmpty() && !height.isEmpty() ){
                buttonNext2.setEnabled(true);
                buttonNext2.setBackgroundResource(R.drawable.button_noclick_next);
            }

        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


}
