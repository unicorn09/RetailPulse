package com.unicorn.retailpulse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class SingleImage extends AppCompatActivity implements View.OnClickListener {
private Button btnloadimage;
private static final int GALLERY_REQUEST =4 ;
private ImageView imageselected;
private TextView tv_output;
private  String TAG="unicornlog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);

        btnloadimage = (Button) findViewById(R.id.btn_mainpage);
        imageselected=(ImageView)findViewById(R.id.imgselected);
        tv_output=(TextView)findViewById(R.id.tv_output);

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
                    try {
                        if (ActivityCompat.checkSelfPermission(SingleImage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(SingleImage.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST);
                        } else {
                            openImage();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }}
            });
        }
        }
        private void openImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST&&data!=null) {

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                     Log.d(TAG, String.valueOf(bitmap));
                    ImageView imageView = findViewById(R.id.imgselected);
                    imageselected.setImageBitmap(bitmap);
                    tv_output.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
