package com.unicorn.retailpulse.MultipleImge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.unicorn.retailpulse.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MultipleImage extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 404;
    RecyclerView recyclerView;
    private Button btn_multichoose;
    int num_columns = 3;
    private  ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    private ViewPager viewPager;
    private Uri uri;
    Intent data1;
    Uri[] bitmaps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_image);

        btn_multichoose=(Button)findViewById(R.id.btn_multiplepage);



        btn_multichoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        viewPager = (ViewPager) findViewById(R.id.vp_multiplepage);

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Multiple Pictures"), GALLERY_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST&&resultCode == Activity.RESULT_OK) {
           // final ImageView imageView=findViewById(R.id.img_multi);
            final List<Bitmap> bitmaps=new ArrayList<>();
            ClipData clipData=data.getClipData();

            if(clipData!=null)
            {
                for(int i=0;i<clipData.getItemCount();i++){
                    Uri imageuri=clipData.getItemAt(i).getUri();
                    try{
                        InputStream is=getContentResolver().openInputStream(imageuri);
                        Bitmap bitmap= BitmapFactory.decodeStream(is);
                        bitmaps.add(bitmap);
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Uri imageuri=data.getData();
                try{
                    InputStream is=getContentResolver().openInputStream(imageuri);
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    bitmaps.add(bitmap);

                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
            imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(MultipleImage.this,bitmaps);
            viewPager.setAdapter(imageFragmentPagerAdapter);
            new Thread(new Runnable() {
                @Override
                public void run() {


                }
            });
            }
        }
    }


