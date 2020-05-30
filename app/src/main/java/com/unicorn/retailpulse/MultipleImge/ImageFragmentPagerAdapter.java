package com.unicorn.retailpulse.MultipleImge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.unicorn.retailpulse.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ImageFragmentPagerAdapter extends PagerAdapter {
    Context context;
    List<Bitmap> bitmaps=new ArrayList<>();
    LayoutInflater mLayoutInflater;

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

        Log.e("Adapter", "instantiateItem: "+bitmaps.get(position).toString());
        imageView.setImageBitmap(bitmaps.get(position));


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}