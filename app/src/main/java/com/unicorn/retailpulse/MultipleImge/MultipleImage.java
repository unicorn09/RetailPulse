package com.unicorn.retailpulse.MultipleImge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;

import com.unicorn.retailpulse.R;

public class MultipleImage extends AppCompatActivity {
    RecyclerView recyclerView;
    public String[] imageList;
    int num_columns = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_image);
        recyclerView = findViewById(R.id.gallery_recycler);
    }

}
