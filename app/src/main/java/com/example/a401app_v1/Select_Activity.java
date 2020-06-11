package com.example.a401app_v1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Select_Activity extends AppCompatActivity {

    private int currentApiVersion;
    String req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_cataglory);
        currentApiVersion = Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);
            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });

        }
        getValue();
    }

    public void selectType(View v){

        switch (v.getId()){
            case R.id.meatImg:
                Intent intent = new Intent(v.getContext(), Meat_Select_Activity.class);
                intent.putExtra("rqCode1" ,req);
                startActivityForResult(intent, 0);
                break;
            case R.id.vegImg:
                Intent intent2 = new Intent(v.getContext(), Veggi_Select_Activity.class);
                intent2.putExtra("rqCode1" ,req);
                startActivityForResult(intent2, 0);
                break;
            case R.id.fruitImg:
                Intent intent3 = new Intent(v.getContext(), Fruit_Select_Activity.class);
                intent3.putExtra("rqCode1" ,req);
                startActivityForResult(intent3, 0);
                break;
            case R.id.spcieImg:
                Intent intent4 = new Intent(v.getContext(), Spices_Select_Activity.class);
                intent4.putExtra("rqCode1" ,req);
                startActivityForResult(intent4, 0);
                break;
            case R.id.milkImg:
                Intent intent5 = new Intent(v.getContext(), Milk_Select_Activity.class);
                intent5.putExtra("rqCode1" ,req);
                startActivityForResult(intent5, 0);
                break;
            case R.id.riceImg:
                Intent intent6 = new Intent(v.getContext(), Rice_Select_Activity.class);
                intent6.putExtra("rqCode1" ,req);
                startActivityForResult(intent6, 0);
                break;
                   }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                int result = data.getIntExtra("result", 0);

            }
        }

    }


    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }
    public void getValue(){
        Intent i = getIntent();
        req = i.getStringExtra("rqCode");
    }

}
