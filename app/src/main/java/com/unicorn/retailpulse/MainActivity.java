package com.unicorn.retailpulse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.unicorn.retailpulse.MultipleImge.MultipleImage;

public class MainActivity extends AppCompatActivity {
private Button btn_single;
private Button btn_multiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_single=findViewById(R.id.btn_mainpage1);
        btn_multiple=findViewById(R.id.btn_mainpage2);




        btn_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SingleImage.class));
            }
        });
        btn_multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MultipleImage.class));
            }
        });
    }
}
