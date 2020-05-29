package com.unicorn.retailpulse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class SingleImage extends AppCompatActivity implements View.OnClickListener {
private Button btnloadimage;
private static final int GALLERY_REQUEST =4 ;
private ImageView imageselected;
private TextView tv_output;
private  String TAG="unicornlog";
private Interpreter tflite;
Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);

        btnloadimage = (Button) findViewById(R.id.btn_mainpage);
        imageselected = (ImageView) findViewById(R.id.imgselected);
        tv_output = (TextView) findViewById(R.id.tv_output);

        btnloadimage.setOnClickListener(this);
        float[] outputval=new float[16];
        try {
            tflite = new Interpreter(loadModelFile());
            tflite.run(bitmap,outputval);
            Log.e(TAG, "onCreate: "+outputval[0]);
        }
        catch(Exception ex)
        {
            Log.e(TAG, "onCreate: "+ex.toString());
        }
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
                     bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
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



    public void doInference(Bitmap bitmap)
    {

        Log.e(TAG, "doInference: +");
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

}
