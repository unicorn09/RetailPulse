package com.unicorn.retailpulse.SingleImage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unicorn.retailpulse.EuclidianDistance;
import com.unicorn.retailpulse.R;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SingleImage extends AppCompatActivity implements View.OnClickListener {



    private Button btnloadimage;
    private static final int GALLERY_REQUEST =4 ;
    private ImageView imageselected;
    private TextView tv_output;
    private  String TAG="unicornlog";
    private Bitmap bitmap;
    private Interpreter interpreter;
    float[][] outputval;

    private static final String MODEL_PATH = "model.tflite";
    private static final int INPUT_SIZE = 300;


    private static final int BATCH_SIZE = 1;
    private static final int PIXEL_SIZE = 3;


    private Executor executor = Executors.newSingleThreadExecutor();
    private int inputSize=INPUT_SIZE;

    private EuclidianDistance euclidianDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);



        btnloadimage = (Button) findViewById(R.id.btn_mainpage);
        imageselected = (ImageView) findViewById(R.id.imgselected);
        tv_output = (TextView) findViewById(R.id.tv_output);

        btnloadimage.setOnClickListener(this);
        outputval=new float[1][16];

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



                     bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
                     interpreter=new Interpreter(loadModelFile(getAssets(),MODEL_PATH));

                    ByteBuffer byteBuffer = convertBitmapToByteBuffer(bitmap);
                        float[][] result = new float[1][16];
                        interpreter.run(byteBuffer, result);
                        euclidianDistance=new EuclidianDistance(this,result);
                        if(euclidianDistance.calculatedis(result)==0)
                        {
                            tv_output.setText("Rock");
                            tv_output.setVisibility(View.VISIBLE);

                        }
                        else if(euclidianDistance.calculatedis(result)==1)
                        {
                            tv_output.setText("Paper");
                            tv_output.setVisibility(View.VISIBLE);

                        }
                        else
                        {
                            tv_output.setText("Scissors");
                            tv_output.setVisibility(View.VISIBLE);
                        }
                   Log.e(TAG, "onActivityResult: "+result[0][0]+" "+result[0][15] );
                    imageselected.setImageBitmap(bitmap);
                    tv_output.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer;

        byteBuffer = ByteBuffer.allocateDirect(4*BATCH_SIZE * 300 * 300 * PIXEL_SIZE);


        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[inputSize * inputSize];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < inputSize; ++i) {
            for (int j = 0; j < inputSize; ++j) {
                final int val = intValues[pixel++];

                byteBuffer.putFloat(((val>> 16) & 0xFF) / 255.f);
                byteBuffer.putFloat(((val>> 8) & 0xFF) / 255.f);
                byteBuffer.putFloat((val & 0xFF) / 255.f);

                }
            }

        return byteBuffer;
    }


}