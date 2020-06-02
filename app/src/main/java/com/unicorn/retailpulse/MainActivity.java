package com.unicorn.retailpulse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.unicorn.retailpulse.MultipleImge.MultipleImage;
import com.unicorn.retailpulse.SingleImage.SingleImage;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;
    //private static final String SHARED_PREFS_FILE = ;
    private Button btn_single;
private Button btn_multiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_single=findViewById(R.id.btn_mainpage1);
        btn_multiple=findViewById(R.id.btn_mainpage2);


        Data d=new Data(this);
        Double a[][]=d.getArray();
        for(int i=0;i<32;i++)
        {
            for(int j=0;j<16;j++)
            {
                Log.e(TAG, "onCreate: "+a[i][j]);
            }
            Log.e(TAG, "onCreate: line change" );
        }


        btn_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SingleImage.class));
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
