package com.unicorn.retailpulse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private Button btnloadimage;
private static final int GALLERY_REQUEST =4 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnloadimage = (Button) findViewById(R.id.btn_mainpage);
        btnloadimage.setOnClickListener(this);

        }

    @Override
    public void onClick(View v) {
        if(v.findViewById(R.id.btn_mainpage)==btnloadimage)
        {
            btnloadimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //    Toast.makeText(EditProfileActivity.thi,"The image uploaded should not be more than 200kb",Toast.LENGTH_LONG).show();
                    try {
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST);
                        } else {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, GALLERY_REQUEST);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }}
            });
        }


        }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_REQUEST);

    }
}
