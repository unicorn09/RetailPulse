package com.unicorn.retailpulse.MultipleImge;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.unicorn.retailpulse.EuclidianDistance;
import com.unicorn.retailpulse.R;
import com.unicorn.retailpulse.TensorflowImage;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ImageFragmentPagerAdapter extends PagerAdapter {
    Context context;
    List<Bitmap> bitmaps=new ArrayList<>();
    LayoutInflater mLayoutInflater;
    private static final String MODEL_PATH = "model.tflite";
    private static final int INPUT_SIZE = 300;


    private static final int BATCH_SIZE = 1;
    private static final int PIXEL_SIZE = 3;


    private Executor executor = Executors.newSingleThreadExecutor();
    private int inputSize=INPUT_SIZE;

    private EuclidianDistance euclidianDistance;
    private Interpreter interpreter;

    ImageFragmentPagerAdapter(Context context, List<Bitmap> bitmaps){
        this.context=context;
        this.bitmaps=bitmaps;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        Log.e("Adapter", "getCount: "+bitmaps.size());
        return bitmaps.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        TextView textView=(TextView)itemView.findViewById(R.id.tv_pager_item);
        Log.e("Adapter", "instantiateItem: "+bitmaps.get(position).toString());
        try{
        interpreter=new Interpreter(loadModelFile(context.getAssets(),MODEL_PATH));

        ByteBuffer byteBuffer = convertBitmapToByteBuffer(bitmaps.get(position));
        float[][] result = new float[1][16];
        interpreter.run(byteBuffer, result);
        euclidianDistance=new EuclidianDistance(result);
        if(euclidianDistance.calculatedis(result)==0)
        {
            textView.setText("Rock");
            textView.setVisibility(View.VISIBLE);

        }
        else if(euclidianDistance.calculatedis(result)==1)
        {
            textView.setText("Paper");
            textView.setVisibility(View.VISIBLE);

        }
        else
        {
            textView.setText("Scissors");
            textView.setVisibility(View.VISIBLE);
        }
        Log.e("harsh", "onActivityResult: "+result[0][0]+" "+result[0][15] );
        imageView.setImageBitmap(bitmaps.get(position));
        textView.setVisibility(View.VISIBLE);
    } catch (IOException e) {
        e.printStackTrace();
    }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
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