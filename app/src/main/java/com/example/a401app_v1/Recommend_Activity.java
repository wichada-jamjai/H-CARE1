package com.example.a401app_v1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a401app_v1.R;

public class  Recommend_Activity extends AppCompatActivity {
    private String menuNo;
    private Bundle extras;
    private LinearLayout layout;
    private TextView menuName, menuDes;
    private ImageView menuPic;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        extras = getIntent().getExtras();
        menuNo = extras.getString("menuNo");
        menuName = findViewById(R.id.menuName);
        menuDes = findViewById(R.id.menuDes);
        menuPic = findViewById(R.id.menuPic);

        switch (menuNo) {
            case "1": {
                menuName.setText("มัจฉาแต่งองค์ (สำหรับ 2 ท่าน)");
                menuDes.setText(R.string.menuFood1);
                menuPic.setImageResource(R.drawable.food01);
                break;
            }
            case "2": {
                menuName.setText("เถาเครือม้วนคำ (สำหรับ 2 ท่าน)");
                menuDes.setText(R.string.menuFood2);
                menuPic.setImageResource(R.drawable.food02);
                break;
            }
            case "3": {
                menuName.setText("บัวบกคลุกฝุ่น (สำหรับ 2 ท่าน)");
                menuDes.setText(R.string.menuFood3);
                menuPic.setImageResource(R.drawable.food03);
                break;
            }
            case "4": {
                menuName.setText("ข้าวผัดหมู");
                menuDes.setText(R.string.menuFood4);
                menuPic.setImageResource(R.drawable.food04);
                break;
            }
            case "5": {
                menuName.setText("ข้าวต้มไก่");
                menuDes.setText(R.string.menuFood5);
                menuPic.setImageResource(R.drawable.food05);
                break;
            }
            case "6": {
                menuName.setText("ยำไข่ขาว");
                menuDes.setText(R.string.menuFood6);
                menuPic.setImageResource(R.drawable.food06);
                break;
            }
        }

        back = (Button) findViewById(R.id.backBtn4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });


    }
}
