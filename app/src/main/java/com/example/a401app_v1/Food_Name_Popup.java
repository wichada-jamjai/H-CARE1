package com.example.a401app_v1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


public class Food_Name_Popup extends Activity {

    private Button nameOK;
    private EditText menuName;
    private SharedPreferences caloriesSP;
    private SharedPreferences.Editor caloriesEditor;
    Bundle extras;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_name);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;

        getWindow().setLayout((int)width*80/100, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

        extras = getIntent().getExtras();
        id = extras.getString("meal");

        menuName = (EditText) findViewById(R.id.menuNameEdt);
        menuName.addTextChangedListener(formTextWatcher);

        nameOK = (Button) findViewById(R.id.menuNameOK);
        nameOK.setEnabled(false);
        nameOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSP();
                Intent it = new Intent(getBaseContext(), Add_Ingredient_Activity.class);
                startActivity(it);
            }
        });

    }
    public void setSP (){

        caloriesSP = this.getSharedPreferences("CALORIESTODAY", Context.MODE_PRIVATE);
        caloriesEditor = caloriesSP.edit();

        if(id.equalsIgnoreCase("1")){
            caloriesEditor.putInt("breakfast", 1);
            caloriesEditor.putString("breakfastname", menuName.getText().toString());
            caloriesEditor.putString("latestMeal", "breakfast");
        }
        if(id.equalsIgnoreCase("2")){
            caloriesEditor.putInt("lunch", 1);
            caloriesEditor.putString("lunchname", menuName.getText().toString());
            caloriesEditor.putString("latestMeal", "lunch");
        }
        if(id.equalsIgnoreCase("3")){
            caloriesEditor.putInt("dinner", 1);
            caloriesEditor.putString("dinnername", menuName.getText().toString());
            caloriesEditor.putString("latestMeal", "dinner");
        }


        caloriesEditor.commit();

    }
    private TextWatcher formTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String foodName = menuName.getText().toString().trim();
            if(!foodName.isEmpty()){
                nameOK.setEnabled(true);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    public void RecommendMenu(View v){
        setSP();
        Intent intent = new Intent(v.getContext(), Add_Recommend.class);
        startActivityForResult(intent, 0);
    }
}
